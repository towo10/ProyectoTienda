package com.sebasoft.tienda11.esquema;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ordencompra_colores {
    private int id;
    private String codigo,color,subtotal;
    private JSONArray jtalles;
    private ArrayList<ordencompra_talles> listatalle;

    public ordencompra_colores(){

    }
    public ordencompra_colores(int id,String codigo,String color,String subtotal,JSONArray jtalles){
        this.codigo = codigo;
        this.color = color;
        this.subtotal = subtotal;
        this.jtalles = jtalles;
        this.id = id;
        onDetalleOC(jtalles);
    }

    private void onDetalleOC(JSONArray jdata){
        listatalle = new ArrayList<>();
        ordencompra_talles detalle;
        try {
            for (int i = 0; i < jdata.length(); i++) {
                detalle = new ordencompra_talles(
                        i,
                        jdata.getJSONObject(i).getString("codigo_detalle"),
                        jdata.getJSONObject(i).getString("talla"),
                        jdata.getJSONObject(i).getString("cantidad"),
                        jdata.getJSONObject(i).getString("precio"),
                        jdata.getJSONObject(i).getString("subtotal"));
                listatalle.add(detalle);
            }
        }catch (JSONException e){
            e.getMessage();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getColor() {
        return color;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<ordencompra_talles> getListatalle() {
        return listatalle;
    }

    public JSONArray getJtalles() {
        return jtalles;
    }

    public void setJtalles(JSONArray jtalles) {
        this.jtalles = jtalles;
    }

    public void setListatalle(ArrayList<ordencompra_talles> listatalle) {
        this.listatalle = listatalle;
    }
}
