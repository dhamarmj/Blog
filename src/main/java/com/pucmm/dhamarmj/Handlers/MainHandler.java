package com.pucmm.dhamarmj.Handlers;

import com.pucmm.dhamarmj.Encapsulacion.*;
import com.pucmm.dhamarmj.Services.*;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Request;
import spark.Session;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;


import static spark.Spark.*;

public class MainHandler {

    public void mainHandler() {

        encryption = new Encryption();
        usuarioServices = new UsuarioServices();
        etiquetaServices = new EtiquetaServices();
        comentarioServices = new ComentarioServices(usuarioServices);
        articuloEtiquetaService = new ArticuloEtiquetaService(etiquetaServices, articuloServices);
        articuloServices = new ArticuloServices(usuarioServices, etiquetaServices);
        startup();
    }

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
            List<Articulo> articulo = articuloServices.getArticulo();
            LoadEtiquetas(articulo);
            String user = request.cookie("LoginU");
            if (user != null) {
                String passw = encryption.Decrypt(request.cookie("LoginP"));
                String usern = encryption.Decrypt(user);
                currentUser = usuarioServices.getUsuario(usern, passw, false);
                CreateSession(request, currentUser);
            }
            currentUser = getSessionUsuario(request);
            Map<String, Object> attributes = validateUSer();
            if (currentUser != null) {
                attributes.put("currentUserId", String.valueOf(currentUser.getId()));
            } else {
                attributes.put("currentUserId", "0");
            }
            attributes.put("list", articulo);
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        get("/Post/:idpost", (request, response) -> {
            Map<String, Object> attributes = getArticle(request);
            return new ModelAndView(attributes, "articleDetail.ftl");
        }, freeMarkerEngine);

        post("/sendComment/:idpost", (request, response) -> {
            Articulo Ar = articuloServices.getArticulo(Integer.parseInt(request.params("idpost")));
            Comentario C = new Comentario(request.queryParams("comment").trim(),
                    currentUser, Ar);
            comentarioServices.crearComentario(C);
            response.redirect("/Post/"+Ar.getId());
            return null;
        }, freeMarkerEngine);

        get("/Admin/Modify/:idpost", (request, response) -> {
            Map<String, Object> attributes = getArticle(request);
            attributes.remove("list");
            return new ModelAndView(attributes, "modifyPost.ftl");
        }, freeMarkerEngine);
        get("/Author/Modify/:idpost", (request, response) -> {
            Map<String, Object> attributes = getArticle(request);
            attributes.remove("list");
            return new ModelAndView(attributes, "modifyPost.ftl");
        }, freeMarkerEngine);

        post("/updateArticle/:idpost", (request, response) -> {
            Map<String, Object> attributes = validateUSer();
            Articulo Ar = articuloServices.getArticulo(Integer.parseInt(request.params("idpost")));
            Ar.setCuerpo(request.queryParams("body"));
            Ar.setTitulo(request.queryParams("title"));
            Ar.startTeaser();
            LoadEtiquetasInArticulo(Ar);

            if (Ar != null)
                Ar.setListaEtiquetas(ModifyEtiquetasFromArticle(Ar, request.queryParams("etiquetas")));

            articuloServices.updateArticulo(Ar);
            response.redirect("/Home/");
            return null;
        }, freeMarkerEngine);

//        get("/deleteArticle/:idpost", (request, response) -> {
//            Map<String, Object> attributes = validateUSer();
//            Articulo Ar = articuloServices.getArticulo(Integer.parseInt(request.params("idpost")));
//            ModifyEtiquetasFromArticle(Ar, "");
//            articuloServices.deleteArticulo(Ar.getId());
//            response.redirect("/Home/");
//            return null;
//        }, freeMarkerEngine);

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
            return new ModelAndView(null, "signIn.ftl");
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

        get("/removeArticle/:idpost", (request, response) -> {
            Articulo art = articuloServices.getArticulo(Integer.parseInt(request.params("idpost")));
            comentarioServices.deleteComentario(art);
            articuloEtiquetaService.deleteArticuloEtiqueta(art);
            articuloServices.deleteArticulo(art.getId());
            response.redirect("/Home/");
            return null;
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
            if (a != null && listE != null) {
                saveEtiquetaArticulo(a, listE);
            }

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

    private Map<String, Object> getArticle(Request request) {
        Map<String, Object> attributes = validateUSer();
        Articulo Ar = articuloServices.getArticulo(Integer.parseInt(request.params("idpost")));
        Ar.setListaComentarios(comentarioServices.getComentario(Ar));
        LoadEtiquetasInArticulo(Ar);
        attributes.put("articulo", Ar);
        attributes.put("list", Ar.getListaComentarios());
        return attributes;
    }

    private void CreateSession(Request request, Usuario user) {
        Session session = request.session(true);
        session.attribute("usuario", user);
    }

    private List<Etiqueta> ModifyEtiquetasFromArticle(Articulo A, String values) {
        if (values.isEmpty()) {
            if (A.getListaEtiquetas() != null)
                articuloEtiquetaService.deleteArticuloEtiqueta(A);
            return null;
        } else {
            articuloEtiquetaService.deleteArticuloEtiqueta(A);
            List<Etiqueta> valuesEtiquetas = getEtiquetas(values);
            saveEtiquetaArticulo(A, valuesEtiquetas);
            return valuesEtiquetas;
        }
    }

    private Usuario getSessionUsuario(Request request) {
        return request.session().attribute("usuario");
    }

    private Map<String, Object> validateUSer() {
        Map<String, Object> attributes = new HashMap<>();
        if (currentUser == null) {
            attributes.put("usuario", "other");
            attributes.put("userSigned", false);
            return attributes;
        }

        if (currentUser.isAdmin()) {
            attributes.put("usuario", "admin");
        } else if (currentUser.isAutor()) {
            attributes.put("usuario", "author");
        } else {
            attributes.put("usuario", "visitor");
        }
        attributes.put("userSigned", true);
        return attributes;
    }

    private List<Etiqueta> getEtiquetas(String values) {
        if (values.equalsIgnoreCase(""))
            return null;

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
            ArticuloEtiqueta ae = articuloEtiquetaService.crearArticuloEtiqueta(new ArticuloEtiqueta(art, e));
        }
    }

    private void LoadEtiquetasInArticulo(Articulo articulo) {
        List<ArticuloEtiqueta> ae = articuloEtiquetaService.getArticuloEtiqueta(articulo);
        List<Etiqueta> et;
        if (ae == null || ae.size() == 0)
            articulo.setListaEtiquetas(new ArrayList<>());
        else {
            et = new ArrayList<>();
            for (ArticuloEtiqueta artEt :
                    ae) {
                et.add(artEt.getEtiqueta());
            }
            articulo.setListaEtiquetas(et);
        }

    }

    private void LoadEtiquetas(List<Articulo> ListArticles) {
        for (Articulo article :
                ListArticles) {
            LoadEtiquetasInArticulo(article);
        }
    }
    private List<Comentario> LoadComentToArticle(Articulo articulo){
        List<Comentario> comments = comentarioServices.getComentario(articulo);
        for (Comentario c:
           comments) {
           c.setArticuloComentario(articulo);
           c.setUsuarioAutor(usuarioServices.getUsuario(c.getAutor()));
        }

        return  comments;
    }
}

