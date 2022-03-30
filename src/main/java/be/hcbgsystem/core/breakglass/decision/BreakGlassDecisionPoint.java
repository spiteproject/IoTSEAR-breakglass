package be.hcbgsystem.core.breakglass.decision;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;

public interface BreakGlassDecisionPoint {
    BreakGlassPolicy decidePolicy();
}
