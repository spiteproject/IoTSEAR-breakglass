package be.hcbgsystem.core.data;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassLevel;

public interface BreakGlassSystemStatus {
    BreakGlassLevel getBreakGlassLevel();
    void setBreakGlassLevel(BreakGlassLevel level);
}
