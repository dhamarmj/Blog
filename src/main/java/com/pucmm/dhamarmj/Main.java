package com.pucmm.dhamarmj;

import com.pucmm.dhamarmj.Encapsulacion.Usuario;
import com.pucmm.dhamarmj.Handlers.MainHandler;
import com.pucmm.dhamarmj.Services.BootStrapServices;
import com.pucmm.dhamarmj.Services.UsuarioServices;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        BootStrapServices.crearTablas();

         new MainHandler().mainHandler();

//        UsuarioServices user = new UsuarioServices();
//        user.startUsuarios();
//        System.out.println(user.getUsuario().size());
    }
}
