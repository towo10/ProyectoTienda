package com.sebasoft.tienda11.esquema;

public class DetalleOrdenCompra {
    private int id;
    private String iddetalle,idcategoria,idsubcategoria,idproducto;
    private String idcolor,idtalla,idmarca,idmoneda,idum;
    private String categoria,subcategoria,producto,color,talla,marca;
    private String cantidad,precio,moneda,um,flg_temp,pro_temp;
    public DetalleOrdenCompra(){

    }

    public DetalleOrdenCompra(int id,String iddetalle,String idcategoria,String idsubcategoria,String idproducto,
                              String idcolor,String idtalla,String idmarca,String idmoneda,String idum,
                              String categoria,String subcategoria,String producto,String color,String talla,String marca,
                              String cantidad,String precio,String moneda,String um,String flg_temp,String pro_temp){
        this.id = id;
        this.iddetalle = iddetalle;
        this.idcategoria = idcategoria;
        this.idsubcategoria = idsubcategoria;
        this.idproducto = idproducto;
        this.idcolor = idcolor;
        this.idtalla = idtalla;
        this.idmarca = idmarca;
        this.idmoneda = idmoneda;
        this.idum = idum;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.producto = producto;
        this.color = color;
        this.talla = talla;
        this.marca = marca;
        this.cantidad = cantidad;
        this.precio = precio;
        this.moneda = moneda;
        this.um = um;
        this.flg_temp = flg_temp;
        this.pro_temp = pro_temp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public int getId() {
        return id;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setIdsubcategoria(String idsubcategoria) {
        this.idsubcategoria = idsubcategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setIdcategoria(String idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getIdcategoria() {
        return idcategoria;
    }

    public String getIdcolor() {
        return idcolor;
    }

    public String getIddetalle() {
        return iddetalle;
    }

    public String getIdmarca() {
        return idmarca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getIdmoneda() {
        return idmoneda;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public String getIdsubcategoria() {
        return idsubcategoria;
    }

    public String getIdtalla() {
        return idtalla;
    }

    public String getIdum() {
        return idum;
    }

    public void setIdcolor(String idcolor) {
        this.idcolor = idcolor;
    }

    public void setIddetalle(String iddetalle) {
        this.iddetalle = iddetalle;
    }

    public void setIdmarca(String idmarca) {
        this.idmarca = idmarca;
    }

    public void setIdmoneda(String idmoneda) {
        this.idmoneda = idmoneda;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public void setIdtalla(String idtalla) {
        this.idtalla = idtalla;
    }

    public void setIdum(String idum) {
        this.idum = idum;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getFlg_temp() {
        return flg_temp;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getPro_temp() {
        return pro_temp;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getUm() {
        return um;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setFlg_temp(String flg_temp) {
        this.flg_temp = flg_temp;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public void setPro_temp(String pro_temp) {
        this.pro_temp = pro_temp;
    }

    public void setUm(String um) {
        this.um = um;
    }

}
