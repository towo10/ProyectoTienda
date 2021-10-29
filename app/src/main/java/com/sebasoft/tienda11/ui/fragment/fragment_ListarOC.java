package com.sebasoft.tienda11.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.esquema.OrdenCompra;
import com.sebasoft.tienda11.ui.controller.adapter_OC_rv;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class fragment_ListarOC extends Fragment {
    private fragment_CrearArticulo.OnFragmentInteractionListener mListener;
    private ListView lv_oc;
    private RecyclerView rv_listaoc;
    private Aplicacion app;
    private String Servidor;
    private int idtienda,iduser;
    private ArrayList<OrdenCompra> listaOC;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listaroc, container, false);
        //lv_oc = (ListView) rootView.findViewById(R.id.lv_ordencompra);
        rv_listaoc = (RecyclerView) rootView.findViewById(R.id.rv_oc_lista);
        rv_listaoc.setLayoutManager(new LinearLayoutManager(this.getContext()));

        app = (Aplicacion) rootView.getContext().getApplicationContext();

        Servidor = app.getServidor();
        idtienda = app.getIdtienda();
        iduser = app.getIduser();

        onListarOrdenCompra();
        return rootView;
    }

    public void onListarOrdenCompra(){
        String tienda =Integer.toString(idtienda);
        String usuario =Integer.toString(iduser);
        String servicio = Servidor+"/"+"orden_compra?tienda="+tienda+"&usuario="+usuario;

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
        OrdenCompra ordencompra;
        listaOC = new ArrayList<OrdenCompra>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                ordencompra = new OrdenCompra(
                        i,
                        jsonArray.getJSONObject(i).getString("codigo"),
                        jsonArray.getJSONObject(i).getString("proveedor"),
                        jsonArray.getJSONObject(i).getString("fecha"),
                        jsonArray.getJSONObject(i).getString("descripcion"),
                        jsonArray.getJSONObject(i).getJSONArray("marcas")
                );

                listaOC.add(ordencompra);
            }
            rv_listaoc.setAdapter(new adapter_OC_rv(listaOC, new adapter_OC_rv.OnItemClickListener() {
                @Override
                public void onItemClick(OrdenCompra item) {

                    Fragment frag = new fragment_oc_detalle(item);
                    FragmentManager fmanager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fmanager.beginTransaction();
                    fragmentTransaction.replace(R.id.app_bar_main, frag);
                    fragmentTransaction.commit();
                    fragmentTransaction.addToBackStack(null);
                    app.setFragmenadd();


                }
            }));

        }catch (JSONException e){
            e.getMessage();
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
