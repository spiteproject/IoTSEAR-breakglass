package be.hcbgsystem.core.configuration;

import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;

import java.util.ArrayList;

public interface EmergencyPolicyConfiguration {
    void addEmergencyPolicy(EmergencyPolicy policy);
    void deleteEmergencyPolicy(String id);
    void updateEmergencyPolicy(String oldId, EmergencyPolicy newPolicy);
    ArrayList<EmergencyPolicy> getEmergencyPolicies();
    EmergencyPolicy getEmergencyPolicy(String id);
}
