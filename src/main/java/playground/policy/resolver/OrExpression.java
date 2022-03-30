package playground.policy.resolver;

public class OrExpression implements Expression {
    private Expression expr1;
    private Expression expr2;

    public OrExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public boolean evaluate(ContextDataRepository data) {
        return expr1.evaluate(data)|| expr2.evaluate(data);
    }
}
