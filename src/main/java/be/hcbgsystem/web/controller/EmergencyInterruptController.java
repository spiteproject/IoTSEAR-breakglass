package be.hcbgsystem.web.controller;

import be.hcbgsystem.core.breakglass.interruption.UserInterruption;
import be.hcbgsystem.core.breakglass.interruption.UserInterruptionResult;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class EmergencyInterruptController implements APIController {
    private UserInterruption toInterrupt;

    public EmergencyInterruptController(UserInterruption toInterrupt) {
        this.toInterrupt = toInterrupt;
    }

    private void registerRoutes() {
        post("/emergency/interrupt", (request, response) -> {
            UserInterruptionResult res = toInterrupt.interruptActivation();
            if (res == UserInterruptionResult.INTERRUPTED) {
                return "Interrupted";
            } else if (res == UserInterruptionResult.NOTHING_TO_INTERRUPT) {
                return "Nothing to interrupt";
            } else if (res == UserInterruptionResult.COULD_NOT_INTERRUPT) {
                return "Could not interrupt";
            }
            return halt(500);
        });

        get("/emergency/interrupt", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("event", toInterrupt.getInterruptableEvents());

            return new ModelAndView(model, "templates/patient/interrupt-emergency.vm");
        }, new VelocityTemplateEngine());
    }

    @Override
    public void start() {
        registerRoutes();
    }
}
