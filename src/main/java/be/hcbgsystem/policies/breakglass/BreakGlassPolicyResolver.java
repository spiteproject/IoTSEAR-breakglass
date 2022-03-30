package be.hcbgsystem.policies.breakglass;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;

public interface BreakGlassPolicyResolver {
    boolean resolve(BreakGlassPolicy policy, EmergencyLevel emergencyLevel);
}
