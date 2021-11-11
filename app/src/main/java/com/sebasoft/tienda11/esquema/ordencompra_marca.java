package com.sebasoft.tienda11.esquema;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ordencompra_marca {
    private int id;
    private String categoria,subcategoria,producto,marca,subtotal;
    private String cat_codigo,subcat_codigo,pro_codigo;
    private String moneda,marca_codigo,moneda_codigo;
    private String ocompra_id;
    private JSONArray jcolor;
    private ArrayList<ordencompra_colores> listacolor;

    public ordencompra_marca(){

    }

    public ordencompra_marca(int id,String categoria,String subcategoria,String producto,
                             String marca,String moneda,String subtotal,String ocompra_id,String cat_codigo,
            String subcat_codigo,String pro_codigo,String marca_codigo,String moneda_codigo,JSONArray jcolor){
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.producto = producto;
        this.marca = marca;
        this.subtotal = subtotal;
        this.moneda = moneda;
        this.jcolor = jcolor;
        this.cat_codigo = cat_codigo;
        this.subcat_codigo = subcat_codigo;
        this.pro_codigo = pro_codigo;
        this.marca_codigo = marca_codigo;
        this.moneda_codigo = moneda_codigo;
        this.ocompra_id = ocompra_id;
        onDetalleOC(jcolor);

    }

    private void onDetalleOC(JSONArray jdata){
        listacolor = new ArrayList<ordencompra_colores>();
        ordencompra_colores detalle;
        try {
            for (int i = 0; i < jdata.length(); i++) {
                detalle = new ordencompra_colores(
                        i,
                        jdata.getJSONObject(i).getString("color_codigo"),
                        jdata.getJSONObject(i).getString("color"),
                        jdata.getJSONObject(i).getString("subtotal"),
                        jdata.getJSONObject(i).getJSONArray("talles"));

                listacolor.add(detalle);
            }
        }catch (JSONException e){
            e.getMessage();
        }
    }

    public String getCat_codigo() {
        return cat_codigo;
    }

    public String getMarca_codigo() {
        return marca_codigo;
    }

    public String getMoneda_codigo() {
        return moneda_codigo;
    }

    public String getPro_codigo() {
        return pro_codigo;
    }

    public String getSubcat_codigo() {
        return subcat_codigo;
    }

    public void setCat_codigo(String cat_codigo) {
        this.cat_codigo = cat_codigo;
    }

    public void setJcolor(JSONArray jcolor) {
        this.jcolor = jcolor;
    }

    public void setListacolor(ArrayList<ordencompra_colores> listacolor) {
        this.listacolor = listacolor;
    }

    public void setMarca_codigo(String marca_codigo) {
        this.marca_codigo = marca_codigo;
    }

    public void setMoneda_codigo(String moneda_codigo) {
        this.moneda_codigo = moneda_codigo;
    }

    public void setPro_codigo(String pro_codigo) {
        this.pro_codigo = pro_codigo;
    }

    public void setSubcat_codigo(String subcat_codigo) {
        this.subcat_codigo = subcat_codigo;
    }

    public String getOcompra_id() {
        return ocompra_id;
    }

    public void setOcompra_id(String ocompra_id) {
        this.ocompra_id = ocompra_id;
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
