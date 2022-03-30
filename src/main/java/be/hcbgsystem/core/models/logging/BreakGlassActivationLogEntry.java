package be.hcbgsystem.core.models.logging;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.nonrepudiation.RetainedNonRepudiationEvidence;

public class BreakGlassActivationLogEntry extends LogEntry {
    private BreakGlassPolicy activePolicy;
    private RetainedNonRepudiationEvidence nonRepudiationEvidence;

    public BreakGlassActivationLogEntry(BreakGlassPolicy activePolicy, RetainedNonRepudiationEvidence nonRepudiationEvidence) {
        this.activePolicy = activePolicy;
        this.nonRepudiationEvidence = nonRepudiationEvidence;
    }

    public BreakGlassPolicy getActivePolicy() {
        return activePolicy;
    }

    public void setActivePolicy(BreakGlassPolicy activePolicy) {
        this.activePolicy = activePolicy;
    }

    public RetainedNonRepudiationEvidence getNonRepudiationEvidence() {
        return nonRepudiationEvidence;
    }

    public void setNonRepudiationEvidence(RetainedNonRepudiationEvidence nonRepudiationEvidence) {
        this.nonRepudiationEvidence = nonRepudiationEvidence;
    }


}
