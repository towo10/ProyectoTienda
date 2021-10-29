package com.sebasoft.tienda11.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.esquema.Producto;
import com.sebasoft.tienda11.ui.controller.adapter_Articulo;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class fragment_BuscarArticulo extends Fragment implements View.OnClickListener{

    private EditText Codigo;
    private ImageButton scanBtn;
    private ProgressBar pb_buscar;
    private ListView lista;
    private String Servidor;
    private int idtienda,iduser;
    private Usuario cuser;
    FrameLayout fragmentContainer;

    private fragment_CrearArticulo.OnFragmentInteractionListener mListener;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = null;
        Aplicacion app = (Aplicacion) getActivity().getApplicationContext();

        if (app.getArticulo_info() == 1) {
            app.setArticulo_info(0);
        }
            rootView = inflater.inflate(R.layout.fragment_buscararticulo, container, false);
            dbconfiguracion dbconf = new dbconfiguracion(rootView.getContext());

            cuser = new Usuario(getContext());
            cuser._iniciarDatosUsuario();
            Servidor = dbconf.getServidor();
            idtienda = cuser.getTienda();
            iduser = cuser.getUsuario();

            fragmentContainer = (FrameLayout) rootView.findViewById(R.id.fragment_marca);
            scanBtn = (ImageButton) rootView.findViewById(R.id.ibt_scan);
            Codigo = (EditText) rootView.findViewById(R.id.et_coditem);
            pb_buscar = (ProgressBar) rootView.findViewById(R.id.pb_buscar);
            lista = (ListView) rootView.findViewById(R.id.lv_articulos);
            /*Forzamos cuando va el foco en el edittext codigo, se abra el teclado*/
            Codigo.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

            scanBtn.setOnClickListener(this);
            /*BUscaquamos los productos*/
            Codigo.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                    String Item;
                    if (arg1 == KeyEvent.KEYCODE_ENTER) {
                        Item = Codigo.getText().toString();
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(Codigo.getWindowToken(), 0);
                        scanBtn.setClickable(false);
                        pb_buscar.setVisibility(View.VISIBLE);
                        onBuscarProducto(Item);

                        //listado.setVisibility(View.VISIBLE);
                        return true;
                    }
                    return false;
                }
            });

            /*Ingresamos mas a detalle al producto*/
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        /* comentamos esto, para probar otra opcion
                        Producto producto = new Producto();
                        producto = (Producto) parent.getItemAtPosition(position);

                        df_articulo articulo = new df_articulo(producto);
                        articulo.show(getParentFragmentManager(),"articulo");
                        android.app.Fragment frag = getActivity().getFragmentManager().findFragmentByTag("stock");
                        if (frag!=null){
                            getActivity().getFragmentManager().beginTransaction().remove(frag).commit();
                        }*/
                }
            });



        return rootView;
    }



    @Override
    public void onClick(View view) {
        /*Aquí va el código del boton scan*/
        switch (view.getId()){
            case R.id.ibt_scan:
                //agregamos las acciones de presionar el boton de escanear
                break;
        }

    }

    public void onBuscarProducto(String item) {
        String tienda =Integer.toString(idtienda);
        String servicio = Servidor+"/"+"productos?tienda="+tienda+"&buscar="+item;

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
        Producto producto;

        ArrayList<Producto> aproductos = new ArrayList<Producto>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                producto = new Producto(
                        i,
                        Integer.parseInt(jsonArray.getJSONObject(i).getString("tienda_codigo")),
                        Integer.parseInt(jsonArray.getJSONObject(i).getString("categorias_codigo")),
                        Integer.parseInt(jsonArray.getJSONObject(i).getString("subcategoria_codigo")),
                        Integer.parseInt(jsonArray.getJSONObject(i).getString("codigo")),
                        0,
                        0,
                        jsonArray.getJSONObject(i).getString("categoria"),
                        jsonArray.getJSONObject(i).getString("subcategoria"),
                        jsonArray.getJSONObject(i).getString("nombre"),
                        jsonArray.getJSONObject(i).getString("observacion"),
                        jsonArray.getJSONObject(i).getString("unidad_medida"),
                        jsonArray.getJSONObject(i).getString("estado"),
                        jsonArray.getJSONObject(i).getString("img_url"),
                        jsonArray.getJSONObject(i).getString("stock_general")
                        );
                aproductos.add(producto);
            }

            //creando el adapter personalizado
            adapter_Articulo adapter= new adapter_Articulo(getActivity(),aproductos,getParentFragmentManager());
            lista.setAdapter(adapter);
            scanBtn.setClickable(true);
            pb_buscar.setVisibility(View.INVISIBLE);

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
