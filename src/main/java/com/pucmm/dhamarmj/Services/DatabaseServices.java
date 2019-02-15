package com.pucmm.dhamarmj.Services;

import org.sql2o.Sql2o;

public class DatabaseServices {

    private static Sql2o instancia;
    private static String URL = "jdbc:h2:~/blog";

    public static Sql2o getInstancia(){
        if(instancia==null){
            try{
                instancia = new Sql2o(URL, "sa", "");
            }catch(Exception ex){
                System.out.println("Error al crear la conexion a la DB\n" + ex.getMessage());
            }
        }
        return instancia;
    }

}
