package be.hcbgsystem.policies.emergency;

import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;

import java.util.ArrayList;

public interface EmergencyPolicyDecisionListener {
    void notifyDecidedEmergencyPolicies(ArrayList<EmergencyPolicy> emergencyPolicies);
    void notifyDecidedEmergencyPolicy(EmergencyPolicy policy);
}
