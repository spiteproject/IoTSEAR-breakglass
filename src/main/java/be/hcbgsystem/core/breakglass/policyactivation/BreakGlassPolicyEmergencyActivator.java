package be.hcbgsystem.core.breakglass.policyactivation;

import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;

public interface BreakGlassPolicyEmergencyActivator {
    void activatePolicies(EmergencyLevel level);
}
