package com.pucmm.dhamarmj.Handlers;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class MainHandler {

    public void mainHandler() {
        startup();
    }

    public void startup() {

        staticFiles.location("/publico");

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(MainHandler.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

        get("/Home/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            //Look for the cookie here!

            attributes.put("userSigned", false);

            // look for the user role!
            attributes.put("usuario", "admin");
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);
        get("/LogIn/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "signIn.ftl");
        }, freeMarkerEngine);

        post("/logInUser/", (request, response) -> {

            //Get User from Db

            response.redirect("/Home/");
            return null;
        }, freeMarkerEngine);

        get("/Admin/CreateUser/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            // look for the user role!

            attributes.put("usuario", "admin");
            return new ModelAndView(attributes, "createUser.ftl");
        }, freeMarkerEngine);

        post("/Admin/saveUser/", (request, response) -> {

            //save User to Db

            response.redirect("/Home/");
            return null;
        }, freeMarkerEngine);

        get("/Admin/Compose/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            // look for the user role!

            attributes.put("usuario", "admin");
            return new ModelAndView(attributes, "compose.ftl");
        }, freeMarkerEngine);

        get("/Author/Compose/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            // look for the user role!

            attributes.put("usuario", "author");
            return new ModelAndView(attributes, "compose.ftl");
        }, freeMarkerEngine);


        post("/Admin/saveArticle/", (request, response) -> {

            //save User to Db

            response.redirect("/Home/");
            return null;
        }, freeMarkerEngine);

    }
}
