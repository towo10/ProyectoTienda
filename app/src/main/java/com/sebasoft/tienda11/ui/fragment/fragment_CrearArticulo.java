package com.sebasoft.tienda11.ui.fragment;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Base64;
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
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.api.apiConexion;
import com.sebasoft.tienda11.ui.controller.GridViewAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fragment_CrearArticulo extends Fragment implements  AdapterView.OnItemSelectedListener{
    private OnFragmentInteractionListener mListener;
    Spinner sp_categoria,sp_subcategoria;
    EditText et_producto,et_modelo,et_observacion;
    ProgressBar pb_guardar;
    apiConexion apiconexion;
    String  ls_categoria,ls_subcategoria;
    private Uri imagenUri = null;
    private Button btnGaleria;
    private GridView gvImagenes;
    List<Uri> listaImagenes = new ArrayList<>();
    private String Servidor;
    GridViewAdapter baseAdapter;
    private boolean lbo_enviando = true;
    List<String> listaBase64Imagenes = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_creararticulo, container, false);
        TabHost tabs = (TabHost) rootView.findViewById(R.id.tabhost);
        tabs.setup();
        setHasOptionsMenu(true);
        TabHost.TabSpec spec = tabs.newTabSpec("tab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("", getResources().getDrawable(android.R.drawable.ic_input_get));
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("", getResources().getDrawable(android.R.drawable.ic_menu_gallery));
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("", getResources().getDrawable(android.R.drawable.ic_menu_manage));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);

        sp_categoria = (Spinner) rootView.findViewById(R.id.sp_categoria);
        sp_subcategoria = (Spinner) rootView.findViewById(R.id.sp_subcategoria);
        et_producto = (EditText) rootView.findViewById(R.id.et_producto);
        et_modelo = (EditText) rootView.findViewById(R.id.et_modelo);
        et_observacion = (EditText) rootView.findViewById(R.id.et_observacion);
        pb_guardar = (ProgressBar) rootView.findViewById(R.id.pb_guardar);
        btnGaleria = (Button) rootView.findViewById(R.id.btnGaleria);
        gvImagenes = (GridView) rootView.findViewById(R.id.gvImagenes);
        sp_categoria.setOnItemSelectedListener(this);
        sp_subcategoria.setOnItemSelectedListener(this);

        Vibrator vibrator = (Vibrator) getActivity().getSystemService(getContext().VIBRATOR_SERVICE);

        /*LLENAMOS LOS DATOS DEL SPINER CON LA CLASE DE APIPRODUCTOS*/
        apiconexion = new apiConexion(getContext());
        apiconexion.setSpinners("categoria",sp_categoria,"");
        Servidor = apiconexion.getServidor();

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_producto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String servicio;
                Map<String, String> data = new HashMap<>();

                if (et_producto.getText().toString().length() == 0) {
                    vibrator.vibrate(400);
                    Toast.makeText(getContext(),"Ingrese una descripción general del producto",Toast.LENGTH_SHORT).show();
                } else {

                    data.put("idtienda", Integer.toString(apiconexion.getIdtienda()));
                    data.put("categoria", ls_categoria);
                    data.put("subcategoria", ls_subcategoria);
                    data.put("producto",et_producto.getText().toString());
                    data.put("observacion", et_observacion.getText().toString());
                    data.put("modelo", et_modelo.getText().toString());
                    data.put("idusuario", Integer.toString(apiconexion.getIduser()));
                    data.put("token", apiconexion.getToken());
                    JSONObject jsonData = new JSONObject(data);
                    //result = apiconexion.onEnviarPOST(jsonData, "productos");

                    servicio = Servidor+"/productos";

                    /*Insertamos datos del producto*/
                    habilitaProducto(false);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, servicio,
                            jsonData,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    JSONObject Obj,Result;
                                    Obj = response;
                                    String  status,mensaje,sidcat,sidsubcat,sidpro;
                                    try {
                                        status = Obj.getString("status");
                                        Result = Obj.getJSONObject("result");
                                        if (status.equals("ok")){
                                            //=======================================OK==================================

                                            sidcat = Result.getString("idcat");
                                            sidsubcat = Result.getString("idsubcat");
                                            sidpro = Result.getString("idpro");
                                            /*Enviamos las Imágenes*/
                                            if (baseAdapter != null){
                                                subirImagenes(sidcat,sidsubcat,sidpro);
                                                baseAdapter.setclean();
                                                gvImagenes.setAdapter(null);
                                            }

                                            et_producto.setText("");
                                            et_modelo.setText("");
                                            et_observacion.setText("");
                                            habilitaProducto(true);
                                            rootView.findViewById(R.id.et_producto).requestFocus();
                                            tabs.setCurrentTab(0);

                                            Toast.makeText(getContext(),"Datos Guardados correctamente",Toast.LENGTH_LONG).show();
                                        }else{
                                            vibrator.vibrate(400);
                                            habilitaProducto(true);
                                            mensaje = Result.getString("error_message");
                                            Toast.makeText(getContext(),mensaje,Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        //El servio no trajo nada
                                        vibrator.vibrate(400);
                                        habilitaProducto(true);
                                        e.printStackTrace();
                                        Toast.makeText(getContext(),"El servicio retorno un error inesperado",Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    vibrator.vibrate(400);
                                    habilitaProducto(true);
                                    Toast.makeText(getContext(),"El servicio no está disponible",Toast.LENGTH_LONG).show();
                                    error.printStackTrace();
                                }
                            });
                    queue.add(postRequest);
                }
            }
        });

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();

            }
        });

        return rootView;
    }

    public void setflag_enviand(boolean flag){
        this.lbo_enviando = flag;
    }

    public boolean getflag_enviando(){
        return this.lbo_enviando;
    }

    public void subirImagenes(String idcat,String idsubcat,String idpro){
        listaBase64Imagenes.clear();
        for(int i=0;i<listaImagenes.size(); i++){
            try {
                InputStream is = getActivity().getContentResolver().openInputStream(listaImagenes.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                String cadena = convertirUriToBase64(bitmap);
                enviarImagen(cadena,idcat,idsubcat,idpro);
                bitmap.recycle();
                if(getflag_enviando()){
                    Thread.sleep(500); //Esperamos medio segundo para elviar el siguiente
                }
            }catch (Exception ex){
                ex.getMessage();
            }
        }
    }

    public void enviarImagen(final String cadena,final String idcat,final String idsubcat,final String idpro){
        Map<String, String> data = new HashMap<>();
        setflag_enviand(true);
        data.put("idtienda", Integer.toString(apiconexion.getIdtienda()));
        data.put("imagen", cadena);
        data.put("idcat",idcat);
        data.put("idsubcat",idsubcat);
        data.put("idpro",idpro);
        data.put("iduser",Integer.toString(apiconexion.getIduser()));
        data.put("token", apiconexion.getToken());

        JSONObject jsonData = new JSONObject(data);

        String servicio = Servidor+"/subir_imagen";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, servicio,
                jsonData,
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
                                //=======================================OK==================================
                                setflag_enviand(false);
                            }else{
                                //vibrator.vibrate(400);
                                //habilitaProducto(true);
                                mensaje = Result.getString("error_message");
                                showToast(mensaje);
                            }
                        } catch (JSONException e) {
                            //El servio no trajo nada
                            //vibrator.vibrate(400);
                            //habilitaProducto(true);
                            e.printStackTrace();
                            Toast.makeText(getContext(),"El servicio de subida de imagenes retorno un error inesperado",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //vibrator.vibrate(400);
                        //habilitaProducto(true);
                        Toast.makeText(getContext(),"El servicio no está disponible",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });
        queue.add(postRequest);

    }

    public String convertirUriToBase64(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bytes = baos.toByteArray();
        String encode = Base64.encodeToString(bytes,Base64.DEFAULT);

        return encode;
    }




    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        galleryActivityResultLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        if(result.getResultCode() == getActivity().RESULT_OK){

                            Intent data = result.getData();
                            ClipData clipData = data.getClipData();
                            if(clipData == null){
                                // Para cuando solo elige una imagen
                                imagenUri = data.getData();
                                listaImagenes.add(imagenUri);
                            }else{
                                //mas de una imagen
                                for (int i = 0;i < clipData.getItemCount(); i++){
                                    listaImagenes.add(clipData.getItemAt(i).getUri());
                                }
                            }
                            baseAdapter = new GridViewAdapter(getContext(),listaImagenes);
                            gvImagenes.setAdapter(baseAdapter);

                        }else{
                            //cancelado
                            showToast("Selección Cancelada");
                        }
                    }catch (Exception ex){
                        ex.getMessage();
                    }

                }
            }
    );

    private void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    private void habilitaProducto(boolean abo){
        if (abo){
            pb_guardar.setVisibility(View.INVISIBLE);
            et_producto.setEnabled(abo);
            et_modelo.setEnabled(abo);
            et_observacion.setEnabled(abo);
        }else{
            pb_guardar.setVisibility(View.VISIBLE);
            et_producto.setEnabled(abo);
            et_modelo.setEnabled(abo);
            et_observacion.setEnabled(abo);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item ;
        item = adapterView.getItemAtPosition(i).toString();
        switch (adapterView.getId()){
            case R.id.sp_categoria:
                ls_categoria = item;
                apiconexion.setSpinners("subcategoria",sp_subcategoria,"&categoria="+item);
                break;
            case R.id.sp_subcategoria:
                ls_subcategoria = item;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
