package com.pucmm.dhamarmj.Services;

import com.pucmm.dhamarmj.Encapsulacion.Articulo;
import com.pucmm.dhamarmj.Encapsulacion.ArticuloEtiqueta;
import com.pucmm.dhamarmj.Encapsulacion.Etiqueta;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArticuloServices {
    private Sql2o db;
    Articulo A = null;
    UsuarioServices usuarioServices;
    EtiquetaServices  etiquetaServices;

//    public  ArticuloServices() {
//
//        db = DatabaseServices.getInstancia();
//        usuarioServices = new UsuarioServices();
//        etiquetaServices = new EtiquetaServices();
////        comentarioServices = new ComentarioServices();
//    }
    public  ArticuloServices(UsuarioServices us, EtiquetaServices et) {

        db = DatabaseServices.getInstancia();
        usuarioServices = us;
        etiquetaServices = et;
    }

    public List<Articulo> getArticulo() {
        String sql = "select * from Articulo";
        try (Connection con = db.open()) {
            List<Articulo> articles = con.createQuery(sql).executeAndFetch(Articulo.class);
            for (Articulo a :
                    articles) {
                InitializeArticle(a);
            }
            Collections.sort(articles, (Articulo p1, Articulo p2) -> p2.getFecha().compareTo(p1.getFecha()));
            return articles;
        } catch (Exception ex) {
            System.out.println("Get Articulo error: " + ex.toString());
            return null;
        }
    }

    private void InitializeArticle(Articulo a) {
        a.startTeaser();
        a.setUsuarioautor(usuarioServices.getUsuario(a.getAutor()));
    }


    public Articulo getArticulo(long idarticulo) {
        String sql = "select * from Articulo where id = :idarticulo";
        try (Connection con = db.open()) {
            Articulo a= con.createQuery(sql)
                    .addParameter("idarticulo", idarticulo)
                    .executeAndFetchFirst(Articulo.class);
            InitializeArticle(a);
            return a;
        } catch (Exception ex) {
            System.out.println("Get Articulo error: " + ex.toString());
            return null;
        }
    }

    public Articulo getArticulo(String title) {
        String sql = "select * from Articulo where titulo = :title";
        try (Connection con = db.open()) {
            Articulo a= con.createQuery(sql)
                    .addParameter("title", title)
                    .executeAndFetchFirst(Articulo.class);
            InitializeArticle(a);
            return a;
        } catch (Exception ex) {
            System.out.println("Get Articulo error: " + ex);
            return null;
        }
    }


    public Articulo crearArticulo(Articulo articulo) {
        String sql = "insert into Articulo (titulo, texto, autor, fecha) values (:titulo, :cuerpo, :autor, :fecha)";
        Articulo A = getArticulo(articulo.getTitulo());
        if (A == null) {
            try (Connection con = db.open()) {
                con.createQuery(sql)
                        .addParameter("titulo", articulo.getTitulo())
                        .addParameter("cuerpo", articulo.getTexto())
                        .addParameter("autor", articulo.getAutor())
                        .addParameter("fecha", articulo.getFecha())
                        .executeUpdate();
                A = getArticulo(articulo.getTitulo());
                return A;
            } catch (Exception ex) {
                System.out.println("Insert Articulo error: " + ex.getMessage());
                return null;
            }
        } else {
            System.out.println(A.getTitulo());
            return A;
        }
    }

    public boolean updateArticulo(Articulo articulo) {
        String sql = "update Articulo set titulo=:titulo, texto=:cuerpo where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("titulo", articulo.getTitulo())
                    .addParameter("cuerpo", articulo.getTexto())
                    .addParameter("id", articulo.getId())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Update Articulo error: " + ex.getMessage());
            return false;
        }

    }

    public boolean deleteArticulo(long id) {
        String sql = "delete from Articulo where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Delete Articulo error: " + ex.getMessage());
            return false;
        }
    }
}
