package be.hcbgsystem.policies.breakglass;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;

public interface BreakGlassPolicyParser {
    BreakGlassPolicy parse(String policy);
}
