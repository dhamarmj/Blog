package com.pucmm.dhamarmj.Services;

import com.pucmm.dhamarmj.Encapsulacion.Comentario;
import com.pucmm.dhamarmj.Encapsulacion.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Random;

public class ComentarioServices {

    private Sql2o db;

    public ComentarioServices() {
        db = DatabaseServices.getInstancia();
    }

    public void startComentarios(){
        String sql="delete from Comentario";
        try(Connection con = db.open()) {
            con.createQuery(sql).executeUpdate();
        }
//        for (int i=1; i<=10;i++){
//            crearComentario(new Comentario("comment" + i,i, i));
//        }
    }

    public List<Comentario> getComentario() {
        String sql="select * from Comentario";
        try(Connection con = db.open()){
            return con.createQuery(sql).executeAndFetch(Comentario.class);
        }
    }

    public boolean crearComentario(Comentario comentario) {
        String sql="insert into Comentario (comentario, autor, articulo) values (:comentario,:autor,:articulo)";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("comentario", comentario.getComentario())
                    .addParameter("autor", comentario.getAutor().getId())
                    .addParameter("articulo", comentario.getAutor().getId())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Insert Comentario error: " + ex.getMessage());
            return false;
        }
    }

    public boolean updateComentario(Comentario comentario) {
        String sql="update Comentario set comentario=:comentario, autor=:autor, articulo=:articulo where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("comentario", comentario.getComentario())
                    .addParameter("autor", comentario.getAutor().getId())
                    .addParameter("articulo", comentario.getAutor().getId())
                    .addParameter("id", comentario.getId())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Update Comentario error: " + ex.getMessage());
            return false;
        }
    }

    public boolean deleteComentario(int id) {
        String sql="delete from Comentario where id=:id";
        try(Connection con = db.open()){
            con.createQuery(sql)
                    .addParameter("id", id);
            return true;
        }
        catch (Exception ex){
            System.out.println("Delete Comentario error: " + ex.getMessage());
            return false;
        }
    }
}
