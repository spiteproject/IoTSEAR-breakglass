package be.hcbgsystem.core.data;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;

import java.util.ArrayList;

public interface BreakGlassPoliciesCRUD {
    void addBreakGlassPolicy(BreakGlassPolicy policy);
    void deleteBreakGlassPolicy(String policyId);
    void updateBreakGlassPolicy(BreakGlassPolicy newPolicy);
    ArrayList<BreakGlassPolicy> getBreakGlassPolicies();
    ArrayList<BreakGlassPolicy> getActiveBreakGlassPolicies();
    void setActiveBreakGlassPolicies(ArrayList<BreakGlassPolicy> policies);
    BreakGlassPolicy getBreakGlassPolicy(String id);
}
