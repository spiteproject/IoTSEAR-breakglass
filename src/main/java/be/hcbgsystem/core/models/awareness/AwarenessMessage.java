package be.hcbgsystem.core.models.awareness;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;

public class AwarenessMessage {
    private String prelude;
    private BreakGlassPolicy bgPolicy;

    public AwarenessMessage(String prelude, BreakGlassPolicy bgPolicy) {
        this.prelude = prelude;
        this.bgPolicy = bgPolicy;
    }

    public String getPrelude() {
        return prelude;
    }

    public void setPrelude(String prelude) {
        this.prelude = prelude;
    }

    public BreakGlassPolicy getBgPolicy() {
        return bgPolicy;
    }

    public void setBgPolicy(BreakGlassPolicy bgPolicy) {
        this.bgPolicy = bgPolicy;
    }
}
