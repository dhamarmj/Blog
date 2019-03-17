package com.pucmm.dhamarmj.Encapsulacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Articulo {

    private long id;
    private String titulo;
    private String texto;
    private int autor;
    private Date fecha;
    private List<Comentario> listaComentarios;
    private List<Etiqueta> listaEtiquetas;
    private Usuario usuarioautor;
    private String teaser;
    private String stringEtiqueta = "";

    public Articulo(String titulo, String cuerpo, Date fecha, List<Comentario> listaComentarios, List<Etiqueta> listaEtiquetas, Usuario user) {
        this.titulo = titulo;
        this.texto = cuerpo;
        this.fecha = fecha;
        this.listaComentarios = listaComentarios;
        this.listaEtiquetas = listaEtiquetas;
        this.usuarioautor = user;
        this.autor = user.getId();
        startTeaser();
        this.stringEtiqueta = startEtiquetasString();
    }

    public String startEtiquetasString() {
        String value = "";
        if (listaEtiquetas == null || listaEtiquetas.size() == 0)
            return value;

        for (Etiqueta etiqueta :
                listaEtiquetas) {
            value += etiqueta.getEtiqueta() + ", ";
        }
        return value.substring(0, value.length() - 2);
    }

    public void startTeaser() {
        if (texto.length() > 160)
            this.teaser = this.texto.substring(0, 160);
        else
            this.teaser = this.texto;
    }

    public Articulo(String titulo, String cuerpo, Date fecha, Usuario user) {
        this.titulo = titulo;
        this.texto = cuerpo;
        this.autor = autor;
        this.fecha = fecha;
        this.listaComentarios = new ArrayList<>();
        this.listaEtiquetas = new ArrayList<>();
        this.usuarioautor = user;
        this.autor = usuarioautor.getId();
        startTeaser();
        this.stringEtiqueta = startEtiquetasString();
    }

    public String getStringEtiqueta() {
        return stringEtiqueta;
    }

    public void setStringEtiqueta(String stringEtiqueta) {
        this.stringEtiqueta = stringEtiqueta;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public void setListaComentarios(List<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public void setListaEtiquetas(List<Etiqueta> listaEtiquetas) {
        this.listaEtiquetas = listaEtiquetas;
        this.stringEtiqueta = startEtiquetasString();
    }

    public Usuario getUsuarioautor() {
        return usuarioautor;
    }

    public void setUsuarioautor(Usuario usuarioautor) {
        this.usuarioautor = usuarioautor;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setCuerpo(String cuerpo) {
        this.texto = cuerpo;
    }

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(ArrayList<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }
    public List<Etiqueta> getListaEtiquetas() {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(ArrayList<Etiqueta> listaEtiquetas) {
        this.listaEtiquetas = listaEtiquetas;
        this.stringEtiqueta = startEtiquetasString();
    }
}
