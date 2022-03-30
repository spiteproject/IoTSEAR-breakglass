package be.hcbgsystem.iotsear.verifiers;

import be.distrinet.spite.iotsear.core.model.context.ContextAttribute;
import be.distrinet.spite.iotsear.policy.PolicyConditionVerifier;
import be.distrinet.spite.iotsear.policy.abstractFactories.PolicyConditionVerifierFactory;
import org.pf4j.Extension;

@Extension
public class FreshnessVerifier15s extends PolicyConditionVerifier implements PolicyConditionVerifierFactory {
    public FreshnessVerifier15s() {
    }

    public boolean verify(ContextAttribute attribute) {
        long timestamp = attribute.getTimestamp();
        return System.currentTimeMillis() - timestamp < 15000L;
    }

    public String getProviderID() {
        return "darc:condition:verifier:freshness:15s";
    }

    public PolicyConditionVerifier createPolicyConditionVerifier() {
        return new FreshnessVerifier15s();
    }
}