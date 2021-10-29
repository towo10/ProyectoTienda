package com.sebasoft.tienda11.ui.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.db.Usuario;
import com.sebasoft.tienda11.db.dbconfiguracion;
import com.sebasoft.tienda11.esquema.Marca;
import com.sebasoft.tienda11.esquema.Producto;
import com.sebasoft.tienda11.ui.fragment.fragment_CrearArticulo;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class df_articulo_info extends Fragment {
    private fragment_CrearArticulo.OnFragmentInteractionListener mListener;
    private String Servidor;
    private int idtienda,iduser,idcategoria,idsubcategoria,idproducto;
    private ListView lv_info_marca;
    Producto producto;
    TextView tv_info_titulo;
    adapter_Marca adapter_marca;
    public df_articulo_info(Producto producto){
        this.producto = producto;

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.df_articulo_informacion, container, false);

        dbconfiguracion dbconf = new dbconfiguracion(v.getContext());
        Usuario cuser = new Usuario(getContext());
        cuser._iniciarDatosUsuario();
        Servidor = dbconf.getServidor();
        idtienda = cuser.getTienda();
        iduser = cuser.getUsuario();

        tv_info_titulo = (TextView) v.findViewById(R.id.tv_info_titulo);
        lv_info_marca = (ListView) v.findViewById(R.id.lv_info_marca);

        tv_info_titulo.setText(producto.getProducto());
        idcategoria = producto.getIdcategoria();
        idsubcategoria = producto.getIdsubcategoria();
        idproducto = producto.getIdProducto();

        if (adapter_marca != null){
            adapter_marca.setclean();
            lv_info_marca.setAdapter(null);
        }
        onBuscarMarca();
        return v;
    }

    public void onBuscarMarca() {
        String tienda =Integer.toString(idtienda);
        String servicio = Servidor+"/"+"marca?tienda="+tienda+
                "&subcategoria="+Integer.toString(idsubcategoria)+
                "&categoria="+Integer.toString(idcategoria)+
                "&producto="+Integer.toString(idproducto);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest postRequest = new StringRequest( Request.Method.GET, servicio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onCargarLista(new String(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Api sin respuestas",Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(postRequest);
    }

    public void onCargarLista(String response){
        Marca marca;

        ArrayList<Marca> listadomarca = new ArrayList<Marca>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                marca = new Marca(
                        i,
                        Integer.valueOf(jsonArray.getJSONObject(i).getString("marca_codigo")),
                        jsonArray.getJSONObject(i).getString("marca"),
                        jsonArray.getJSONObject(i).getString("stock"),
                        jsonArray.getJSONObject(i).getString("ult_precio"),
                        jsonArray.getJSONObject(i).getString("promedio"),
                        jsonArray.getJSONObject(i).getString("precio_venta"),
                        jsonArray.getJSONObject(i).getJSONArray("Contenido")
                );
                listadomarca.add(marca);
            }

            //creando el adapter personalizado
            if (jsonArray.length()> 0){
                adapter_marca= new adapter_Marca(getActivity(),listadomarca,producto);
                lv_info_marca.setAdapter(adapter_marca);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof fragment_CrearArticulo.OnFragmentInteractionListener) {
            mListener = (fragment_CrearArticulo.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
