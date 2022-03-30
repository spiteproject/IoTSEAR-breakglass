package benchmarks;

import be.hcbgsystem.BreakGlassSystem;
import be.hcbgsystem.core.breakglass.activation.BreakGlassActivationHandler;
import be.hcbgsystem.core.breakglass.decision.BreakGlassDecisionPoint;
import be.hcbgsystem.core.breakglass.decision.BreakGlassDecisionPointStandard;
import be.hcbgsystem.core.breakglass.enforcement.BreakGlassEnforcementPoint;
import be.hcbgsystem.core.breakglass.enforcement.BreakGlassEnforcementPointStandard;
import be.hcbgsystem.core.data.BreakGlassDB;
import be.hcbgsystem.core.data.BreakGlassPoliciesCRUD;
import be.hcbgsystem.core.data.BreakGlassPoliciesDB;
import be.hcbgsystem.core.data.BreakGlassSystemStatus;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;
import be.hcbgsystem.policies.breakglass.BreakGlassPolicyResolver;
import be.hcbgsystem.policies.breakglass.BreakGlassPolicyResolverSimple;
import org.openjdk.jmh.annotations.*;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations=0)
@Measurement(iterations = 100)
public class BreakGlassBenchmark {
    BreakGlassPolicyResolver breakGlassPolicyResolver;
    BreakGlassDecisionPoint breakGlassDecisionPoint;
    ArrayList<BreakGlassPolicy> policies;
    EmergencyLevel level;
    BreakGlassDB breakGlassSystemStatus = new BreakGlassDB();
    BreakGlassEnforcementPoint breakGlassEnforcementPoint;


    @Param({"1000", "10000"})
    public int nBreakGlassPolicies;

    @Setup(Level.Trial)
    public void setup() {
        BreakGlassSystem breakGlassSystem = BreakGlassSystem.getInstance();
        breakGlassSystem.configure("breakglasspolicies-" + nBreakGlassPolicies, "emergencypolicies.json");
        breakGlassSystem.startHeadless();

        BreakGlassPoliciesCRUD breakGlassPoliciesCRUD = breakGlassSystem.getBreakGlassPoliciesCRUD();
        ((BreakGlassPoliciesDB) breakGlassPoliciesCRUD).init(new File(BreakGlassSystem.class.getClassLoader().getResource("breakglasspolicies-" + nBreakGlassPolicies).getFile()));
        policies = breakGlassPoliciesCRUD.getBreakGlassPolicies();
        level = new EmergencyLevel(4);
        breakGlassSystemStatus.setEmergencyLevel(new EmergencyLevel(4));
        breakGlassPolicyResolver = breakGlassSystem.getBreakGlassPolicyResolver();
        breakGlassDecisionPoint = breakGlassSystem.getBreakGlassDecisionPoint();
    }

    @Benchmark
    public void breakGlassPoliciesResolution() {
        policies.forEach((p) -> breakGlassPolicyResolver.resolve(p, level));
    }

    @Benchmark
    public void breakGlassDecisionPoint() {
        breakGlassDecisionPoint.decidePolicy();
    }

}
