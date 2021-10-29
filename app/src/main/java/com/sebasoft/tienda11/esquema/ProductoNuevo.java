package com.sebasoft.tienda11.esquema;

import org.json.JSONArray;

public class ProductoNuevo {
    private String categoria,subcategoria,producto,observacion,modelo,productoid;
    private String categoriaid,subcategoriaid;
    private JSONArray jimagenes;
    private int id;

    public ProductoNuevo(){

    }
    public ProductoNuevo(int id,String categoria,String subcategoria,String productoid,
                         String categoriaid,String subcategoriaid,
                         String producto,String observacion,String modelo,JSONArray jimagenes){
        this.id = id;
        this.categoria =categoria;
        this.subcategoria = subcategoria;
        this.producto = producto;
        this.observacion = observacion;
        this.modelo = modelo;
        this.jimagenes = jimagenes;
        this.productoid = productoid;
        this.categoriaid = categoriaid;
        this.subcategoriaid = subcategoriaid;
    }

    public String getCategoriaid() {
        return categoriaid;
    }

    public String getSubcategoriaid() {
        return subcategoriaid;
    }

    public void setCategoriaid(String categoriaid) {
        this.categoriaid = categoriaid;
    }

    public void setSubcategoriaid(String subcategoriaid) {
        this.subcategoriaid = subcategoriaid;
    }

    public void setProductoid(String productoid) {
        this.productoid = productoid;
    }

    public String getProductoid() {
        return productoid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public String getProducto() {
        return producto;
    }

    public String getObservacion() {
        return observacion;
    }

    public JSONArray getJimagenes() {
        return jimagenes;
    }

    public String getModelo() {
        return modelo;
    }

    public void setJimagenes(JSONArray jimagenes) {
        this.jimagenes = jimagenes;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
