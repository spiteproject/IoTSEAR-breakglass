package be.hcbgsystem.iotsear.condition;
import be.distrinet.spite.iotsear.core.exceptions.ProviderNotFoundException;
import be.distrinet.spite.iotsear.core.model.context.ContextAttribute;
import be.distrinet.spite.iotsear.managers.PolicyFactoryManager;
import be.distrinet.spite.iotsear.pbms.ContextStorage;
import be.distrinet.spite.iotsear.policy.PolicyCondition;
import be.distrinet.spite.iotsear.policy.PolicyConditionOperation;
import be.distrinet.spite.iotsear.policy.PolicyConditionVerifier;
import be.distrinet.spite.iotsear.policy.abstractFactories.PolicyConditionFactory;
import be.distrinet.spite.iotsear.policy.abstractFactories.PolicyConditionOperationFactory;
import be.distrinet.spite.iotsear.systemProviders.darc.DarcCondition;
import be.hcbgsystem.BreakGlassSystem;
import be.hcbgsystem.core.data.BreakGlassDB;
import be.hcbgsystem.core.data.BreakGlassSystemStatus;
import org.pf4j.Extension;

import java.util.*;

@Extension
public class BreakGlassLevelCondition extends DarcCondition {
    @Override
    public String getProviderID() {
        return "darc:condition:breakglasslevel";
    }

    @Override
    public boolean evaluate(ContextStorage storage) {
        int val = Integer.parseInt(getValue());

      //  List<ContextAttribute> levels = storage.findBySource("breakglasslevel");

        int currentLevel = BreakGlassSystem.getInstance().getBreakGlassSystemStatus().getBreakGlassLevel().getLevel(); //levels.isEmpty() ? 0 : Integer.parseInt(levels.get(levels.size() - 1).getValue());
        return super.evaluate(storage) && currentLevel >= val;
    }

    @Override
    public PolicyCondition createPolicyCondition(HashMap<String, Object> policyParts) {
        String value = policyParts.get("value").toString();
        return new DarcCondition("breakglasslevel", null, value, null);
    }


}
