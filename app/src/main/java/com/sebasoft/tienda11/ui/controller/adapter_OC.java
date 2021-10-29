package com.sebasoft.tienda11.ui.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.OrdenCompra;
import java.util.ArrayList;

public class adapter_OC extends BaseAdapter {
    private ArrayList<OrdenCompra> listaOC;
    private Activity activity;
    private TextView tv_titulo,tv_fecha,tv_descripcion,tv_proveedor;
    private OrdenCompra item;

    public adapter_OC(Activity activity,ArrayList<OrdenCompra>listaOC){
        this.activity = activity;
        this.listaOC = listaOC;
    }


    public void setclean(){
        listaOC.clear();
    }

    @Override
    public int getCount() {
        return listaOC.size();
    }

    @Override
    public Object getItem(int i) {
        return listaOC.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaOC.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vroot = view;
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vroot = inf.inflate(R.layout.adapter_ordencompra, null);
        }
        item = listaOC.get(i);

        tv_titulo = (TextView) vroot.findViewById(R.id.tv_oc_codigo);
        tv_fecha = (TextView) vroot.findViewById(R.id.tv_oc_fecha);
        tv_descripcion = (TextView) vroot.findViewById(R.id.tv_oc_descripcion);
        tv_proveedor = (TextView) vroot.findViewById(R.id.tv_oc_proveedor);

        tv_titulo.setText(item.getCodigo());
        tv_fecha.setText(item.getFecha());
        tv_descripcion.setText(item.getDescripcion());
        tv_proveedor.setText(item.getProveedor());

        return vroot;
    }
}
