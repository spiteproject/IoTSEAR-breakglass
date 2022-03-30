package benchmarks;

import be.hcbgsystem.BreakGlassSystem;
import be.hcbgsystem.core.context.sinks.EmergencyDetectionPoint;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations=0)
@Measurement(iterations = 100)
public class EmergencyBenchmark {
    private BreakGlassSystem breakGlassSystem;

    @Param({"1000", "10000"})
    public int nPolicies;

    @Setup(Level.Iteration)
    public void setup() {
        breakGlassSystem = BreakGlassSystem.getInstance();
        breakGlassSystem.configure("breakglasspolicies-" + nPolicies, "emergencypolicies-" + nPolicies);
        breakGlassSystem.startHeadless();
    }

    public void emergencyDetected() {
        breakGlassSystem.getEmergencyDetectionPoint().receiveData(null);

    }
}
