package be.hcbgsystem.core.breakglass.enforcement;

import be.hcbgsystem.awareness.AwarenessPoint;
import be.hcbgsystem.core.breakglass.activation.BreakGlassActivationHandler;
import be.hcbgsystem.core.breakglass.activation.BreakGlassActivator;
import be.hcbgsystem.core.breakglass.decision.BreakGlassDecisionPoint;
import be.hcbgsystem.core.data.BreakGlassSystemStatus;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassLevel;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidence;
import be.hcbgsystem.logging.SecureLog;
import be.hcbgsystem.nonrepudiation.EvidenceCollectedCallback;
import be.hcbgsystem.nonrepudiation.EvidenceCollector;
import be.hcbgsystem.nonrepudiation.retention.EvidenceRetention;
import be.hcbgsystem.notification.BreakGlassNotificationPoint;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BreakGlassEnforcementPointStandard implements BreakGlassEnforcementPoint, BreakGlassActivationHandler {
    private enum GLASS_PHASE {
        BROKEN,
        INTACT,
        COLLECTING_EVIDENCE,
        COLLECTED_EVIDENCE
    };
    private NonRepudiationEvidence collectedEvidence;
    private GLASS_PHASE currentPhase;
    private AwarenessPoint awarenessPoint;
    private BreakGlassNotificationPoint notificationPoint;
    private BreakGlassDecisionPoint decisionPoint;
    private EvidenceCollector evidenceCollector;
    private SecureLog secureLog;
    private BreakGlassSystemStatus breakGlassSystemStatus;
    private EvidenceRetention evidenceRetention;

    private ArrayList<BreakGlassActivator> breakGlassActivators;
    private BreakGlassPolicy activePolicy;
    private Timer disableTimer;

    public BreakGlassEnforcementPointStandard(AwarenessPoint awarenessPoint, BreakGlassNotificationPoint notificationPoint,
                                              BreakGlassDecisionPoint decisionPoint, EvidenceCollector evidenceCollector, SecureLog log, BreakGlassSystemStatus breakGlassSystemStatus,
                                              EvidenceRetention evidenceRetention) {
        currentPhase = GLASS_PHASE.INTACT;
        this.awarenessPoint = awarenessPoint;
        this.notificationPoint = notificationPoint;
        this.decisionPoint = decisionPoint;
        this.evidenceCollector = evidenceCollector;
        this.secureLog = log;
        this.breakGlassSystemStatus = breakGlassSystemStatus;
        this.evidenceRetention = evidenceRetention;

        breakGlassActivators = new ArrayList<>();
        disableTimer = new Timer("disable-bg-timer");
    }

    @Override
    public void registerBreakGlassActivator(BreakGlassActivator activator) {
        breakGlassActivators.add(activator);
    }

    @Override
    public void unregisterBreakGlassActivator(BreakGlassActivator activator) {
        breakGlassActivators.remove(activator);
    }

    @Override
    public void start() {
        startActivators();
    }

    private void startActivators() {
        breakGlassActivators.forEach((activator) -> new Thread(() -> activator.startActivator(this::onActivatorTrigger)).start());
    }

    private void onActivatorTrigger(BreakGlassActivator activator) {
        BreakGlassPolicy policy = decisionPoint.decidePolicy(); // Get most applicable Break Glass policy

        if (currentPhase == GLASS_PHASE.INTACT || (currentPhase == GLASS_PHASE.COLLECTING_EVIDENCE && !policy.equals(activePolicy))) {
            if (policy != null) { // TODO Log if no such policy exists bcs no default open policy set.
                activePolicy = policy;
                System.out.println("[BGEP] Providing awareness...");
                awarenessPoint.provideAwareness(policy); // Provide awareness
                System.out.println("[BGEP] Evidence collection INITIATED");
                currentPhase = GLASS_PHASE.COLLECTING_EVIDENCE;
                breakGlassActivators.forEach(BreakGlassActivator::onCollectingEvidence);

                evidenceCollector.collectEvidence(policy.getNonRepudiationRequirements(), new EvidenceCollectedCallback() { // Collect non-repudiation evidence
                    @Override
                    public void evidenceCollected(NonRepudiationEvidence evidence) {
                        onEvidenceCollectionSuccess(evidence);
                    }

                    @Override
                    public void evidenceCollectionFailed() {
                        onEvidenceCollectionFailed();
                    }
                });
            } else {
                System.out.println("[BGEP] No active Break Glass policy! Error in policy configuration!");
            }
        } else if (currentPhase == GLASS_PHASE.COLLECTED_EVIDENCE) {
            enableBreakGlass();
        }


    }

    private void onEvidenceCollectionSuccess(NonRepudiationEvidence evidence) {
        System.out.println("[BGEP] Evidence collection SUCCEEDED");
        currentPhase = GLASS_PHASE.COLLECTED_EVIDENCE;
        breakGlassActivators.forEach(BreakGlassActivator::onCollectedEvidence);
        this.collectedEvidence = evidence;
    }

    private void enableBreakGlass() {
        System.out.println("[BGEP] Glass BROKEN");
        currentPhase = GLASS_PHASE.BROKEN;
        breakGlassActivators.forEach(BreakGlassActivator::onGlassBroken);

        breakGlassSystemStatus.setBreakGlassLevel(activePolicy.getBreakGlassLevel());
        notificationPoint.deliverBreakGlassNotification(activePolicy.getBreakGlassLevel());
        secureLog.logBreakGlassActivationEntry(activePolicy, evidenceRetention.retainEvidence(collectedEvidence));

        disableTimer.cancel(); // Cancel previous timer. Else, new BG level might be disabled too early due to an old timer.
        disableTimer.purge();
        disableTimer = new Timer("disable-bg-timer");
        disableTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                disableBreakGlass();
            }
        }, activePolicy.getBreakGlassDuration() * 1000);
    }

    private void disableBreakGlass() {
        System.out.println("[BGEP] Glass RESTORED");
        currentPhase = GLASS_PHASE.INTACT;
        breakGlassActivators.forEach(BreakGlassActivator::onRestored);
        breakGlassSystemStatus.setBreakGlassLevel(new BreakGlassLevel(0));
    }

    private void onEvidenceCollectionFailed() {
        System.out.println("[BGEP] Evidence collection FAILED");
        currentPhase = GLASS_PHASE.INTACT;
        breakGlassActivators.forEach(BreakGlassActivator::onRestored);
        reset();
    }

    private void reset() {
        activePolicy = null;
    }
}
