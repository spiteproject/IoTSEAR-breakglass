package be.hcbgsystem.logging;

import be.hcbgsystem.core.models.logging.BreakGlassActionLogEntry;
import be.hcbgsystem.core.models.logging.BreakGlassActivationLogEntry;
import be.hcbgsystem.core.models.logging.EmergencyDetectedLogEntry;
import be.hcbgsystem.core.models.nonrepudiation.RetainedNonRepudiationEvidence;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassAction;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;
import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Log4JLogger implements SecureLog {
    private static final Logger logger = LogManager.getLogger(Log4JLogger.class.getName());
    private Gson serializer = new Gson();

    private static final String LEVEL_BREAK_GLASS_ACTION = "BG_ACTION";
    private static final String LEVEL_BREAK_GLASS_ACTIVATION = "BG_ACTIVATION";
    private static final String LEVEL_EMERGENCY_DETECTED = "EMERGENCY_DETECTED";

    @Override
    public void logBreakGlassActionEntry(BreakGlassAction action) {
        String log = serializer.toJson(new BreakGlassActionLogEntry(action));
        logger.log(Level.forName(LEVEL_BREAK_GLASS_ACTION, 350), log);
    }

    @Override
    public void logBreakGlassActivationEntry(BreakGlassPolicy activePolicy, RetainedNonRepudiationEvidence evidence) {
        String log = serializer.toJson(new BreakGlassActivationLogEntry(activePolicy, evidence));
        logger.log(Level.forName(LEVEL_BREAK_GLASS_ACTIVATION, 550), log);
    }

    @Override
    public void logEmergencyDetectedEntry(EmergencyPolicy policy) {
        String log = serializer.toJson(new EmergencyDetectedLogEntry(policy));
        logger.log(Level.forName(LEVEL_EMERGENCY_DETECTED, 450), log);
    }
}
