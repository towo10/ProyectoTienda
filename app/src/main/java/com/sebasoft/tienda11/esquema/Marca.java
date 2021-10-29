package com.sebasoft.tienda11.esquema;

import org.json.JSONArray;

public class Marca {
    private int id,idmarca;
    private String marca,stock,ultimo,promedio,precio;
    private JSONArray json_detalle;

    public Marca(){

    }
    public  Marca (int id, int idmarca,String marca,String stock,String ultimo,String promedio,String precio,JSONArray json_detalle){
        this.id = id;
        this.marca = marca;
        this.stock = stock;
        this.ultimo = ultimo;
        this.promedio = promedio;
        this.idmarca = idmarca;
        this.precio = precio;
        this.json_detalle = json_detalle;
    }

    public JSONArray getJson_detalle() {
        return json_detalle;
    }

    public void setJson_detalle(JSONArray json_detalle) {
        this.json_detalle = json_detalle;
    }

    public int getIdmarca() {
        return idmarca;
    }
    public  void setIdmarca(int idmarca){
        this.idmarca = idmarca;
    }


    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStock() {
        return stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUltimo() {
        return ultimo;
    }

    public String getMarca() {
        return marca;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setUltimo(String ultimo) {
        this.ultimo = ultimo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
