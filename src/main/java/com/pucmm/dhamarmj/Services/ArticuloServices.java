package com.pucmm.dhamarmj.Services;

import com.pucmm.dhamarmj.Encapsulacion.Articulo;
import com.pucmm.dhamarmj.Encapsulacion.Etiqueta;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class ArticuloServices {
    private Sql2o db;

    public ArticuloServices() {
        db = DatabaseServices.getInstancia();
    }

    public void startArticulo(){
        String sql="delete from Articulo";
        try(Connection con = db.open()) {
            con.createQuery(sql).executeUpdate();
        }
//        for (int i=1; i<=10;i++){
//            crearArticulo(new Articulo("titulo"+1, "Texto"+1,));
//        }
    }

    public List<Articulo> getArticulo() {
        String sql="select * from Articulo";
        try(Connection con = db.open()){
            return con.createQuery(sql).executeAndFetch(Articulo.class);
        }
    }

    public boolean crearArticulo(Articulo articulo) {
        String sql="insert into Articulo (titulo, texto, autor, fecha) values (:titulo, :texto, :autor, :fecha)";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("titulo",articulo.getTitulo() )
                    .addParameter("texto",articulo.getCuerpo() )
                    .addParameter("autor",articulo.getAutor().getId())
                    .addParameter("fecha",articulo.getFecha())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Insert Etiqueta error: " + ex.getMessage());
            return false;
        }

    }

    public boolean updateArticulo(Articulo articulo) {
        String sql="update Articulo set titulo=:titulo, texto=:texto, autor=:autor, fecha=:fecha where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("titulo",articulo.getTitulo() )
                    .addParameter("texto",articulo.getCuerpo() )
                    .addParameter("autor",articulo.getAutor().getId())
                    .addParameter("fecha",articulo.getFecha())
                    .addParameter("id",articulo.getId())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Update Articulo error: " + ex.getMessage());
            return false;
        }

    }

    public boolean deleteArticulo(int id) {
        String sql="delete from Articulo where id=:id";
        try(Connection con = db.open()){
            con.createQuery(sql)
                    .addParameter("id", id);
            return true;
        }
        catch (Exception ex){
            System.out.println("Delete Articulo error: " + ex.getMessage());
            return false;
        }
    }
}
