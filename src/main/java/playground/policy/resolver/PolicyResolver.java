package playground.policy.resolver;

import be.hcbgsystem.core.models.ContextData;

public class PolicyResolver {
    public static void main(String[] args) {
        GreaterThanCondition gt = new GreaterThanCondition<Integer>("ecg-1", 5);
        LessThanCondition lt = new LessThanCondition<Integer>("ecg-2", 5);
        OrExpression orExpr = new OrExpression(gt, lt);
        EqualsCondition eq = new EqualsCondition<Integer>("ecg-3", 10);
        AndExpression andExpr =  new AndExpression(orExpr, eq);
        Expression policy = andExpr;

        // (ecg-1 > 5 or ecg-2 < 5) and (ecg-3 = 10)

        ContextData<Double> ecg1 = new ContextData<>("ecg-1", "", 6.0, null);
        ContextData<Double> ecg2 = new ContextData<>("ecg-2", "", 5.0, null);
        ContextData<Double> ecg3 = new ContextData<>("ecg-3", "", 10.0, null);

        ContextDataRepository repository = new ContextDataRepository();
        repository.addContextData(ecg1);
        repository.addContextData(ecg2);
        repository.addContextData(ecg3);

        boolean a = gt.evaluate(repository);
        boolean b = lt.evaluate(repository);
        boolean c = orExpr.evaluate(repository);
        boolean d = eq.evaluate(repository);
        boolean e = policy.evaluate(repository);

        boolean result = policy.evaluate(repository);
        System.out.println(result);
    }
}
