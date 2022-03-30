package be.hcbgsystem.policies.breakglass;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;

public class BreakGlassPolicyResolverSimple implements BreakGlassPolicyResolver {
    @Override
    public boolean resolve(BreakGlassPolicy policy, EmergencyLevel emergencyLevel) {
        return emergencyLevel.getLevel() >= policy.getEmergencyLevel().getLevel();
    }
}
