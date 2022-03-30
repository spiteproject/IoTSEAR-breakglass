package playground.policy.resolver;

public interface Expression {
    boolean evaluate(ContextDataRepository data);
}
