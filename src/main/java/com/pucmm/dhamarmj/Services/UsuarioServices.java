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

    public void startUsuarios() {
        String sql = "delete from Usuario";
        String passw;
        try (Connection con = db.open()) {
            con.createQuery(sql).executeUpdate();
        }
        for (int i = 1; i <= 10; i++) {
            passw = encryptPassword("pass" + i);
            crearUsuario(new Usuario("username" + i, "nombre" + i, passw, random.nextBoolean(), random.nextBoolean()));
        }
    }

    public List<Usuario> getUsuario() {
        String sql = "select * from Usuario";
        try (Connection con = db.open()) {
            return con.createQuery(sql).executeAndFetch(Usuario.class);
        }
    }

    public void showUsuario() {
        List<Usuario> users;
        String sql = "select * from Usuario";
        try (Connection con = db.open()) {
            users = con.createQuery(sql).executeAndFetch(Usuario.class);
        }
        for (Usuario usuario:
             users) {
            System.out.println(usuario.getUsername() + " " + usuario.getPassword());
        }
    }



    public Usuario getUsuario(int idusuario) {
        String sql = "select * from Usuario where id=:idusuario";
        try (Connection con = db.open()) {
            return con.createQuery(sql)
                    .addParameter("idusuario", idusuario)
                    .executeAndFetchFirst(Usuario.class);
        }
    }

    public Usuario getUsuario(String username, String password, boolean encrypt) {
        String pass = password;
        if (encrypt)
            pass = encryptPassword(password);
        String sql = "select * from Usuario where username=:username and password=:pass";
        try (Connection con = db.open()) {
            return con.createQuery(sql)
                    .addParameter("username", username)
                    .addParameter("pass", pass)
                    .executeAndFetchFirst(Usuario.class);
        }
    }

    public boolean crearUsuario(Usuario user) {
        String pass = encryptPassword(user.getPassword());
        String sql = "insert into Usuario (username, nombre, password, admin, autor) values (:username,:nombre,:password,:admin,:autor)";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("username", user.getUsername().toLowerCase())
                    .addParameter("nombre", user.getNombre())
                    .addParameter("password", pass)
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
        String sql = "update Usuario set username=:username, nombre=:nombre, password=:password , admin=:admin, autor=:autor where id=:id";
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
        String sql = "delete from Usuario where id=:id";
        try (Connection con = db.open()) {
            con.createQuery(sql)
                    .addParameter("id", id);
            return true;
        } catch (Exception ex) {
            System.out.println("Delete Usuario error: " + ex.getMessage());
            return false;
        }
    }

    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /* Retorna un hash MD5 a partir de un texto */
    public static String encryptPassword(String txt) {
        return getHash(txt, "MD5");
    }

}
