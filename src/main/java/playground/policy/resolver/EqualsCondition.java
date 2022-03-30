package playground.policy.resolver;

import be.hcbgsystem.core.models.ContextData;

public class EqualsCondition<T> implements Condition {
    private String providerId;
    private T value;

    public EqualsCondition(String providerId, T value) {
        this.providerId = providerId;
        this.value = value;
    }

    @Override
    public boolean evaluate(ContextDataRepository data) {
        for (ContextData entry : data.getContextData(providerId)) {
            if (Double.parseDouble(entry.getData().toString()) == (Double.parseDouble(value.toString()))) { // TODO Only works for numbers. not strings.
                return true;
            }
        }

        return false;
    }
}
