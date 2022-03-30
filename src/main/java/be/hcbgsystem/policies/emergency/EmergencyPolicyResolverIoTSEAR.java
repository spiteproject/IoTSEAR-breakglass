package be.hcbgsystem.policies.emergency;

import be.distrinet.spite.iotsear.IoTSEAR;
import be.distrinet.spite.iotsear.pbms.PolicyDecisionListener;
import be.distrinet.spite.iotsear.pbms.PolicyEngine;
import be.distrinet.spite.iotsear.policy.AuthorizationPolicy;
import be.distrinet.spite.iotsear.policy.PolicyTarget;
import be.hcbgsystem.core.data.EmergencyPoliciesCRUD;
import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;

import java.util.ArrayList;
import java.util.List;

public class EmergencyPolicyResolverIoTSEAR implements EmergencyPolicyResolver {
    private PolicyEngine policyEngine;
    private EmergencyPoliciesCRUD emergencyPoliciesCRUD;

    public EmergencyPolicyResolverIoTSEAR(EmergencyPoliciesCRUD emergencyPoliciesCRUD) {
        this.emergencyPoliciesCRUD = emergencyPoliciesCRUD;
        this.policyEngine = IoTSEAR.getInstance().getPolicyEngine();
    }

    @Override
    public void resolve(EmergencyPolicyDecisionListener emergencyPolicyDecisionListener) {
        PolicyTarget target = new PolicyTarget("EmergencyPolicyResolver", "emergency", "detect");
        policyEngine.enforce(target, new PolicyDecisionListener() {
            @Override
            public void processDecision(AuthorizationPolicy.PolicyEffect policyEffect, PolicyTarget policyTarget) {
            }

            @Override
            public void notifyMatchedPolicies(List<AuthorizationPolicy> policies, PolicyTarget pepTarget) {
                if (!policies.isEmpty()) {
                    ArrayList<EmergencyPolicy> emergencyPolicies = new ArrayList<>();
                    for (AuthorizationPolicy p : policies) {
                        emergencyPolicies.add(emergencyPoliciesCRUD.getEmergencyPolicy(p.getIdentifier()));
                    }
                    emergencyPolicyDecisionListener.notifyDecidedEmergencyPolicies(emergencyPolicies);
                }
            }

            @Override
            public void notifyEnforcedPolicy(AuthorizationPolicy policy, PolicyTarget pepTarget) {
            }
        });
    }
}
