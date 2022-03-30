package be.hcbgsystem.core.configuration;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;

public interface PolicyConsent {
    void giveConsent(BreakGlassPolicy policy, boolean consent);
    void giveConsent(EmergencyPolicy policy, boolean consent);
}
