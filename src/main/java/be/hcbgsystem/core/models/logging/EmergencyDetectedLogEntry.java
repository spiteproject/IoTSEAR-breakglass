package be.hcbgsystem.core.models.logging;

import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;

public class EmergencyDetectedLogEntry extends LogEntry {
    private EmergencyPolicy activePolicy;

    public EmergencyDetectedLogEntry(EmergencyPolicy activePolicy) {
        this.activePolicy = activePolicy;
    }

    public EmergencyPolicy getActivePolicy() {
        return activePolicy;
    }

    public void setActivePolicy(EmergencyPolicy activePolicy) {
        this.activePolicy = activePolicy;
    }
}
