package be.hcbgsystem.policies.emergency;

import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;

public interface EmergencyPolicyParser {
    EmergencyPolicy parse(String policy);
}
