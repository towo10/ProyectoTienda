package com.sebasoft.tienda11.ui.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.db.dbconfiguracion;
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.esquema.Producto;
import java.util.ArrayList;

public class adapter_Articulo extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Producto> producto;
    private String Servidor;
    FragmentManager fm_base;
    Aplicacion app;
    df_articulo_info fragment2;

    public adapter_Articulo(Activity activity,ArrayList<Producto> producto,FragmentManager fm_base){
        this.activity = activity;
        this.producto = producto;
        this.fm_base = fm_base;
        app = (Aplicacion) activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return producto.size();
    }

    @Override
    public Object getItem(int position) {
        return producto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return producto.get(position).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.adapter_articulo, null);
        }
        final Producto item = (Producto) getItem(i);
        dbconfiguracion dbconf = new dbconfiguracion(v.getContext());
        Servidor = dbconf.getServidor();
        String ls_imagen_url,ls_anulado;



        ImageView iv_imagen = (ImageView) v.findViewById(R.id.iv_principal_articulo);
        TextView  tv_codigo = (TextView) v.findViewById(R.id.tv_codigo_articulo);
        TextView  tv_anulado = (TextView) v.findViewById(R.id.tv_anulado_articulo);
        TextView  tv_categoria = (TextView) v.findViewById(R.id.tv_categoria_articulo);
        TextView  tv_subcategoria = (TextView) v.findViewById(R.id.tv_subcategoria_articulo);
        TextView  tv_producto = (TextView) v.findViewById(R.id.tv_producto_articulo);
        TextView  tv_adicional = (TextView) v.findViewById(R.id.tv_adicional_articulo);
        LinearLayout ly_info = (LinearLayout) v.findViewById(R.id.ly_info);


        tv_codigo.setText("Código "+Integer.toString(item.getIdcategoria()) +
                                    Integer.toString(item.getIdsubcategoria()) +
                                    Integer.toString(item.getIdProducto()));

        ls_anulado = item.getEstado();
        if(!ls_anulado.equals("Vigente")){
            tv_anulado.setVisibility(View.VISIBLE);
        }

        tv_categoria.setText(item.getCategoria());
        tv_subcategoria.setText(item.getSubcategoria());
        tv_producto.setText(item.getProducto());
        tv_adicional.setText(item.getStock());
        ls_imagen_url = item.getUrl_imagen();
        if(!ls_imagen_url.equals("null")){
            Glide.with(v.getContext())
                    .load(Servidor+ls_imagen_url)
                    .into(iv_imagen);


        }else{
            iv_imagen.setImageResource(R.mipmap.tienda);
        }

        iv_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                df_articulo_imagen articulo_imagen = new df_articulo_imagen(item);
                articulo_imagen.show(fm_base,"Imagenes");
                android.app.Fragment frag = activity.getFragmentManager().findFragmentByTag("Imagenes");
                if (frag!=null){
                    activity.getFragmentManager().beginTransaction().remove(frag).commit();
                }
            }
        });
        ly_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    fragment2 = new df_articulo_info(item);
                    FragmentManager fragmentManager = fm_base;
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.animator.fade_out);
                    fragmentTransaction.replace(R.id.fragment_marca, fragment2);
                    fragmentTransaction.commit();
                    fragmentTransaction.addToBackStack(null);
                    app.setFragmenadd();

            }
        });



        return v;
    }

    private void showToast(String mensaje){
        Toast.makeText(activity,mensaje, Toast.LENGTH_SHORT).show();
    }
}
