package com.sebasoft.tienda11.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.esquema.Producto;
import com.sebasoft.tienda11.esquema.ProductoNuevo;
import com.sebasoft.tienda11.ui.controller.adapter_Articulo;
import com.sebasoft.tienda11.ui.controller.adapter_ProductoNuevo;
import com.sebasoft.tienda11.ui.controller.df_ProductoNuevo_Imagen;
import com.sebasoft.tienda11.ui.controller.df_ProductoNuevo_modificar;
import com.sebasoft.tienda11.ui.controller.df_articulo_info;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class fragment_ListarArticulo extends Fragment {
    private fragment_CrearArticulo.OnFragmentInteractionListener mListener;
    private ListView lv_producto;
    private Aplicacion app;
    private String Servidor;
    private int idtienda;
    private int iduser;
    private ArrayList<ProductoNuevo> asproductos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listararticulo, container, false);

        app = (Aplicacion) rootView.getContext().getApplicationContext();
        //.getApplicationContext()
        lv_producto = (ListView) rootView.findViewById(R.id.lv_listar_articulo_nuevo);
        Servidor = app.getServidor();
        idtienda = app.getIdtienda();
        iduser = app.getIduser();
        onListarProducto();

        lv_producto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProductoNuevo productoNuevo = new ProductoNuevo();
                productoNuevo = (ProductoNuevo) adapterView.getItemAtPosition(i);

                Fragment fragment2 = new fragment_ProductoNuevo_modificar(productoNuevo);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.app_bar_main, fragment2);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                app.setFragmenadd();

            }
        });

        return rootView;
    }

    private void onListarProducto(){
        String tienda =Integer.toString(idtienda);
        String usuario =Integer.toString(iduser);
        String servicio = Servidor+"/"+"productos?tienda="+tienda+"&usuario="+usuario;

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
        ProductoNuevo producto;

        asproductos = new ArrayList<ProductoNuevo>();
        try {
            JSONArray jsonArray = new JSONArray(response);

            //ProductoNuevo(int id,String categoria,String subcategoria,String producto,String observacion,String modelo,JSONArray jimagenes
            for(int i=0;i<jsonArray.length();i++){
                producto = new ProductoNuevo(
                        i,
                        jsonArray.getJSONObject(i).getString("categoria"),
                        jsonArray.getJSONObject(i).getString("subcategoria"),
                        jsonArray.getJSONObject(i).getString("id"),
                        jsonArray.getJSONObject(i).getString("catid"),
                        jsonArray.getJSONObject(i).getString("subcatid"),
                        jsonArray.getJSONObject(i).getString("dsc_prod"),
                        jsonArray.getJSONObject(i).getString("dsc_obs"),
                        jsonArray.getJSONObject(i).getString("dsc_modelo"),
                        jsonArray.getJSONObject(i).getJSONArray("imagenes")

                );
                asproductos.add(producto);
            }

            //creando el adapter personalizado
            adapter_ProductoNuevo adapter= new adapter_ProductoNuevo(getActivity(),asproductos);
            lv_producto.setAdapter(adapter);

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
