package com.pucmm.dhamarmj;

import com.pucmm.dhamarmj.Encapsulacion.Articulo;
import com.pucmm.dhamarmj.Encapsulacion.ArticuloEtiqueta;
import com.pucmm.dhamarmj.Encapsulacion.Etiqueta;
import com.pucmm.dhamarmj.Services.*;
import com.pucmm.dhamarmj.Handlers.MainHandler;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception{

        BootStrapServices.crearTablas();

////         System.out.println(UsuarioServices.encryptPassword("admin"));
//         System.out.println(UsuarioServices.encryptPassword("author"));
//         System.out.println(UsuarioServices.encryptPassword("visitor"));

        new MainHandler().mainHandler();


    }
}
