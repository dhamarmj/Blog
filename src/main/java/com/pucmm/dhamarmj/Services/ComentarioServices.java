package com.pucmm.dhamarmj.Services;

import com.pucmm.dhamarmj.Encapsulacion.Articulo;
import com.pucmm.dhamarmj.Encapsulacion.Comentario;
import com.pucmm.dhamarmj.Encapsulacion.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class ComentarioServices {

    private Sql2o db;
    private UsuarioServices usuarioServices;

    public ComentarioServices(UsuarioServices us) {
        db = DatabaseServices.getInstancia();
        usuarioServices = us;
    }

    public void startComentarios() {
        String sql = "delete from Comentario";
        try (Connection con = db.open()) {
            con.createQuery(sql).executeUpdate();
        }
//        for (int i=1; i<=10;i++){
//            crearComentario(new Comentario("comment" + i,i, i));
//        }
    }

    public List<Comentario> getComentario() {
        String sql = "select * from Comentario";
        try (Connection con = db.open()) {
             return con.createQuery(sql).executeAndFetch(Comentario.class);
        }
    }

    public List<Comentario> getComentario(Articulo articulo) {
        String sql = "select * from Comentario where articulo=:articulo";
        try (Connection con = db.open()) {
            List<Comentario> comments = con.createQuery(sql)
                    .addParameter("articulo", articulo.getId())
                    .executeAndFetch(Comentario.class);
            LoadComments(comments, articulo);
            return  comments;
        }
    }

    private void LoadComments(List<Comentario> comments, Articulo articulo) {
        for (Comentario c :
                comments) {
            c.setUsuarioAutor(usuarioServices.getUsuario(c.getAutor()));
            c.setArticuloComentario(articulo);
        }
    }

    public boolean crearComentario(Comentario comentario) {
        String sql = "insert into Comentario (comentario, autor, articulo) values (:comentario,:autor,:articulo)";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("comentario", comentario.getComentario())
                    .addParameter("autor", comentario.getAutor())
                    .addParameter("articulo", comentario.getArticulo())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Insert Comentario error: " + ex.getMessage());
            return false;
        }
    }

    public boolean updateComentario(Comentario comentario) {
        String sql = "update Comentario set comentario=:comentario, autor=:autor, articulo=:articulo where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("comentario", comentario.getComentario())
                    .addParameter("autor", comentario.getUsuarioAutor().getId())
                    .addParameter("articulo", comentario.getUsuarioAutor().getId())
                    .addParameter("id", comentario.getId())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Update Comentario error: " + ex.getMessage());
            return false;
        }
    }

    public boolean deleteComentario(int id) {
        String sql = "delete from Comentario where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Delete Comentario error: " + ex.getMessage());
            return false;
        }
    }
    public boolean  deleteComentario(Articulo articulo) {
        String sql = "delete from Comentario where articulo=:idarticulo";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("idarticulo", articulo.getId())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Delete Comentario error: " + ex.getMessage());
            return false;
        }
    }
}
