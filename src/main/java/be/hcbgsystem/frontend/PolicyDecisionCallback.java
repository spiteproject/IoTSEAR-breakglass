package be.hcbgsystem.frontend;

import be.hcbgsystem.core.models.AccessRequest;

public interface PolicyDecisionCallback {
    void decision(AccessDecision decision, AccessRequest request);
}
