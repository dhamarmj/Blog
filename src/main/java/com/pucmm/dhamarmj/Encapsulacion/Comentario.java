package com.pucmm.dhamarmj.Encapsulacion;

public class Comentario {
    private long id;
    private String comentario;
    private Usuario usuarioAutor;
    private Articulo articuloComentario;
    private long autor;
    private long articulo;

    public Comentario(String comentario, Usuario autor, Articulo articulo) {
        this.comentario = comentario;
        this.usuarioAutor = autor;
        this.articuloComentario = articulo;
        this.autor = autor.getId();
        this.articulo = articulo.getId();
    }
    public Comentario(String comentario, int autor, int articulo) {
        this.comentario = comentario;
        this.autor = autor;
        this.articulo = articulo;
//        this.autor = autor.getId();
//        this.articulo = articulo.getId();
    }
    public long getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }

    public long getArticulo() {
        return articulo;
    }

    public void setArticulo(int articulo) {
        this.articulo = articulo;
    }

    public long getId() {
        return id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuarioAutor() {
        return usuarioAutor;
    }

    public void setUsuarioAutor(Usuario usuarioAutor) {
        this.usuarioAutor = usuarioAutor;
    }

    public Articulo getArticuloComentario() {
        return articuloComentario;
    }

    public void setArticuloComentario(Articulo articuloComentario) {
        this.articuloComentario = articuloComentario;
    }
}
