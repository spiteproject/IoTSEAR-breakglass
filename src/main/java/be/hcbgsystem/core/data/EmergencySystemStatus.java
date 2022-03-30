package be.hcbgsystem.core.data;

import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;

public interface EmergencySystemStatus {
    EmergencyLevel getEmergencyLevel();
    void setEmergencyLevel(EmergencyLevel level);
}
