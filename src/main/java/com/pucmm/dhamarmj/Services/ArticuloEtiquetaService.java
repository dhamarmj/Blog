package com.pucmm.dhamarmj.Services;

import com.pucmm.dhamarmj.Encapsulacion.Articulo;
import com.pucmm.dhamarmj.Encapsulacion.ArticuloEtiqueta;
import com.pucmm.dhamarmj.Encapsulacion.Etiqueta;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class ArticuloEtiquetaService {
    private Sql2o db;
    ArticuloEtiqueta AE;
    EtiquetaServices etiquetaServices;
    ArticuloServices articuloServices;

    public ArticuloEtiquetaService() {
        db = DatabaseServices.getInstancia();
        etiquetaServices = new EtiquetaServices();
        articuloServices = new ArticuloServices();
    }

    public List<ArticuloEtiqueta> getArticuloEtiqueta() {
        String sql = "select * from ArticuloEtiqueta";
        try (Connection con = db.open()) {
            List<ArticuloEtiqueta> AE = con.createQuery(sql).executeAndFetch(ArticuloEtiqueta.class);
            for (ArticuloEtiqueta ae :
                    AE) {
                loadArticuloEtiqueta(ae);
            }
            return AE;
        }
    }

    public ArticuloEtiqueta getArticuloEtiqueta(long a, long b) {
        String sql = "select * from ArticuloEtiqueta where idarticulo=:idarticulo and idetiqueta=:idetiqueta";
        try (Connection con = db.open()) {
            ArticuloEtiqueta ae = con.createQuery(sql)
                    .addParameter("idarticulo", a)
                    .addParameter("idetiqueta", b)
                    .executeAndFetchFirst(ArticuloEtiqueta.class);
            loadArticuloEtiqueta(ae);
            return ae;
        }
    }

    private void loadArticuloEtiqueta(ArticuloEtiqueta ae) {
        ae.setEtiqueta(etiquetaServices.getEtiqueta(ae.getIdetiqueta()));
        ae.setArticulo(articuloServices.getArticulo((int) ae.getIdarticulo()));
    }


    public ArticuloEtiqueta crearArticuloEtiqueta(ArticuloEtiqueta articuloEtiqueta) {
        String sql = "insert into ArticuloEtiqueta (idarticulo, idetiqueta) values (:idarticulo, :idetiqueta)";
        AE = getArticuloEtiqueta(articuloEtiqueta.getArticulo().getId(), articuloEtiqueta.getEtiqueta().getId());
        if (AE == null) {
            try (Connection con = db.open()) {
                con.createQuery(sql)
                        .addParameter("idarticulo", articuloEtiqueta.getArticulo().getId())
                        .addParameter("idetiqueta", articuloEtiqueta.getEtiqueta().getId())
                        .executeUpdate();
                AE = getArticuloEtiqueta(articuloEtiqueta.getArticulo().getId(), articuloEtiqueta.getEtiqueta().getId());
                return AE;
            } catch (Exception ex) {
                System.out.println("Insert ArticuloEtiqueta error: " + ex.getMessage());
                return null;
            }
        } else {
            System.out.println("NULL");
            return AE;

        }

    }

    public boolean updateArticuloEtiqueta(ArticuloEtiqueta etiqueta) {
        String sql = "update ArticuloEtiqueta set idetiqueta=:idetiqueta, idarticulo=:idarticulo where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("idetiqueta", etiqueta.getEtiqueta().getId())
                    .addParameter("idarticulo", etiqueta.getArticulo().getId())
                    .addParameter("id", etiqueta.getId())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Update ArticuloEtiqueta error: " + ex.getMessage());
            return false;
        }

    }

    public boolean deleteArticuloEtiqueta(int id) {
        String sql = "delete from ArticuloEtiqueta where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("id", id);
            return true;
        } catch (Exception ex) {
            System.out.println("Delete ArticuloEtiqueta error: " + ex.getMessage());
            return false;
        }
    }
}
