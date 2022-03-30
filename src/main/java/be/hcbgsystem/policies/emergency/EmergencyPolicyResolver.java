package be.hcbgsystem.policies.emergency;

public interface EmergencyPolicyResolver {
    void resolve(EmergencyPolicyDecisionListener emergencyPolicyDecisionListener);
}
