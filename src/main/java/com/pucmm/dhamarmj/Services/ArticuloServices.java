package com.pucmm.dhamarmj.Services;

import com.pucmm.dhamarmj.Encapsulacion.Articulo;
import com.pucmm.dhamarmj.Encapsulacion.Etiqueta;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class ArticuloServices {
    private Sql2o db;
    Articulo A = null;
    UsuarioServices usuarioServices;
//    EtiquetaServices etiquetaServices;
//    ComentarioServices comentarioServices;
//    ArticuloEtiquetaService articuloEtiquetaService;

    public ArticuloServices() {

        db = DatabaseServices.getInstancia();
        usuarioServices = new UsuarioServices();
//        etiquetaServices = new EtiquetaServices();
//        comentarioServices = new ComentarioServices();
//        articuloEtiquetaService = new ArticuloEtiquetaService();
    }

    public void startArticulo() {
        String sql = "delete from Articulo";
        try (Connection con = db.open()) {
            con.createQuery(sql).executeUpdate();
        }
//        for (int i=1; i<=10;i++){
//            crearArticulo(new Articulo("titulo"+1, "cuerpo"+1,));
//        }
    }

    public List<Articulo> getArticulo() {
        String sql = "select * from Articulo";
        try (Connection con = db.open()) {
            List<Articulo> articles = con.createQuery(sql).executeAndFetch(Articulo.class);
            for (Articulo a:
                 articles) {
                a.startTeaser();
                a.setUsuarioautor(usuarioServices.getUsuario(a.getAutor()));
            }
            return articles;
        }catch(Exception ex){
            System.out.println("Get Articulo error: " + ex.toString());
            return null;
        }
    }

    public Articulo getArticulo(int idarticulo) {
        String sql = "select * from Articulo where id = :idarticulo";
        try (Connection con = db.open()) {
            return con.createQuery(sql)
                    .addParameter("idarticulo", idarticulo)
                    .executeAndFetchFirst(Articulo.class);
        }
        catch(Exception ex){
            System.out.println("Get Articulo error: " + ex.toString());
            return null;
        }
    }

    public Articulo getArticulo(String title) {
        String sql = "select * from Articulo where titulo = :title";
        try (Connection con = db.open()) {
         return con.createQuery(sql)
                    .addParameter("title", title)
                    .executeAndFetchFirst(Articulo.class);
        }
        catch(Exception ex){
            System.out.println("Get Articulo error: " + ex);
            return null;
        }
    }

    public Articulo crearArticulo(Articulo articulo) {
        String sql = "insert into Articulo (titulo, cuerpo, autor, fecha) values (:titulo, :cuerpo, :autor, :fecha)";
            try (Connection con = db.open()) {
                con.createQuery(sql)
                        .addParameter("titulo", articulo.getTitulo())
                        .addParameter("cuerpo", articulo.getCuerpo())
                        .addParameter("autor", articulo.getAutor())
                        .addParameter("fecha", articulo.getFecha())
                        .executeUpdate();
                A = getArticulo(articulo.getTitulo());
                return A;
            } catch (Exception ex) {
                System.out.println("Insert Articulo error: " + ex.getMessage());
                return null;
            }
        }

    public boolean updateArticulo(Articulo articulo) {
        String sql = "update Articulo set titulo=:titulo, cuerpo=:cuerpo, autor=:autor, fecha=:fecha where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("titulo", articulo.getTitulo())
                    .addParameter("cuerpo", articulo.getCuerpo())
                    .addParameter("autor", articulo.getAutor())
                    .addParameter("fecha", articulo.getFecha())
                    .addParameter("id", articulo.getId())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Update Articulo error: " + ex.getMessage());
            return false;
        }

    }

    public boolean deleteArticulo(int id) {
        String sql = "delete from Articulo where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("id", id);
            return true;
        } catch (Exception ex) {
            System.out.println("Delete Articulo error: " + ex.getMessage());
            return false;
        }
    }
}
