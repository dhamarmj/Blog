package com.pucmm.dhamarmj.Services;

import com.pucmm.dhamarmj.Encapsulacion.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class UsuarioServices {

    private Sql2o db;

    public UsuarioServices() {
        db = DatabaseServices.getInstancia();
    }

    public List<Usuario> getUsuario() {
        String sql="select * from Usuario";
        try(Connection con = db.open()){
            return con.createQuery(sql).executeAndFetch(Usuario.class);
        }
    }

//    public Usuario crearUsuario() {
//        String sql="insert into Usuario (username, nombre, password) values(:matricula,:nombre)";
//        try(Connection con = db.open()){
//            return con.createQuery(sql).executeAndFetch(Usuario.class);
//        }
//    }





}
