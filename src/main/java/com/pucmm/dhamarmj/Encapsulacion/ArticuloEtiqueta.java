package com.pucmm.dhamarmj.Encapsulacion;

public class ArticuloEtiqueta {
    private long id;
    private Articulo articulo;
    private Etiqueta etiqueta;
    private long idarticulo;
    private long idetiqueta;

    public ArticuloEtiqueta(Articulo articulo, Etiqueta etiqueta) {
        this.articulo = articulo;
        this.etiqueta = etiqueta;
        this.idarticulo = articulo.getId();
        this.idetiqueta = etiqueta.getId();
    }

    public long getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(long idarticulo) {
        this.idarticulo = idarticulo;
    }

    public long getIdetiqueta() {
        return idetiqueta;
    }

    public void setIdetiqueta(long idetiqueta) {
        this.idetiqueta = idetiqueta;
    }


    public long getId() {
        return id;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Etiqueta getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(Etiqueta etiqueta) {
        this.etiqueta = etiqueta;
    }


}
