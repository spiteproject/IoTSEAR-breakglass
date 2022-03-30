package be.hcbgsystem.web.controller;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

public class HomeController implements APIController {
    @Override
    public void start() {
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            return new ModelAndView(model, "templates/home.vm");
        }, new VelocityTemplateEngine());
    }
}
