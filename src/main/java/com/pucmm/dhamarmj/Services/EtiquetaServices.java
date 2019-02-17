package com.pucmm.dhamarmj.Services;

import com.pucmm.dhamarmj.Encapsulacion.Etiqueta;
import com.pucmm.dhamarmj.Encapsulacion.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;


public class EtiquetaServices {
    private Sql2o db;

    public EtiquetaServices() {
        db = DatabaseServices.getInstancia();
    }

    public void startEtiqueta(){
        String sql="delete from Etiqueta";
        try(Connection con = db.open()) {
            con.createQuery(sql).executeUpdate();
        }
        for (int i=1; i<=10;i++){
            crearEtiqueta(new Etiqueta("Etiqueta" + i));
        }
    }

    public List<Etiqueta> getEtiqueta() {
        String sql="select * from Usuario";
        try(Connection con = db.open()){
            return con.createQuery(sql).executeAndFetch(Etiqueta.class);
        }
    }

    public boolean crearEtiqueta(Etiqueta etiqueta) {
        String sql="insert into Etiqueta (etiqueta) values (:etiqueta)";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("etiqueta", etiqueta.getEtiqueta())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Insert Etiqueta error: " + ex.getMessage());
            return false;
        }

    }

    public boolean updateEtiqueta(Etiqueta etiqueta) {
        String sql="update Etiqueta set etiqueta=:etiqueta where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("etiqueta", etiqueta.getEtiqueta())
                    .addParameter("id", etiqueta.getId())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Update Etiqueta error: " + ex.getMessage());
            return false;
        }

    }

    public boolean deleteEtiqueta(int id) {
        String sql="delete from Etiqueta where id=:id";
        try(Connection con = db.open()){
            con.createQuery(sql)
                    .addParameter("id", id);
            return true;
        }
        catch (Exception ex){
            System.out.println("Delete Etiqueta error: " + ex.getMessage());
            return false;
        }
    }
}
