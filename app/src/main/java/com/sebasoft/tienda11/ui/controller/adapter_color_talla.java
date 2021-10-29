package com.sebasoft.tienda11.ui.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.Color_talla;
import java.util.ArrayList;

public class adapter_color_talla extends BaseAdapter {
    private Activity activity;
    private ArrayList<Color_talla> color_talle;

    public adapter_color_talla(Activity activity, ArrayList<Color_talla> color_talle) {
        this.activity = activity;
        this.color_talle = color_talle;
    }


    @Override
    public int getCount() {
        return color_talle.size();
    }

    @Override
    public Object getItem(int position) {
        return color_talle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return color_talle.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.df_articulo_info_marca_existencia, null);
        }
        final Color_talla item = (Color_talla) getItem(position);
        TextView  tv_titulo = (TextView) v.findViewById(R.id.tv_info_marca_exis_titulo);
        TextView  tv_talle = (TextView) v.findViewById(R.id.tv_info_marca_exis_cuerpo);


        tv_titulo.setText(item.getColor());
        tv_talle.setText(item.getTalle());



        return v;
    }
}
