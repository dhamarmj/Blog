package com.pucmm.dhamarmj.Encapsulacion;

public class Usuario {
    private int id;
    private String username;
    private String nombre;
    private String password;
    private boolean admin;
    private boolean autor;

    public Usuario(String username, String nombre, String password, boolean admin, boolean autor) {
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.admin = admin;
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() { return admin; }

    public void setAdmin(boolean administrator) {
        this.admin = administrator;
    }

    public boolean isAutor() {
        return autor;
    }

    public void setAutor(boolean autor) {
        this.autor = autor;
    }


}
