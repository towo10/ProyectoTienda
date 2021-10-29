package com.sebasoft.tienda11.esquema;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ordencompra_marca {
    private int id;
    private String categoria,subcategoria,producto,marca,subtotal;
    private String moneda;
    private JSONArray jcolor;
    private ArrayList<ordencompra_colores> listacolor;

    public ordencompra_marca(){

    }

    public ordencompra_marca(int id,String categoria,String subcategoria,String producto,
                             String marca,String subtotal,String moneda,JSONArray jcolor){
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.producto = producto;
        this.marca = marca;
        this.subtotal = subtotal;
        this.moneda = moneda;
        this.jcolor = jcolor;
        onDetalleOC(jcolor);
    }

    private void onDetalleOC(JSONArray jdata){
        listacolor = new ArrayList<>();
        ordencompra_colores detalle;
        try {
            for (int i = 0; i < jdata.length(); i++) {
                detalle = new ordencompra_colores(
                        i,
                        jdata.getJSONObject(i).getString("codigo"),
                        jdata.getJSONObject(i).getString("color"),
                        jdata.getJSONObject(i).getString("subtotal"),
                        jdata.getJSONObject(i).getJSONArray("talles"));
                listacolor.add(detalle);
            }
        }catch (JSONException e){
            e.getMessage();
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<ordencompra_colores> getListacolor() {
        return listacolor;
    }

    public JSONArray getJcolor() {
        return jcolor;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public String getProducto() {
        return producto;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
