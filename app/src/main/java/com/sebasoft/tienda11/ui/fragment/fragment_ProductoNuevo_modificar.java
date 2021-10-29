package com.sebasoft.tienda11.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.api.apiConexion;
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.esquema.ProductoNuevo;
import com.sebasoft.tienda11.ui.controller.adapter_gvImagen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_ProductoNuevo_modificar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_ProductoNuevo_modificar extends Fragment {

    private String Servidor,userid,tiendaid,token;
    private Aplicacion app;
    private JSONArray jimagen;
    private TextView tv_cat,tv_subcat;
    private EditText et_producto,et_modelo,et_observacion;
    private ProgressBar pb_guardar;
    private GridView gvImagenes;
    private ProductoNuevo productoNuevo;
    Fragment fragment;

    public fragment_ProductoNuevo_modificar(ProductoNuevo productoNuevo){
        this.productoNuevo = productoNuevo;
    }

    public static fragment_ProductoNuevo_modificar newInstance(ProductoNuevo productoNuevo) {
        fragment_ProductoNuevo_modificar fragment = new fragment_ProductoNuevo_modificar(productoNuevo);
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__producto_nuevo_modificar, container, false);
        String url_imagen,title_imagen;
        fragment = this;

        app = (Aplicacion) view.getContext().getApplicationContext();
        Servidor = app.getServidor();
        userid = Integer.toString(app.getIduser());
        tiendaid = Integer.toString(app.getIdtienda());
        token = app.getToken();
        jimagen = productoNuevo.getJimagenes();

        TabHost tabs = (TabHost) view.findViewById(R.id.tabhostnuevo_fragment);
        tabs.setup();
        setHasOptionsMenu(true);
        TabHost.TabSpec spec = tabs.newTabSpec("tab17");
        spec.setContent(R.id.tab1);
        spec.setIndicator("", getResources().getDrawable(android.R.drawable.ic_input_get));
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tab27");
        spec.setContent(R.id.tab2);
        spec.setIndicator("", getResources().getDrawable(android.R.drawable.ic_menu_gallery));
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tab37");
        spec.setContent(R.id.tab3);
        spec.setIndicator("", getResources().getDrawable(android.R.drawable.ic_menu_manage));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);

        tv_cat = (TextView) view.findViewById(R.id.tv_cat2);
        tv_subcat = (TextView) view.findViewById(R.id.tv_subcat2);
        et_producto = (EditText) view.findViewById(R.id.et_producto2);
        et_modelo = (EditText) view.findViewById(R.id.et_modelo2);
        et_observacion = (EditText) view.findViewById(R.id.et_observacion2);
        pb_guardar = (ProgressBar) view.findViewById(R.id.pb_guardar2);
        gvImagenes = (GridView) view.findViewById(R.id.gvImagenes2);

        title_imagen = productoNuevo.getProducto();
        et_producto.setText(title_imagen);
        et_modelo.setText(productoNuevo.getModelo());
        et_observacion.setText(productoNuevo.getObservacion());
        tv_cat.setText(productoNuevo.getCategoria());
        tv_subcat.setText(productoNuevo.getSubcategoria());

        ArrayList<String> listaImagen = new ArrayList<String>();
        for (int i = 0; i < jimagen.length(); i++) {
            try {

                url_imagen = Servidor+jimagen.getJSONObject(i).getString("dsc_dir");
                listaImagen.add(url_imagen);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter_gvImagen aimagen = new adapter_gvImagen(getContext(),listaImagen);
        gvImagenes.setAdapter(aimagen);



        /*Eventos Click*/
        FloatingActionButton fab_guardar = (FloatingActionButton) view.findViewById(R.id.fab_prod_guardar2);
        fab_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Modificar
                AlertDialog.Builder dialogo = new AlertDialog.Builder(view.getContext());
                dialogo.setTitle("Modificar Producto Nuevo");
                dialogo.setMessage("¿Desea Modificar "+productoNuevo.getProducto()+"?");
                dialogo.setCancelable(false);

                dialogo.setPositiveButton("Guardar",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogo, int id){
                        pb_guardar.setVisibility(getView().VISIBLE);
                        try {
                            onActualizarProducto();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pb_guardar.setVisibility(getView().INVISIBLE);
                            Toast.makeText(getContext(),"No se pudo Actualizar el producto",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                dialogo.setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogo, int id){
                        // TODO por ahora no hacemos nada...
                    }

                });
                dialogo.show();
            }
        });
        FloatingActionButton fab_eliminar = (FloatingActionButton) view.findViewById(R.id.fab_prod_eliminar2);
        fab_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Eliminar
                AlertDialog.Builder dialogo = new AlertDialog.Builder(view.getContext());
                dialogo.setTitle("Eliminar Producto Nuevo");
                dialogo.setMessage("¿Desea Eliminar "+productoNuevo.getProducto()+"?");
                dialogo.setCancelable(false);

                dialogo.setPositiveButton("Eliminar",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogo, int id){
                        pb_guardar.setVisibility(getView().VISIBLE);
                        onEliminarProducto();

                    }

                });
                dialogo.setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogo, int id){
                        // TODO por ahora no hacemos nada...
                    }

                });
                dialogo.show();
            }
        });

        return view;
    }
    private void onActualizarProducto() throws JSONException {
        /*Armamos el Json*/
        Map<String, String> data = new HashMap<>();

        data.put("idtienda", tiendaid);
        data.put("idusuario", userid);
        data.put("idproducto", productoNuevo.getProductoid());
        data.put("token", token);

        data.put("idcategoria", productoNuevo.getCategoriaid());
        data.put("idsubcategoria", productoNuevo.getSubcategoriaid());

        data.put("producto", et_producto.getText().toString());
        data.put("modelo", et_modelo.getText().toString());
        data.put("observacion", et_observacion.getText().toString());

        JSONObject jdata2 = new JSONObject(data);
        //TODO este pesado de codigo nos sirve para armar json anidado
        /*
        JSONObject jexample = new JSONObject(data);
        jexample.put("imagenes",productoNuevo.getJimagenes());
        System.out.println(jexample);
        */

        String servicio = Servidor+"/productos";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.PUT, servicio,
                jdata2,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject Obj,Result;
                        Obj = response;
                        String  status,mensaje;
                        try {
                            status = Obj.getString("status");
                            Result = Obj.getJSONObject("result");
                            if (status.equals("ok")){
                                Toast.makeText(getContext(),"Producto Modificado",Toast.LENGTH_LONG).show();
                                pb_guardar.setVisibility(getView().INVISIBLE);
                                getActivity().onBackPressed();

                            }else{
                                //vibrator.vibrate(400);
                                mensaje = Result.getString("error_message");
                                Toast.makeText(getContext(),mensaje,Toast.LENGTH_LONG).show();
                                //getFragmentManager().beginTransaction().remove(fragment).commit();
                            }
                        } catch (JSONException e) {
                            //El servio no trajo nada
                            e.printStackTrace();
                            Toast.makeText(getContext(),"El servicio retorno un error inesperado",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"El servicio no está disponible",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });
        queue.add(postRequest);
    }

    private void onEliminarProducto(){
        /*Armamos el Json*/
        Map<String, String> data = new HashMap<>();

        data.put("idtienda", tiendaid);
        data.put("idusuario", userid);
        data.put("idproducto", productoNuevo.getProductoid());
        data.put("idcategoria", productoNuevo.getCategoriaid());
        data.put("idsubcategoria", productoNuevo.getSubcategoriaid());
        data.put("token", token);



        JSONObject jdata2 = new JSONObject(data);
        String servicio = Servidor+"/producto_eliminar";

        //System.out.println(jdata2);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, servicio,
                jdata2,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject Obj,Result;
                        Obj = response;
                        String  status,mensaje;
                        try {
                            status = Obj.getString("status");
                            Result = Obj.getJSONObject("result");
                            if (status.equals("ok")){
                                Toast.makeText(getContext(),"Producto Eliminado.",Toast.LENGTH_LONG).show();
                                pb_guardar.setVisibility(getView().INVISIBLE);
                                getActivity().onBackPressed();

                            }else{
                                //vibrator.vibrate(400);
                                mensaje = Result.getString("error_message");
                                Toast.makeText(getContext(),mensaje,Toast.LENGTH_LONG).show();
                                //getFragmentManager().beginTransaction().remove(fragment).commit();
                            }
                        } catch (JSONException e) {
                            //El servio no trajo nada
                            e.printStackTrace();
                            Toast.makeText(getContext(),"El servicio retorno un error inesperado",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"El servicio no está disponible",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });
        queue.add(postRequest);
    }
}