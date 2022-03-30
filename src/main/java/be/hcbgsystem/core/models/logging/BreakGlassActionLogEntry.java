package be.hcbgsystem.core.models.logging;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassAction;

public class BreakGlassActionLogEntry extends LogEntry {
    private BreakGlassAction action;

    public BreakGlassActionLogEntry(BreakGlassAction action) {
        this.action = action;
    }

    public BreakGlassAction getAction() {
        return action;
    }

    public void setAction(BreakGlassAction action) {
        this.action = action;
    }
}
