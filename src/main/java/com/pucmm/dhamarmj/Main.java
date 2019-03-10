package com.pucmm.dhamarmj;

import com.pucmm.dhamarmj.Services.Encryption;
import com.pucmm.dhamarmj.Handlers.MainHandler;
import com.pucmm.dhamarmj.Services.BootStrapServices;

public class Main {

    public static void main(String[] args) throws Exception{

        BootStrapServices.crearTablas();

        // System.out.println(UsuarioServices.encryptPassword("admin"));
        // System.out.println(UsuarioServices.encryptPassword("author"));
        // System.out.println(UsuarioServices.encryptPassword("visitor"));

        new MainHandler().mainHandler();


    }
}
