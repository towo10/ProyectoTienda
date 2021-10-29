package com.sebasoft.tienda11.api;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sebasoft.tienda11.db.Usuario;
import com.sebasoft.tienda11.db.dbconfiguracion;
import com.sebasoft.tienda11.esquema.Producto;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class apiConexion {
    private Context context;
    private dbconfiguracion dbconf;
    private String Servidor,token;
    private Producto producto;
    private int idtienda,iduser;
    private Usuario cuser;

    public apiConexion(@Nullable Context context){
        this.context = context;
        dbconf = new dbconfiguracion(context);
        cuser = new Usuario(context);
        cuser._iniciarDatosUsuario();
        Servidor = dbconf.getServidor();
        idtienda = cuser.getTienda();
        iduser = cuser.getUsuario();
        token = cuser.getToken();

    }

    public int getIdtienda() {
        return idtienda;
    }
    public int getIduser() {
        return iduser;
    }
    public String getToken() {
        return token;
    }
    public String getServidor() {
        return Servidor;
    }


    public void onEnviarPOST(JSONObject jdata, String recurso){
        String servicio = Servidor+"/"+recurso;

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, servicio,
                jdata,
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
                                Toast.makeText(context,"Datos Guardados correctamente",Toast.LENGTH_LONG).show();
                            }else{
                                //vibrator.vibrate(400);
                                mensaje = Result.getString("error_message");
                                Toast.makeText(context,mensaje,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            //El servio no trajo nada
                            e.printStackTrace();
                            Toast.makeText(context,"El servicio retorno un error inesperado",Toast.LENGTH_LONG).show();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"El servicio no está disponible",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });
        queue.add(postRequest);
    }

    //===================================================METHOD GET-> Listar , sirve para llenar spinner

    public void setSpinners(String recurso, Spinner spinner, String adicionalUrl){

        String servicio = Servidor+"/"+recurso+"?tienda="+Integer.toString(idtienda)+adicionalUrl;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest( Request.Method.GET, servicio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<String> categorias;
                        categorias = onListado(recurso,new String(response));
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, categorias);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"No se pudo cargar "+recurso+" del servidor",Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(postRequest);
    }
    public void setSpinnersSelected(String recurso, Spinner spinner, String adicionalUrl,String selected){

        String servicio = Servidor+"/"+recurso+"?tienda="+Integer.toString(idtienda)+adicionalUrl;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest( Request.Method.GET, servicio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<String> categorias;
                        categorias = onListado(recurso,new String(response));
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, categorias);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);

                        spinner.setSelection(getIndex(spinner,selected));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"No se pudo cargar "+recurso+" del servidor",Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(postRequest);
    }




    /*======================================================================================================================
     *
     * ARREGLOS DE LOS LISTADOS solo para Spinner
     * ======================================================================================================================*/
    public ArrayList<String> onListado(String recurso,String response){
        ArrayList<String> listado = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                listado.add(jsonArray.getJSONObject(i).getString(recurso));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listado;
    }


    /*Devuelve el Index del Spiner String*/
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

}
