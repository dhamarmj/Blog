package com.pucmm.dhamarmj.Services;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class BootStrapServices {

    public static void crearTablas(){
        Sql2o bd = DatabaseServices.getInstancia();
        String sql1="CREATE TABLE IF NOT EXISTS Usuario(id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255) not null," +
                "nombre VARCHAR(255)," +
                "password VARCHAR(255) not null," +
                "admin bit not null, autor bit not null);";
        String sql2="CREATE TABLE IF NOT EXISTS Etiqueta(id INT PRIMARY KEY AUTO_INCREMENT, etiqueta VARCHAR(255) NOT NULL);";
        String sql3="CREATE TABLE IF NOT EXISTS Articulo(id INT PRIMARY KEY AUTO_INCREMENT, titulo VARCHAR(255) ," +
                "texto TEXT, autor INT, " +
                "fecha date);";
        String sql4="CREATE TABLE IF NOT EXISTS Comentario(id INT PRIMARY KEY AUTO_INCREMENT, comentario VARCHAR(255) not null," +
                "autor INT, articulo INT," +
                "FOREIGN KEY(autor) REFERENCES Usuario(id)," +
                "FOREIGN KEY(articulo) REFERENCES Articulo(id));";
        String sql5="CREATE TABLE IF NOT EXISTS ArticuloEtiqueta(id INT PRIMARY KEY AUTO_INCREMENT, idetiqueta INT, idarticulo INT, " +
                "FOREIGN KEY(idarticulo) REFERENCES Articulo(id)," +
                "FOREIGN KEY(idetiqueta) REFERENCES Etiqueta(id));";
        String sql6="CREATE TABLE IF NOT EXISTS ArticuloComentario(id INT PRIMARY KEY AUTO_INCREMENT, idcomentario INT, idarticulo INT, " +
                "FOREIGN KEY(idarticulo) REFERENCES Articulo(id)," +
                "FOREIGN KEY(idcomentario) REFERENCES Comentario(id));";

        ExecuteQuery(bd, sql1, "Usuario");
        ExecuteQuery(bd, sql2, "Etiqueta");
        ExecuteQuery(bd, sql3, "Articulo");
        ExecuteQuery(bd, sql4,"Comentario");
        ExecuteQuery(bd, sql5, "ArticuloEtiqueta");
        ExecuteQuery(bd, sql5, "ArticuloComentario");
    }

    private static void ExecuteQuery(Sql2o bd, String sql1, String TableName) {
        try(Connection con = bd.open()){
            con.createQuery(sql1).executeUpdate();
            System.out.println("All Good Table: " + TableName );
        } catch (Exception ex){
            System.out.println("Not Good \n " + ex.getMessage());
        }
    }

}
