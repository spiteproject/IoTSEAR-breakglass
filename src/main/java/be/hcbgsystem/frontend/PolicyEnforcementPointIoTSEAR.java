package be.hcbgsystem.frontend;

import be.distrinet.spite.iotsear.IoTSEAR;
import be.distrinet.spite.iotsear.pbms.PolicyDecisionListener;
import be.distrinet.spite.iotsear.pbms.PolicyEngine;
import be.distrinet.spite.iotsear.policy.AuthorizationPolicy;
import be.distrinet.spite.iotsear.policy.PolicyTarget;
import be.hcbgsystem.core.models.AccessRequest;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassAction;
import be.hcbgsystem.logging.SecureLog;

public class PolicyEnforcementPointIoTSEAR implements PolicyEnforcementPoint {
    private PolicyEngine policyEngine;
    private SecureLog secureLog;

    public PolicyEnforcementPointIoTSEAR(IoTSEAR ioTSEAR, SecureLog secureLog) {
        this.policyEngine = ioTSEAR.getPolicyEngine();
        this.secureLog = secureLog;
    }

    @Override
    public void requestAccess(AccessRequest accessRequest, PolicyDecisionCallback callback) {
        policyEngine.enforce(new PolicyTarget(accessRequest.getSubject(), accessRequest.getResource(), accessRequest.getAction()), new PolicyDecisionListener() {
            @Override
            public void processDecision(AuthorizationPolicy.PolicyEffect policyEffect, PolicyTarget policyTarget) {
                AccessDecision decision = policyEffect == AuthorizationPolicy.PolicyEffect.ALLOW ? AccessDecision.GRANTED : AccessDecision.DENIED;
           //     if (decision == AccessDecision.GRANTED) {
                    BreakGlassAction action = new BreakGlassAction(accessRequest, decision);
                    secureLog.logBreakGlassActionEntry(action);
           //     }

                callback.decision(decision, accessRequest);
            }
        });
    }
}
