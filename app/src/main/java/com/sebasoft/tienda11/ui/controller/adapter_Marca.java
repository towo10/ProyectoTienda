package com.sebasoft.tienda11.ui.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.db.Usuario;
import com.sebasoft.tienda11.db.dbconfiguracion;
import com.sebasoft.tienda11.esquema.Marca;
import com.sebasoft.tienda11.esquema.Producto;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class adapter_Marca extends BaseAdapter {
    private ArrayList<Marca> marca;
    protected Activity activity;
    private String Servidor;
    private String idcategoria,idsubcategoria,idproducto,id_marca;
    private int idtienda,iduser;
    private Producto producto;
    TextView tv_detalle;

    public adapter_Marca(Activity activity, ArrayList<Marca> marca,Producto producto){
        this.activity = activity;
        this.marca = marca;
        this.producto = producto;

    }

    public void setclean(){
        marca.clear();
    }
    @Override
    public int getCount() {
        return marca.size();
    }

    @Override
    public Object getItem(int position) {
        return marca.get(position);
    }

    @Override
    public long getItemId(int position) {
        return marca.get(position).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.df_articulo_info_marca, null);
        }

            dbconfiguracion dbconf = new dbconfiguracion(activity);
            Usuario cuser = new Usuario(activity);
            cuser._iniciarDatosUsuario();
            Servidor = dbconf.getServidor();
            idtienda = cuser.getTienda();
            iduser = cuser.getUsuario();
            idcategoria = Integer.toString(producto.getIdcategoria());
            idsubcategoria = Integer.toString(producto.getIdsubcategoria());
            idproducto = Integer.toString(producto.getIdProducto());

            Marca item = (Marca) getItem(i);
            TextView tv_marca = (TextView) v.findViewById(R.id.tv_marca);
            TextView tv_promedio = (TextView) v.findViewById(R.id.tv_info_compra_prom);
            TextView tv_ult_compra = (TextView) v.findViewById(R.id.tv_info_ult_compra);
            TextView tv_pre_venta = (TextView) v.findViewById(R.id.tv_info_venta);
            TextView tv_stock = (TextView) v.findViewById(R.id.tv_info_stock);
            tv_detalle = (TextView) v.findViewById(R.id.tv_info_stock_detalle);
            id_marca = Integer.toString(item.getIdmarca());
            tv_marca.setText(item.getMarca());
            tv_promedio.setText(item.getPromedio());
            tv_ult_compra.setText(item.getUltimo());
            tv_pre_venta.setText(item.getPrecio());
            tv_stock.setText(item.getStock());

            onCargarLista1(item.getJson_detalle());
        return v;
    }


    public void onCargarLista1(JSONArray jdata){

        String st_texto = "";
        try {
            for(int z=0;z<jdata.length();z++){

                st_texto = st_texto + jdata.getJSONObject(z).getString("stock")+' '+
                        jdata.getJSONObject(z).getString("color")+"\n\t"+
                        jdata.getJSONObject(z).getString("talle")+"\n\n";
            }
            tv_detalle.setText(st_texto);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
