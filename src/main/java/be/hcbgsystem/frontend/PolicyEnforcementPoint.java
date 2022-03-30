package be.hcbgsystem.frontend;

import be.hcbgsystem.core.models.AccessRequest;

public interface PolicyEnforcementPoint {
    void requestAccess(AccessRequest accessRequest, PolicyDecisionCallback callback);
}
