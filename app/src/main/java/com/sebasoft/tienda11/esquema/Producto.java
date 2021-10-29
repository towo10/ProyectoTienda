package com.sebasoft.tienda11.esquema;

public class Producto {
    private String categoria,subcategoria,producto,observacion,unidad_medida,estado,url_imagen,stock;
    private int idtienda,idcategoria,idsubcategoria,id,idUnidadMedida,idusuario,idProducto;

    public Producto(){
        //Contructor vacio
    }

    public Producto(int id,int idtienda,int idcategoria,int idsubcategoria,int idProducto,int idusuario,int idUnidadMedida
            ,String categoria,String subcategoria,String producto,String observacion,String unidad_medida
            ,String estado,String url_imagen,String stock){

        this.idtienda = idtienda;
        this.idcategoria = idcategoria;
        this.idsubcategoria = idsubcategoria;
        this.id = id;
        this.idusuario = idusuario;
        this.idUnidadMedida = idUnidadMedida;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.producto = producto;
        this.observacion = observacion;
        this.unidad_medida = unidad_medida;
        this.estado = estado;
        this.url_imagen = url_imagen;
        this.stock = stock;
        this.idProducto = idProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public int getId() {
        return id;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public int getIdsubcategoria() {
        return idsubcategoria;
    }

    public int getIdtienda() {
        return idtienda;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEstado() {
        return estado;
    }

    public String getProducto() {
        return producto;
    }

    public String getObservacion() {
        return observacion;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public void setIdsubcategoria(int idsubcategoria) {
        this.idsubcategoria = idsubcategoria;
    }

    public void setIdtienda(int idtienda) {
        this.idtienda = idtienda;
    }

    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }
}
