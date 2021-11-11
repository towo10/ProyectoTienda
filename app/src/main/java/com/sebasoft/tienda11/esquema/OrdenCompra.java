package com.sebasoft.tienda11.esquema;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class OrdenCompra {
    private int id;
    private String codigo,proveedor,fecha,descripcion;
    private JSONArray jdetalle;
    private ArrayList<ordencompra_marca> listamarca;
    public OrdenCompra(){

    }

    public OrdenCompra(int id,String codigo,String proveedor,String fecha,String descripcion,
                       JSONArray jdetalle){
        this.id = id;
        this.codigo = codigo;
        this.proveedor = proveedor;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.jdetalle = jdetalle;

        onDetalleOC(jdetalle);
    }

    private void onDetalleOC(JSONArray jdata){

        listamarca = new ArrayList<ordencompra_marca>();
        ordencompra_marca detalle;
        try {
            for (int i = 0; i < jdata.length(); i++) {
                detalle = new ordencompra_marca(
                        i,
                        jdata.getJSONObject(i).getString("categoria"),
                        jdata.getJSONObject(i).getString("subcategoria"),
                        jdata.getJSONObject(i).getString("producto"),
                        jdata.getJSONObject(i).getString("marca"),
                        jdata.getJSONObject(i).getString("moneda"),
                        jdata.getJSONObject(i).getString("subtotal"),
                        codigo,
                        jdata.getJSONObject(i).getString("categorias_codigo"),
                        jdata.getJSONObject(i).getString("subcategoria_codigo"),
                        jdata.getJSONObject(i).getString("productos_codigo"),
                        jdata.getJSONObject(i).getString("marca_codigo"),
                        jdata.getJSONObject(i).getString("moneda_codigo"),
                        jdata.getJSONObject(i).getJSONArray("colores"));
                listamarca.add(detalle);
            }
        }catch (JSONException e){
            e.getMessage();
        }


    }

    public ArrayList<ordencompra_marca> getlistamarca() {
        return listamarca;
    }

    public void setListaoc(ArrayList<ordencompra_marca> listamarca) {
        this.listamarca = listamarca;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ordencompra_marca> getListamarca() {
        return listamarca;
    }

    public void setListamarca(ArrayList<ordencompra_marca> listamarca) {
        this.listamarca = listamarca;
    }


    public JSONArray getJdetalle() {
        return jdetalle;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setJdetalle(JSONArray jdetalle) {
        this.jdetalle = jdetalle;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

}
