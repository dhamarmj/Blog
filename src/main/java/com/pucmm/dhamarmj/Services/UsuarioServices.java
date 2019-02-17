package com.pucmm.dhamarmj.Services;

import com.pucmm.dhamarmj.Encapsulacion.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.Random;
import java.util.List;

public class UsuarioServices {

    private Sql2o db;
    Random random;

    public UsuarioServices() {
        db = DatabaseServices.getInstancia();
        random = new Random();
    }

    public void startUsuarios(){
        String sql="delete from Usuario";
        try(Connection con = db.open()) {
            con.createQuery(sql).executeUpdate();
        }
            for (int i=1; i<=10;i++){
            crearUsuario(new Usuario("username" + i,"nombre"+i, "pass"+i,random.nextBoolean(),random.nextBoolean()));
        }
    }

    public List<Usuario> getUsuario() {
        String sql="select * from Usuario";
        try(Connection con = db.open()){
            return con.createQuery(sql).executeAndFetch(Usuario.class);
        }
    }

    public boolean crearUsuario(Usuario user) {
        String sql="insert into Usuario (username, nombre, password, admin, autor) values (:username,:nombre,:password,:admin,:autor)";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("username", user.getUsername())
                    .addParameter("nombre", user.getNombre())
                    .addParameter("password", user.getPassword())
                    .addParameter("admin", user.isAdmin())
                    .addParameter("autor", user.isAutor())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Insert Usuario error: " + ex.getMessage());
            return false;
        }

    }

    public boolean updateUsuario(Usuario user) {
        String sql="update Usuario set username=:username, nombre=:nombre, password=:password , admin=:admin, autor=:autor where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("username", user.getUsername())
                    .addParameter("nombre", user.getNombre())
                    .addParameter("password", user.getPassword())
                    .addParameter("admin", user.isAdmin())
                    .addParameter("autor", user.isAutor())
                    .addParameter("id", user.getId())
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Update Usuario error: " + ex.getMessage());
            return false;
        }

    }

    public boolean deleteUsuario(int id) {
        String sql="delete from Usuario where id=:id";
        try(Connection con = db.open()){
            con.createQuery(sql)
                    .addParameter("id", id);
            return true;
        }
        catch (Exception ex){
         System.out.println("Delete Usuario error: " + ex.getMessage());
         return false;
        }
    }

}
