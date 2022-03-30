package be.hcbgsystem.logging;

import be.hcbgsystem.core.models.nonrepudiation.RetainedNonRepudiationEvidence;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassAction;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;

public interface SecureLog {
    void logBreakGlassActionEntry(BreakGlassAction action);

    void logBreakGlassActivationEntry(BreakGlassPolicy activePolicy, RetainedNonRepudiationEvidence evidence);

    void logEmergencyDetectedEntry(EmergencyPolicy policy);
}
