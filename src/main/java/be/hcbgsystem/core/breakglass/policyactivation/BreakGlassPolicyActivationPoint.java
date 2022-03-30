package be.hcbgsystem.core.breakglass.policyactivation;

import be.hcbgsystem.core.data.BreakGlassPoliciesCRUD;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;
import be.hcbgsystem.policies.breakglass.BreakGlassPolicyResolver;

import java.util.ArrayList;

public class BreakGlassPolicyActivationPoint implements BreakGlassPolicyEmergencyActivator {
    private BreakGlassPoliciesCRUD breakGlassPoliciesCRUD;
    private BreakGlassPolicyResolver policyResolver;

    public BreakGlassPolicyActivationPoint(BreakGlassPoliciesCRUD breakGlassPoliciesCRUD, BreakGlassPolicyResolver policyResolver) {
        this.breakGlassPoliciesCRUD = breakGlassPoliciesCRUD;
        this.policyResolver = policyResolver;
        activatePolicies(new EmergencyLevel(0)); // Enable the default policies
    }

    @Override
    public void activatePolicies(EmergencyLevel level) {
        if (level != null) {
            ArrayList<BreakGlassPolicy> policies = breakGlassPoliciesCRUD.getBreakGlassPolicies();
            policies.removeIf(p -> !policyResolver.resolve(p, level));
            breakGlassPoliciesCRUD.setActiveBreakGlassPolicies(policies);
        }
    }
}
