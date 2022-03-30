package be.hcbgsystem.core.context.sinks;

import be.hcbgsystem.core.breakglass.interruption.UserInterruption;
import be.hcbgsystem.core.breakglass.interruption.UserInterruptionResult;
import be.hcbgsystem.core.breakglass.policyactivation.BreakGlassPolicyEmergencyActivator;
import be.hcbgsystem.core.data.EmergencyPoliciesCRUD;
import be.hcbgsystem.core.data.EmergencySystemStatus;
import be.hcbgsystem.core.models.ContextData;
import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;
import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;
import be.hcbgsystem.core.models.InterruptableEvent;
import be.hcbgsystem.logging.SecureLog;
import be.hcbgsystem.notification.EmergencyNotificationPoint;
import be.hcbgsystem.policies.emergency.EmergencyPolicyDecisionListener;
import be.hcbgsystem.policies.emergency.EmergencyPolicyResolver;

import java.util.*;

public class EmergencyDetectionPoint implements ContextDataSink, UserInterruption {
    private EmergencyPoliciesCRUD emergencyPoliciesCRUD;
    private EmergencySystemStatus emergencySystemStatus;
    private EmergencyNotificationPoint emergencyNotificationPoint;
    private BreakGlassPolicyEmergencyActivator policyActivator;
    private EmergencyPolicyResolver emergencyPolicyResolver;
    private SecureLog secureLog;

    private Queue<EmergencyTimer> interruptionTimers;
    private ArrayList<EmergencyTimer> disableTimers;
    private ArrayList<EmergencyPolicy> activePolicies;

    private EmergencyPolicy principalPolicy;

    public EmergencyDetectionPoint(EmergencyPoliciesCRUD emergencyPoliciesCRUD, EmergencySystemStatus emergencySystemStatus,
                                   EmergencyNotificationPoint emergencyNotificationPoint, BreakGlassPolicyEmergencyActivator policyActivator,
                                   EmergencyPolicyResolver emergencyPolicyResolver, SecureLog secureLog) {
        this.emergencyPoliciesCRUD = emergencyPoliciesCRUD;
        this.emergencySystemStatus = emergencySystemStatus;
        this.emergencyNotificationPoint = emergencyNotificationPoint;
        this.policyActivator = policyActivator;
        this.emergencyPolicyResolver = emergencyPolicyResolver;
        this.secureLog = secureLog;

        interruptionTimers = new LinkedList<>();
        activePolicies = new ArrayList<>();
        disableTimers = new ArrayList<>();
    }

    @Override
    public void receiveData(ContextData data) {
        detectEmergency();
    }

    private void detectEmergency() {
        // We don't do anything with the context data at this moment as it is automatically stored in IoTSEAR.
        emergencyPolicyResolver.resolve(new EmergencyPolicyDecisionListener() {
            @Override
            public void notifyDecidedEmergencyPolicies(ArrayList<EmergencyPolicy> emergencyPolicies) {
                // Max level
                onDetectedEmergencies(emergencyPolicies);
            }

            @Override
            public void notifyDecidedEmergencyPolicy(EmergencyPolicy policy) {

            }
        });

    }

    private void onDetectedEmergencies(ArrayList<EmergencyPolicy> emergencyPolicies) {
        emergencyPolicies.forEach(this::onDetectedEmergency);
    }

    private synchronized void onDetectedEmergency(EmergencyPolicy emergencyPolicy) {
        if (hasInterruptTimer(emergencyPolicy)) return; // Already has interrupt timer: do nothing. Yet to be activated

        if (hasDisableTimer(emergencyPolicy)) { // Emergency already activated --> Reset timer
            System.out.println("[EDP] Emergency '" + emergencyPolicy.getId() + "' already activated. Resetting timer...");
            resetDisableTimer(emergencyPolicy);
        } else { // Emergency not activated/detected
            System.out.println("[EDP] Emergency '" + emergencyPolicy.getId() + "' detected");
            setNewInterruptTimer(emergencyPolicy);
        }
    }

    private boolean hasInterruptTimer(EmergencyPolicy policy) {
        return interruptionTimers.stream().anyMatch((t) -> t.getPolicy().equals(policy));
    }

    private boolean hasDisableTimer(EmergencyPolicy policy) {
        return disableTimers.stream().anyMatch((t) -> t.getPolicy().equals(policy));
    }

    private void setNewInterruptTimer(EmergencyPolicy policy) {
        EmergencyTimer interruptTimer = new EmergencyTimer(policy);
        interruptionTimers.add(interruptTimer);
        interruptTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                emergencyNotificationPoint.deliverEmergencyNotification(policy.getEmergencyLevel());
                activateEmergency(policy);
            }
        }, policy.getCancelPeriod() * 1000);
    }

    private void setNewDisableTimer(EmergencyPolicy policy) {
        EmergencyTimer disableTimer = new EmergencyTimer(policy);
        disableTimers.add(disableTimer);
        disableTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                disableTimers.remove(disableTimer);
                deactivateEmergency(policy);
            }
        }, policy.getEmergencyPeriod() * 1000);
    }

    private synchronized void activateEmergency(EmergencyPolicy policy) {
        // Add to the active policies
        activePolicies.add(policy);
        // Set new max level
        setMaxEmergencyLevel();
        // Set disable timer
        setNewDisableTimer(policy);
        // Activate BG policies
        policyActivator.activatePolicies(principalPolicy.getEmergencyLevel());
        // Log emergency
        secureLog.logEmergencyDetectedEntry(policy);
    }

    private synchronized void deactivateEmergency(EmergencyPolicy policy) {
        // Remove from active policies
        activePolicies.remove(policy);
        // Set new active emergency level
        setMaxEmergencyLevel();
        // Remove disable timers
        purgeDisableTimer(policy); // Todo shouldn't be necessary as timer removes itself
        // Re-activate policies
        policyActivator.activatePolicies(principalPolicy.getEmergencyLevel());
    }

    private void purgeDisableTimer(EmergencyPolicy policy) {
        disableTimers.forEach((t) -> {
            if (t.getPolicy().equals(policy)) {
                t.cancel();
                t.purge();
            }
        });
        disableTimers.removeIf((t) -> t.getPolicy().equals(policy));
    }

    private void resetDisableTimer(EmergencyPolicy policy) {
        purgeDisableTimer(policy);
        setNewDisableTimer(policy);
    }

    private void setMaxEmergencyLevel() {
        if (!activePolicies.isEmpty()) {
            EmergencyPolicy maxPolicy = activePolicies.stream().max(Comparator.comparingInt(a -> a.getEmergencyLevel().getLevel())).get();
            emergencySystemStatus.setEmergencyLevel(maxPolicy.getEmergencyLevel());
            principalPolicy = maxPolicy;
        } else {
            principalPolicy = null;
            emergencySystemStatus.setEmergencyLevel(new EmergencyLevel(0));
        }
    }

    @Override
    public UserInterruptionResult interruptActivation() {
        System.out.println("[EDP] Interrupting emergency activation...");
        if (!interruptionTimers.isEmpty()) {
            EmergencyTimer t = interruptionTimers.remove();
            t.cancel();
            t.purge();
            System.out.println("[EDP] Interrupted emergency activation (policy: " + t.getPolicy().getId() +  ")");
            return UserInterruptionResult.INTERRUPTED;
        }
        System.out.println("[EDP] Nothing to interrupt");
        return UserInterruptionResult.NOTHING_TO_INTERRUPT;
    }

    @Override
    public InterruptableEvent getInterruptableEvents() {
        return !interruptionTimers.isEmpty() ? new InterruptableEvent(interruptionTimers.peek().getPolicy().toString()) : null;
    }

    private class EmergencyTimer extends Timer {
        private EmergencyPolicy policy;

        EmergencyTimer(EmergencyPolicy policy) {
            this.policy = policy;
        }

        public EmergencyPolicy getPolicy() {
            return policy;
        }

        public void setPolicy(EmergencyPolicy policy) {
            this.policy = policy;
        }
    }
}
