package be.hcbgsystem.web.controller;

import be.hcbgsystem.core.configuration.BreakGlassPolicyConfiguration;
import be.hcbgsystem.core.configuration.EmergencyContactsConfiguration;
import be.hcbgsystem.core.configuration.EmergencyPolicyConfiguration;
import be.hcbgsystem.core.configuration.PolicyConsent;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class PatientSystemConfigurationController implements APIController {
    private PolicyConsent policyConsent;
    private EmergencyContactsConfiguration emergencyContactsConfiguration;
    private EmergencyPolicyConfiguration emergencyPolicyConfiguration;
    private BreakGlassPolicyConfiguration breakGlassPolicyConfiguration;

    public PatientSystemConfigurationController(PolicyConsent policyConsent, EmergencyContactsConfiguration emergencyContactsConfiguration, EmergencyPolicyConfiguration emergencyPolicyConfiguration, BreakGlassPolicyConfiguration breakGlassPolicyConfiguration) {
        this.policyConsent = policyConsent;
        this.emergencyContactsConfiguration = emergencyContactsConfiguration;
        this.emergencyPolicyConfiguration = emergencyPolicyConfiguration;
        this.breakGlassPolicyConfiguration = breakGlassPolicyConfiguration;
    }

    @Override
    public void start() {
        get("/emergencycontacts/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("contacts", emergencyContactsConfiguration.getEmergencyContacts());

            return new ModelAndView(model, "templates/patient/emergencycontacts-overview.vm");
        }, new VelocityTemplateEngine());

        get("/policies/emergency/consent/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("policies", emergencyPolicyConfiguration.getEmergencyPolicies());

            return new ModelAndView(model, "templates/patient/emergencypolicy-consent.vm");
        }, new VelocityTemplateEngine());

        post("/policies/emergency/consent/accept", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("policies", emergencyPolicyConfiguration.getEmergencyPolicies());

            EmergencyPolicy policy = emergencyPolicyConfiguration.getEmergencyPolicy(req.queryParams("policy"));
            policyConsent.giveConsent(policy, true);

            return new ModelAndView(model, "templates/patient/emergencypolicy-consent.vm");
        }, new VelocityTemplateEngine());

        post("/policies/emergency/consent/reject", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("policies", emergencyPolicyConfiguration.getEmergencyPolicies());

            EmergencyPolicy policy = emergencyPolicyConfiguration.getEmergencyPolicy(req.queryParams("policy"));
            policyConsent.giveConsent(policy, false);

            return new ModelAndView(model, "templates/patient/emergencypolicy-consent.vm");
        }, new VelocityTemplateEngine());

        get("/policies/breakglass/consent/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("policies", breakGlassPolicyConfiguration.getBreakGlassPolicies());

            return new ModelAndView(model, "templates/patient/breakglasspolicy-consent.vm");
        }, new VelocityTemplateEngine());

        post("/policies/breakglass/consent/accept", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("policies", breakGlassPolicyConfiguration.getBreakGlassPolicies());

            BreakGlassPolicy policy = breakGlassPolicyConfiguration.getBreakGlassPolicy(req.queryParams("policy"));
            policyConsent.giveConsent(policy, true);

            return new ModelAndView(model, "templates/patient/breakglasspolicy-consent.vm");
        }, new VelocityTemplateEngine());

        post("/policies/breakglass/consent/reject", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("policies", breakGlassPolicyConfiguration.getBreakGlassPolicies());

            BreakGlassPolicy policy = breakGlassPolicyConfiguration.getBreakGlassPolicy(req.queryParams("policy"));
            policyConsent.giveConsent(policy, false);

            return new ModelAndView(model, "templates/patient/breakglasspolicy-consent.vm");
        }, new VelocityTemplateEngine());
    }
}
