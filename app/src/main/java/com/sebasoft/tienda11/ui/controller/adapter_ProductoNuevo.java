package com.sebasoft.tienda11.ui.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.esquema.Producto;
import com.sebasoft.tienda11.esquema.ProductoNuevo;

import org.json.JSONException;

import java.util.ArrayList;


public class adapter_ProductoNuevo extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<ProductoNuevo> lista_producto;
    Aplicacion app;
    String Servidor;
    Boolean abo_contiene_imagen = false;

    public adapter_ProductoNuevo(Activity activity,ArrayList<ProductoNuevo> lista_producto){
        this.activity = activity;
        this.lista_producto = lista_producto;

    }
    @Override
    public int getCount() {
        return lista_producto.size();
    }

    @Override
    public Object getItem(int i) {
        return lista_producto.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lista_producto.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.adapter_producto_nuevo, null);
        }
        ProductoNuevo pnuevo = (ProductoNuevo) getItem(i);
        app = (Aplicacion) v.getContext().getApplicationContext();

        Servidor = app.getServidor();
        String url_image = "";
        TextView tv_titulo = (TextView) v.findViewById(R.id.tv_producto_nuevo_titulo);
        TextView tv_codigo = (TextView) v.findViewById(R.id.tv_producto_nuevo_codigo);
        TextView tv_categoria = (TextView) v.findViewById(R.id.tv_producto_nuevo_categoria);
        TextView tv_subcategoria = (TextView) v.findViewById(R.id.tv_producto_nuevo_subcategoria);
        TextView tv_modelo = (TextView) v.findViewById(R.id.tv_producto_nuevo_modelo);
        TextView tv_observacion = (TextView) v.findViewById(R.id.tv_producto_nuevo_observacion);
        ImageView iv_imagen = (ImageView) v.findViewById(R.id.iv_producto_nuevo_imagen);

        tv_titulo.setText(pnuevo.getProducto());
        tv_codigo.setText(pnuevo.getProductoid());
        tv_categoria.setText(pnuevo.getCategoria());
        tv_subcategoria.setText(pnuevo.getSubcategoria());
        tv_modelo.setText(pnuevo.getModelo());
        tv_observacion.setText(pnuevo.getObservacion());

        if(pnuevo.getJimagenes().length() > 0){
            try {
                url_image = pnuevo.getJimagenes().getJSONObject(0).getString("dsc_dir");
                abo_contiene_imagen = true;
            } catch (JSONException e) {
                e.printStackTrace();
                abo_contiene_imagen = false;
                Toast.makeText(v.getContext(),"(-1) No se pudo cargar la Imagen.",Toast.LENGTH_SHORT).show();
            }
        }else{
            abo_contiene_imagen = false;
        }
        if (abo_contiene_imagen){
            Glide.with(v.getContext())
                    .load(Servidor + url_image)
                    .into(iv_imagen);
        }else{
            iv_imagen.setImageResource(R.mipmap.tienda);
        }

        return v;
    }
}
