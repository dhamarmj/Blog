package com.pucmm.dhamarmj.Handlers;

import com.pucmm.dhamarmj.Encapsulacion.*;
import com.pucmm.dhamarmj.Services.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Request;
import spark.Session;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Time;
import java.util.*;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static spark.Spark.*;

public class MainHandler {

    public void mainHandler() {

        encryption = new Encryption();
        articuloServices = new ArticuloServices();
        usuarioServices = new UsuarioServices();
        comentarioServices = new ComentarioServices();
        etiquetaServices = new EtiquetaServices();
        articuloEtiquetaService = new ArticuloEtiquetaService();
        startup();

    }
    //MISSING: COOKIE CREATED AND SECURE PERO NO SE QUE HACER CON ELLA

    ArticuloServices articuloServices;
    ArticuloEtiquetaService articuloEtiquetaService;
    UsuarioServices usuarioServices;
    ComentarioServices comentarioServices;
    EtiquetaServices etiquetaServices;
    boolean userSigned = false;
    Usuario currentUser;
    String KeyText = "Practica3WebClass";
    Encryption encryption;

    public void startup() {
        staticFiles.location("/publico");

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(MainHandler.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);


        get("/Home/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            List<Articulo> Articles = articuloServices.getArticulo();
            for (Articulo a:
                 Articles) {
                System.out.println(a.getTitulo());
            }
            attributes.put("list", Articles);


            String user = request.cookie("LoginU");
            if (user != null) {
                String passw = encryption.Decrypt(request.cookie("LoginP"));
                String usern = encryption.Decrypt(user);
                currentUser = usuarioServices.getUsuario(usern, passw, false);
                CreateSession(request, currentUser);
            }
            currentUser = getSessionUsuario(request);
            if (currentUser != null) {
                attributes = validateUSer();
                attributes.put("userSigned", true);
            } else {
                attributes.put("usuario", "other");
                attributes.put("userSigned", false);
            }
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        before("/Admin/*", (request, response) -> {
            Usuario usuario = request.session().attribute("usuario");
            if (usuario == null) {
                response.redirect("/Home/");
            } else {
                if (!usuario.isAdmin()) {
                    response.redirect("/Home/");
                }
            }
        });
        before("/Author/*", (request, response) -> {
            Usuario usuario = request.session().attribute("usuario");
            if (usuario == null) {
                response.redirect("/Home/");
            } else {
                if (!usuario.isAutor()) {
                    response.redirect("/Home/");
                }
            }
        });
        get("/LogIn/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "signIn.ftl");
        }, freeMarkerEngine);

        post("/logInUser/", (request, response) -> {
            Usuario user = usuarioServices.getUsuario(request.queryParams("username"), request.queryParams("password"), true);
            if (user != null) {
                CreateSession(request, user);
                boolean remember = Boolean.parseBoolean(request.queryParams("remember"));
                if (remember) {
                    response.cookie("/", "LoginU", encryption.Encrypt(user.getUsername()), 604800, false);
                    response.cookie("/", "LoginP", encryption.Encrypt(user.getPassword()), 604800, false);
                }
                response.redirect("/Home/");
            } else {
                response.redirect("/LogIn/");
            }
            return null;
        }, freeMarkerEngine);

        get("/Admin/CreateUser/", (request, response) -> {
            Map<String, Object> attributes = validateUSer();
            return new ModelAndView(attributes, "createUser.ftl");
        }, freeMarkerEngine);

        post("/Admin/saveUser/", (request, response) -> {
            usuarioServices.crearUsuario(new Usuario(request.queryParams("name"),
                    request.queryParams("username"),
                    request.queryParams("pass"),
                    Boolean.parseBoolean(request.queryParams("adminVal")),
                    Boolean.parseBoolean(request.queryParams("authorVal"))));
            response.redirect("/Home/");
            return null;
        }, freeMarkerEngine);

        get("/Admin/Compose/", (request, response) -> {
            Map<String, Object> attributes = validateUSer();
            return new ModelAndView(attributes, "compose.ftl");
        }, freeMarkerEngine);

        get("/Author/Compose/", (request, response) -> {
            Map<String, Object> attributes = validateUSer();
            return new ModelAndView(attributes, "compose.ftl");
        }, freeMarkerEngine);

        post("/saveArticle/", (request, response) -> {
            List<Etiqueta> listE = getEtiquetas(request.queryParams("etiquetas"));
            Articulo Art = new Articulo(request.queryParams("title"),
                    request.queryParams("body").trim(),
                    new Date(),
                    new ArrayList<>(),
                    listE,
                    currentUser);
            Articulo a = articuloServices.crearArticulo(Art);
            if(a != null)
                saveEtiquetaArticulo(a, listE);
            response.redirect("/Home/");
            return null;
        }, freeMarkerEngine);

        get("/LogOut/", (request, response) -> {
            request.session().removeAttribute("usuario");
            request.session().invalidate();
            response.removeCookie("/", "LoginU");
            response.removeCookie("/", "LoginP");
            response.redirect("/Home/");
            return null;
        }, freeMarkerEngine);

    }

    private void CreateSession(Request request, Usuario user) {
        Session session = request.session(true);
        session.attribute("usuario", user);
    }
    private Usuario getSessionUsuario(Request request) {
        return request.session().attribute("usuario");
    }

    private Map<String, Object> validateUSer() {
        Map<String, Object> attributes = new HashMap<>();
        if (currentUser.isAdmin()) {
            attributes.put("usuario", "admin");
        } else if (currentUser.isAutor()) {
            attributes.put("usuario", "author");
        } else {
            attributes.put("usuario", "visitor");
        }
        return attributes;
    }

    private List<Etiqueta> getEtiquetas(String values) {
        if(values.equalsIgnoreCase(""))
            return  null;

        List<Etiqueta> list = new ArrayList<>();
        Etiqueta E;
        String[] et = values.split(",");
        for (String e :
                et) {
            E = etiquetaServices.crearEtiqueta(new Etiqueta(e));
            list.add(E);
        }
        return list;
    }

    private void saveEtiquetaArticulo(Articulo art, List<Etiqueta> list) {
        for (Etiqueta e :
                list) {
            articuloEtiquetaService.crearArticuloEtiqueta(new ArticuloEtiqueta(art, e));
        }
    }


}
