package com.sebasoft.tienda11.esquema;

public class ordencompra_talles {
    private String talla,cantidad,precio,subtotal;
    private int id;

    public ordencompra_talles(){

    }
    public ordencompra_talles(int id,String talla,String cantidad,String precio,String subtotal){
        this.talla = talla;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
        this.id = id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getTalla() {
        return talla;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
