package com.sebasoft.tienda11.ui.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.Aplicacion;
import java.util.ArrayList;

public class adapter_gvImagen extends BaseAdapter {
    Context context;
    ArrayList<String> listaImagenes;
    LayoutInflater layoutInflater;

    public adapter_gvImagen(Context context, ArrayList<String> listaImagenes){
        this.context = context;
        this.listaImagenes = listaImagenes;
    }
    public void setclean(){
        listaImagenes.clear();
    }

    @Override
    public int getCount() {
        return listaImagenes.size();
    }

    @Override
    public Object getItem(int i) {
        return listaImagenes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_gv_imagenes,null);
        }

        ImageView ivImagen = view.findViewById(R.id.ivImagen);
        ImageButton ibtnEliminar = view.findViewById(R.id.ibtnEliminar);

        Glide.with(view.getContext())
                .load(listaImagenes.get(i))
                .into(ivImagen);

        ibtnEliminar.setVisibility(View.INVISIBLE);
        return view;
    }
}