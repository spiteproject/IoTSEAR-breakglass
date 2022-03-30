package be.hcbgsystem.core.configuration;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;

import java.util.ArrayList;

public interface BreakGlassPolicyConfiguration {
    void addBreakGlassPolicy(BreakGlassPolicy policy);
    void deleteBreakGlassPolicy(String policyId);
    void updateBreakGlassPolicy(BreakGlassPolicy newPolicy);
    ArrayList<BreakGlassPolicy> getBreakGlassPolicies();
    BreakGlassPolicy getBreakGlassPolicy(String id);
}
