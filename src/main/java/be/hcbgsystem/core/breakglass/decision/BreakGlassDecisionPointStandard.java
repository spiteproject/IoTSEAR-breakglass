package be.hcbgsystem.core.breakglass.decision;

import be.hcbgsystem.core.data.BreakGlassPoliciesCRUD;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;

import java.util.ArrayList;
import java.util.Comparator;

public class BreakGlassDecisionPointStandard implements BreakGlassDecisionPoint {
    private BreakGlassPoliciesCRUD breakGlassPoliciesCRUD;

    public BreakGlassDecisionPointStandard(BreakGlassPoliciesCRUD breakGlassPoliciesCRUD) {
        this.breakGlassPoliciesCRUD = breakGlassPoliciesCRUD;
    }

    @Override
    public BreakGlassPolicy decidePolicy() {
        ArrayList<BreakGlassPolicy> activePolicies = breakGlassPoliciesCRUD.getActiveBreakGlassPolicies();
        System.out.println("[BGDP] " + activePolicies.size() + " active policies.");
        if (!activePolicies.isEmpty()) {
            return activePolicies.stream().max(Comparator.comparingInt(a -> a.getBreakGlassLevel().getLevel())).get();
        }
        return null;
    }
}
