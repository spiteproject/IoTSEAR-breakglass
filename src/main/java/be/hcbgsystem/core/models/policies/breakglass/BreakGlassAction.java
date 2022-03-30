package be.hcbgsystem.core.models.policies.breakglass;

import be.hcbgsystem.core.models.AccessRequest;
import be.hcbgsystem.frontend.AccessDecision;

public class BreakGlassAction {
    private AccessRequest request;
    private AccessDecision decision;

    public BreakGlassAction(AccessRequest request, AccessDecision decision) {
        this.request = request;
        this.decision = decision;
    }

    public AccessRequest getRequest() {
        return request;
    }

    public void setRequest(AccessRequest request) {
        this.request = request;
    }

    public AccessDecision getDecision() {
        return decision;
    }

    public void setDecision(AccessDecision decision) {
        this.decision = decision;
    }
}
