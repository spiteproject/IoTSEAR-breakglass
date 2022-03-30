package be.hcbgsystem;

import be.distrinet.spite.iotsear.IoTSEAR;
import be.hcbgsystem.awareness.AwarenessPoint;
import be.hcbgsystem.awareness.AwarenessPointNoFilter;
import be.hcbgsystem.awareness.providers.AudioAwarenessProvider;
import be.hcbgsystem.awareness.providers.AwarenessProvider;
import be.hcbgsystem.awareness.providers.ScreenAwarenessProvider;
import be.hcbgsystem.awareness.providers.StandardOutputAwarenessProvider;
import be.hcbgsystem.core.breakglass.activation.BreakGlassActivationHandler;
import be.hcbgsystem.core.breakglass.activation.BreakGlassActivator;
import be.hcbgsystem.core.breakglass.activation.VirtualButton;
import be.hcbgsystem.core.breakglass.decision.BreakGlassDecisionPoint;
import be.hcbgsystem.core.breakglass.decision.BreakGlassDecisionPointStandard;
import be.hcbgsystem.core.breakglass.enforcement.BreakGlassEnforcementPoint;
import be.hcbgsystem.core.breakglass.enforcement.BreakGlassEnforcementPointStandard;
import be.hcbgsystem.core.breakglass.policyactivation.BreakGlassPolicyActivationPoint;
import be.hcbgsystem.core.configuration.BreakGlassConfigurator;
import be.hcbgsystem.core.context.ContextProcessor;
import be.hcbgsystem.core.context.ContextProcessorNoFilter;
import be.hcbgsystem.core.context.sinks.EmergencyDetectionPoint;
import be.hcbgsystem.core.context.sources.IoTSEARDataSource;
import be.hcbgsystem.core.data.*;
import be.hcbgsystem.frontend.PolicyEnforcementPoint;
import be.hcbgsystem.frontend.PolicyEnforcementPointIoTSEAR;
import be.hcbgsystem.logging.Log4JLogger;
import be.hcbgsystem.logging.SecureLog;
import be.hcbgsystem.nonrepudiation.EvidenceCollector;
import be.hcbgsystem.nonrepudiation.EvidenceCollectorDefault;
import be.hcbgsystem.nonrepudiation.providers.ElectronicID;
import be.hcbgsystem.nonrepudiation.providers.NameInput;
import be.hcbgsystem.nonrepudiation.providers.NonRepudiationProvider;
import be.hcbgsystem.nonrepudiation.providers.SecurityCamera;
import be.hcbgsystem.nonrepudiation.resolution.NonRepudiationRequirementsResolver;
import be.hcbgsystem.nonrepudiation.resolution.NonRepudiationRequirementsResolverSimple;
import be.hcbgsystem.nonrepudiation.retention.EvidenceRetention;
import be.hcbgsystem.nonrepudiation.retention.FileNonRepudiationRetention;
import be.hcbgsystem.notification.BreakGlassNotificationPoint;
import be.hcbgsystem.notification.EmergencyNotificationPoint;
import be.hcbgsystem.notification.NotificationPoint;
import be.hcbgsystem.notification.NotificationPointAll;
import be.hcbgsystem.notification.deliverers.EmailDeliverer;
import be.hcbgsystem.notification.deliverers.NotificationDeliverer;
import be.hcbgsystem.notification.deliverers.StandardOutputDeliverer;
import be.hcbgsystem.policies.breakglass.BreakGlassPolicyResolver;
import be.hcbgsystem.policies.breakglass.BreakGlassPolicyResolverSimple;
import be.hcbgsystem.policies.emergency.EmergencyPolicyResolver;
import be.hcbgsystem.policies.emergency.EmergencyPolicyResolverIoTSEAR;
import be.hcbgsystem.web.controller.EmergencyInterruptController;
import be.hcbgsystem.web.controller.HomeController;
import be.hcbgsystem.web.controller.PatientSystemConfigurationController;
import demo.SmartLock;

import java.io.File;

public class BreakGlassSystem {
    private static BreakGlassSystem INSTANCE;

    private String breakGlassPoliciesPath;
    private String emergencyPoliciesPath;

    // Components
    private static AwarenessPoint awarenessPoint;
    private static BreakGlassDecisionPoint breakGlassDecisionPoint;
    private static BreakGlassEnforcementPoint breakGlassEnforcementPoint;
    private static BreakGlassPolicyActivationPoint breakGlassPolicyActivationPoint;
    private static ContextProcessor contextProcessor;
    private static EmergencyDetectionPoint emergencyDetectionPoint;
    private static EvidenceCollector evidenceCollector;
    private static EvidenceRetention evidenceRetention;
    private static NotificationPoint notificationPoint;
    private static BreakGlassPolicyResolver breakGlassPolicyResolver;
    private static EmergencyPolicyResolver emergencyPolicyResolver;
    private static NonRepudiationRequirementsResolver nonRepudiationRequirementsResolver;

    private static EmergencyPoliciesCRUD emergencyPoliciesCRUD;
    private static BreakGlassPoliciesCRUD breakGlassPoliciesCRUD;
    private static EmergencyContactsCRUD emergencyContactsCRUD;
    private static EmergencySystemStatus emergencySystemStatus;
    private static BreakGlassSystemStatus breakGlassSystemStatus;
    private static BreakGlassDB breakGlassDB;

    public void configure(String breakGlassPoliciesPath, String emergencyPoliciesPath) {
        this.breakGlassPoliciesPath = breakGlassPoliciesPath;
        this.emergencyPoliciesPath = emergencyPoliciesPath;
    }

    public void startHeadless() {
        IoTSEAR ioTSEAR = be.distrinet.spite.iotsear.IoTSEAR.getInstance();
        ioTSEAR.configure(new File(IoTSEARDataSource.class.getClassLoader().getResource("iotsear/config.json").getFile()));

          /*
        --- CRUDs
         */
        breakGlassDB = new BreakGlassDB();
        breakGlassPoliciesCRUD = new BreakGlassPoliciesDB();
        emergencyPoliciesCRUD = new EmergencyPoliciesDB();

        ((BreakGlassPoliciesDB) breakGlassPoliciesCRUD).init(new File(BreakGlassSystem.class.getClassLoader().getResource(breakGlassPoliciesPath).getFile()));
        ((EmergencyPoliciesDB) emergencyPoliciesCRUD).init(new File(BreakGlassSystem.class.getClassLoader().getResource(emergencyPoliciesPath).getFile()));

        breakGlassSystemStatus = breakGlassDB;
        emergencyContactsCRUD = breakGlassDB;
        emergencySystemStatus = breakGlassDB;

        /*
        --- Secure log
         */
        SecureLog log = new Log4JLogger();


        /*
        --- Notification
         */
        notificationPoint = new NotificationPointAll(emergencyContactsCRUD);
        NotificationDeliverer stdNotificationDeliverer = new StandardOutputDeliverer();
        NotificationDeliverer emailNotificationDeliverer = new EmailDeliverer();

        notificationPoint.registerDeliverer(stdNotificationDeliverer);
        //    notificationPoint.registerDeliverer(emailNotificationDeliverer);

        /*
        --- Policy resolvers
         */
        breakGlassPolicyResolver = new BreakGlassPolicyResolverSimple();
        emergencyPolicyResolver = new EmergencyPolicyResolverIoTSEAR(emergencyPoliciesCRUD);
        /*
        --- Policy activators
         */
        // Break Glass policies
        breakGlassPolicyActivationPoint = new BreakGlassPolicyActivationPoint(breakGlassPoliciesCRUD, breakGlassPolicyResolver);

        /*
         --- Context
         */
        /* sinks */
        emergencyDetectionPoint = new EmergencyDetectionPoint(emergencyPoliciesCRUD, emergencySystemStatus, (EmergencyNotificationPoint) notificationPoint,
                breakGlassPolicyActivationPoint, emergencyPolicyResolver, log);

        /* processor */
        contextProcessor = new ContextProcessorNoFilter();
        contextProcessor.registerSink(emergencyDetectionPoint);

        /* sources */
        //       MqttSensorData mqttSensorData = new MqttSensorData(contextProcessor);
        //       mqttSensorData.startSource();


        IoTSEARDataSource iotSearDatesource = new IoTSEARDataSource(contextProcessor, ioTSEAR.getContextSources());

        /*
        --- Non-repudiation requirement resolution
         */
        nonRepudiationRequirementsResolver = new NonRepudiationRequirementsResolverSimple();

        /*
         --- Non-repudiation
         */
        evidenceCollector = new EvidenceCollectorDefault(nonRepudiationRequirementsResolver);
        NonRepudiationProvider stdProvider = new NameInput();
        NonRepudiationProvider camProvider = new SecurityCamera();
        NonRepudiationProvider eidProvider = new ElectronicID();

        //     evidenceCollector.registerNonRepudiationProvider(stdProvider);
        evidenceCollector.registerNonRepudiationProvider(camProvider);
        evidenceCollector.registerNonRepudiationProvider(eidProvider);
/*
        PluginManager pluginManager = new DefaultPluginManager();
        pluginManager.loadPlugins();
        List<NonRepudiationProvider> providers = pluginManager.getExtensions(NonRepudiationProvider.class);
*/

        /*
        --- Decision point
         */
        breakGlassDecisionPoint = new BreakGlassDecisionPointStandard(breakGlassPoliciesCRUD);

        /*
        --- Awareness point
         */
        awarenessPoint = new AwarenessPointNoFilter();
        AwarenessProvider stdAwarenessProvider = new StandardOutputAwarenessProvider();
        AwarenessProvider audioAwarenessProvider = new AudioAwarenessProvider();
        AwarenessProvider screenAwarenessProvider = new ScreenAwarenessProvider();

        awarenessPoint.registerProvider(stdAwarenessProvider);
        //      awarenessPoint.registerProvider(audioAwarenessProvider);
        awarenessPoint.registerProvider(screenAwarenessProvider);

        /*
        --- BG Activators
         */
        BreakGlassActivator virtualButtonActivator = new VirtualButton();

        /*
        --- Evidence retention
         */
        EvidenceRetention fileRetainer = new FileNonRepudiationRetention();

        /*
        --- Enforcement point
         */
        breakGlassEnforcementPoint = new BreakGlassEnforcementPointStandard(
                awarenessPoint, (BreakGlassNotificationPoint) notificationPoint, breakGlassDecisionPoint,
                evidenceCollector, log, breakGlassSystemStatus, fileRetainer
        );
        ((BreakGlassActivationHandler) breakGlassEnforcementPoint).registerBreakGlassActivator(virtualButtonActivator);

        /*
        --- Configurators
         */
        BreakGlassConfigurator configurator = new BreakGlassConfigurator(breakGlassPoliciesCRUD, emergencyContactsCRUD, emergencyPoliciesCRUD);

    }

    public void start() {
        be.distrinet.spite.iotsear.IoTSEAR ioTSEAR = be.distrinet.spite.iotsear.IoTSEAR.getInstance();
        ioTSEAR.configure(new File(IoTSEARDataSource.class.getClassLoader().getResource("iotsear/config.json").getFile()));

          /*
        --- CRUDs
         */
        breakGlassDB = new BreakGlassDB();
        breakGlassPoliciesCRUD = new BreakGlassPoliciesDB();
        emergencyPoliciesCRUD = new EmergencyPoliciesDB();

        ((BreakGlassPoliciesDB) breakGlassPoliciesCRUD).init(new File(BreakGlassSystem.class.getClassLoader().getResource(breakGlassPoliciesPath).getFile()));
        ((EmergencyPoliciesDB) emergencyPoliciesCRUD).init(new File(BreakGlassSystem.class.getClassLoader().getResource(emergencyPoliciesPath).getFile()));

        breakGlassSystemStatus = breakGlassDB;
        emergencyContactsCRUD = breakGlassDB;
        emergencySystemStatus = breakGlassDB;

        /*
        --- Secure log
         */
        SecureLog log = new Log4JLogger();


        /*
        --- Notification
         */
        notificationPoint = new NotificationPointAll(emergencyContactsCRUD);
        NotificationDeliverer stdNotificationDeliverer = new StandardOutputDeliverer();
        NotificationDeliverer emailNotificationDeliverer = new EmailDeliverer();

        notificationPoint.registerDeliverer(stdNotificationDeliverer);
        //    notificationPoint.registerDeliverer(emailNotificationDeliverer);

        /*
        --- Policy resolvers
         */
        breakGlassPolicyResolver = new BreakGlassPolicyResolverSimple();
        emergencyPolicyResolver = new EmergencyPolicyResolverIoTSEAR(emergencyPoliciesCRUD);
        /*
        --- Policy activators
         */
        // Break Glass policies
        breakGlassPolicyActivationPoint = new BreakGlassPolicyActivationPoint(breakGlassPoliciesCRUD, breakGlassPolicyResolver);

        /*
         --- Context
         */
        /* sinks */
        emergencyDetectionPoint = new EmergencyDetectionPoint(emergencyPoliciesCRUD, emergencySystemStatus, (EmergencyNotificationPoint) notificationPoint,
                breakGlassPolicyActivationPoint, emergencyPolicyResolver, log);

        /* processor */
        contextProcessor = new ContextProcessorNoFilter();
        contextProcessor.registerSink(emergencyDetectionPoint);

        /* sources */
        //       MqttSensorData mqttSensorData = new MqttSensorData(contextProcessor);
        //       mqttSensorData.startSource();


        IoTSEARDataSource iotSearDatesource = new IoTSEARDataSource(contextProcessor, ioTSEAR.getContextSources());

        /*
        --- Non-repudiation requirement resolution
         */
        nonRepudiationRequirementsResolver = new NonRepudiationRequirementsResolverSimple();

        /*
         --- Non-repudiation
         */
        evidenceCollector = new EvidenceCollectorDefault(nonRepudiationRequirementsResolver);
        NonRepudiationProvider stdProvider = new NameInput();
        NonRepudiationProvider camProvider = new SecurityCamera();
        NonRepudiationProvider eidProvider = new ElectronicID();

        //     evidenceCollector.registerNonRepudiationProvider(stdProvider);
        evidenceCollector.registerNonRepudiationProvider(camProvider);
        evidenceCollector.registerNonRepudiationProvider(eidProvider);
/*
        PluginManager pluginManager = new DefaultPluginManager();
        pluginManager.loadPlugins();
        List<NonRepudiationProvider> providers = pluginManager.getExtensions(NonRepudiationProvider.class);
*/

        /*
        --- Decision point
         */
        breakGlassDecisionPoint = new BreakGlassDecisionPointStandard(breakGlassPoliciesCRUD);

        /*
        --- Awareness point
         */
        awarenessPoint = new AwarenessPointNoFilter();
        AwarenessProvider stdAwarenessProvider = new StandardOutputAwarenessProvider();
        AwarenessProvider audioAwarenessProvider = new AudioAwarenessProvider();
        AwarenessProvider screenAwarenessProvider = new ScreenAwarenessProvider();

        awarenessPoint.registerProvider(stdAwarenessProvider);
        //      awarenessPoint.registerProvider(audioAwarenessProvider);
        awarenessPoint.registerProvider(screenAwarenessProvider);

        /*
        --- BG Activators
         */
        BreakGlassActivator virtualButtonActivator = new VirtualButton();

        /*
        --- Evidence retention
         */
        EvidenceRetention fileRetainer = new FileNonRepudiationRetention();

        /*
        --- Enforcement point
         */
        breakGlassEnforcementPoint = new BreakGlassEnforcementPointStandard(
                awarenessPoint, (BreakGlassNotificationPoint) notificationPoint, breakGlassDecisionPoint,
                evidenceCollector, log, breakGlassSystemStatus, fileRetainer
        );
        ((BreakGlassActivationHandler) breakGlassEnforcementPoint).registerBreakGlassActivator(virtualButtonActivator);

        breakGlassEnforcementPoint.start();

        /*
        --- Demo Front-end
         */
        // IoTSEAR PEP
        PolicyEnforcementPoint policyEnforcementPoint = new PolicyEnforcementPointIoTSEAR(ioTSEAR, log);

        // Smart access devices
        new Thread(() -> {
           new SmartLock(policyEnforcementPoint);
        }).start();

        /*
        --- Configurators
         */
        BreakGlassConfigurator configurator = new BreakGlassConfigurator(breakGlassPoliciesCRUD, emergencyContactsCRUD, emergencyPoliciesCRUD);


        /*
        Sources starten
         */

        iotSearDatesource.startSource();


        /*
        --- IoTSEAR
         */
        // Gets setup in the IoTSEAR data source
        /*
        --- Web API
         */
        /*
        Emergency interruption
         */
        EmergencyInterruptController emergencyInterruptController = new EmergencyInterruptController(emergencyDetectionPoint);
        emergencyInterruptController.start();

        PatientSystemConfigurationController patientSystemConfigurationController = new PatientSystemConfigurationController(configurator, configurator, configurator, configurator);
        patientSystemConfigurationController.start();

        HomeController homeController = new HomeController();
        homeController.start();
    }

    public String getBreakGlassPoliciesPath() {
        return breakGlassPoliciesPath;
    }

    public String getEmergencyPoliciesPath() {
        return emergencyPoliciesPath;
    }

    public AwarenessPoint getAwarenessPoint() {
        return awarenessPoint;
    }

    public BreakGlassDecisionPoint getBreakGlassDecisionPoint() {
        return breakGlassDecisionPoint;
    }

    public BreakGlassEnforcementPoint getBreakGlassEnforcementPoint() {
        return breakGlassEnforcementPoint;
    }

    public BreakGlassPolicyActivationPoint getBreakGlassPolicyActivationPoint() {
        return breakGlassPolicyActivationPoint;
    }

    public ContextProcessor getContextProcessor() {
        return contextProcessor;
    }

    public EmergencyDetectionPoint getEmergencyDetectionPoint() {
        return emergencyDetectionPoint;
    }

    public EvidenceCollector getEvidenceCollector() {
        return evidenceCollector;
    }

    public EvidenceRetention getEvidenceRetention() {
        return evidenceRetention;
    }

    public NotificationPoint getNotificationPoint() {
        return notificationPoint;
    }

    public BreakGlassPolicyResolver getBreakGlassPolicyResolver() {
        return breakGlassPolicyResolver;
    }

    public EmergencyPolicyResolver getEmergencyPolicyResolver() {
        return emergencyPolicyResolver;
    }

    public NonRepudiationRequirementsResolver getNonRepudiationRequirementsResolver() {
        return nonRepudiationRequirementsResolver;
    }

    public EmergencyPoliciesCRUD getEmergencyPoliciesCRUD() {
        return emergencyPoliciesCRUD;
    }

    public BreakGlassPoliciesCRUD getBreakGlassPoliciesCRUD() {
        return breakGlassPoliciesCRUD;
    }

    public EmergencyContactsCRUD getEmergencyContactsCRUD() {
        return emergencyContactsCRUD;
    }

    public EmergencySystemStatus getEmergencySystemStatus() {
        return emergencySystemStatus;
    }

    public BreakGlassSystemStatus getBreakGlassSystemStatus() {
        return breakGlassSystemStatus;
    }

    public BreakGlassDB getBreakGlassDB() {
        return breakGlassDB;
    }

    public static BreakGlassSystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BreakGlassSystem();
        }
        return INSTANCE;
    }
}
