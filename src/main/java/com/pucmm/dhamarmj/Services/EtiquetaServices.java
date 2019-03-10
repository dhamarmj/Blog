package com.pucmm.dhamarmj.Services;

import com.pucmm.dhamarmj.Encapsulacion.ArticuloEtiqueta;
import com.pucmm.dhamarmj.Encapsulacion.Etiqueta;
import com.pucmm.dhamarmj.Encapsulacion.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;


public class EtiquetaServices {
    private Sql2o db;
    Etiqueta et;
    public EtiquetaServices() {
        db = DatabaseServices.getInstancia();
    }

    public void startEtiqueta() {
        String sql = "delete from Etiqueta";
        try (Connection con = db.open()) {
            con.createQuery(sql).executeUpdate();
        }
        for (int i = 1; i <= 10; i++) {
            crearEtiqueta(new Etiqueta("Etiqueta" + i));
        }
    }

    public Etiqueta getEtiqueta(String etiqueta) {
        String sql = "select * from Etiqueta where etiqueta =:etiqueta";
        try (Connection con = db.open()) {
            return con.createQuery(sql).addParameter("etiqueta", etiqueta)
                    .executeAndFetchFirst(Etiqueta.class);
        }
    }
    public Etiqueta getEtiqueta(long idetiqueta) {
        String sql = "select * from Etiqueta where id =:idetiqueta";
        try (Connection con = db.open()) {
            return con.createQuery(sql).addParameter("idetiqueta", idetiqueta)
                    .executeAndFetchFirst(Etiqueta.class);
        }
    }

    public List<Etiqueta> getEtiqueta() {
        String sql = "select * from Usuario";
        try (Connection con = db.open()) {
            return con.createQuery(sql).executeAndFetch(Etiqueta.class);
        }
    }

    public Etiqueta crearEtiqueta(Etiqueta etiqueta) {
        String desc = etiqueta.getEtiqueta().trim();
        etiqueta.setEtiqueta(desc.substring(0, 1).toUpperCase() + desc.substring(1));

        et = getEtiqueta(etiqueta.getEtiqueta());
        if (et == null){
            String sql = "insert into Etiqueta (etiqueta) values (:etiqueta)";
            try (Connection con = db.open()) {
                con.createQuery(sql)
                        .addParameter("etiqueta", etiqueta.getEtiqueta())
                        .executeUpdate();
                et = getEtiqueta(etiqueta.getEtiqueta());
                return et;
            } catch (Exception ex) {
                System.out.println("Insert Etiqueta error: " + ex.getMessage());
                return null;
            }
        }
        else
            return et;
    }

    public boolean updateEtiqueta(Etiqueta etiqueta) {
        String sql = "update Etiqueta set etiqueta=:etiqueta where id=:id";
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
        String sql = "delete from Etiqueta where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("id", id);
            return true;
        } catch (Exception ex) {
            System.out.println("Delete Etiqueta error: " + ex.getMessage());
            return false;
        }
    }
}
