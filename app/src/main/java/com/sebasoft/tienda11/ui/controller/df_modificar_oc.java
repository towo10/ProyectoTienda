package com.sebasoft.tienda11.ui.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.api.apiConexion;
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.esquema.ordencompra_marca;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class df_modificar_oc extends DialogFragment implements AdapterView.OnItemSelectedListener{
    private ordencompra_marca product;
    private String Servidor,ls_color,ls_talla;
    private Aplicacion app;
    private TextView tv_producto,tv_marca;
    private Spinner sp_color,sp_talla;
    private EditText et_cantidad;
    private Button btn_guardar;
    private String codigo_detalle,codigo;
    private Activity activity;
    private Context context;
    apiConexion apiconexion;

    public df_modificar_oc(ordencompra_marca product,Activity activity,Context context){
        this.product = product;
        this.activity = activity;
        this.context = context;
        apiconexion = new apiConexion(context);
        Servidor = apiconexion.getServidor();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.df_modificar_oc_vista, container);



        tv_producto = (TextView) view.findViewById(R.id.df_oc_producto);
        tv_marca = (TextView) view.findViewById(R.id.df_oc_marca);
        sp_color = (Spinner) view.findViewById(R.id.sp_color);
        sp_talla = (Spinner) view.findViewById(R.id.sp_talla);
        et_cantidad = (EditText) view.findViewById(R.id.et_cantidad_oc);
        btn_guardar = (Button) view.findViewById(R.id.btn_oc_guardar);
        codigo = product.getOcompra_id();

        tv_producto.setText(product.getProducto());
        tv_marca.setText(product.getMarca());

        ArrayList<String> color = new ArrayList<String>();
        for (int i=0;i<product.getListacolor().size();i++){
            color.add(product.getListacolor().get(i).getColor());

        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, color);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_color.setAdapter(dataAdapter);
        sp_color.setOnItemSelectedListener(this);
        sp_talla.setOnItemSelectedListener(this);
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActualizarCantidad();
            }
        });

        return view;
    }

    public void onActualizarCantidad() {
        String servicio;
        Map<String, String> data = new HashMap<>();
        servicio = Servidor + "/orden_compra_editar";

        data.put("compra", product.getOcompra_id());
        data.put("tipo", "1");
        data.put("detalle", codigo_detalle);
        data.put("cantidad", et_cantidad.getText().toString());
        data.put("token", apiconexion.getToken());
        JSONObject jsonData = new JSONObject(data);

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, servicio,
                jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject Obj, Result;
                        Obj = response;
                        String status, mensaje, sidcat, sidsubcat, sidpro;
                        try {
                            status = Obj.getString("status");
                            Result = Obj.getJSONObject("result");
                            if (status.equals("ok")) {
                                activity.onBackPressed();
                                dismiss();
                            } else {
                                mensaje = Result.getString("error_message");
                                Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            //El servio no trajo nada
                            e.printStackTrace();
                            Toast.makeText(context, "El servicio retorno un error inesperado", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "El servicio no está disponible", Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });
        queue.add(postRequest);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item ;
        item = parent.getItemAtPosition(position).toString();

        switch (parent.getId()){
            case R.id.sp_color:
                ls_color = item;
                ArrayList<String> talle = new ArrayList<String>();
                for (int i=0;i<product.getListacolor().size();i++){
                    if (ls_color == product.getListacolor().get(i).getColor()){
                        for(int j=0;j<product.getListacolor().get(i).getListatalle().size();j++){
                            talle.add(product.getListacolor().get(i).getListatalle().get(j).getTalla());
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, talle);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_talla.setAdapter(dataAdapter);
                        //et_cantidad.setText();

                    }
                }
                break;
            case R.id.sp_talla:
                ls_talla = item;
                System.out.println(ls_talla);
                for (int i=0;i<product.getListacolor().size();i++){
                    for(int j=0;j<product.getListacolor().get(i).getListatalle().size();j++){
                        System.out.println(product.getListacolor().get(i).getListatalle().get(j).getTalla());
                        if (ls_color == product.getListacolor().get(i).getColor() &&
                                ls_talla == product.getListacolor().get(i).getListatalle().get(j).getTalla()){

                            et_cantidad.setText(product.getListacolor().get(i).getListatalle().get(j).getCantidad());
                            codigo_detalle = product.getListacolor().get(i).getListatalle().get(j).getCodigo_detalle();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
