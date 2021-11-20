package com.sebasoft.tienda11.ui.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.api.apiConexion;
import com.sebasoft.tienda11.esquema.ordencompra_marca;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapter_oc_marca extends RecyclerView.Adapter<adapter_oc_marca.marca_vh> {

    ArrayList<ordencompra_marca> listamarca;
    private ordencompra_marca item;
    final adapter_oc_marca.OnItemClickListener listener;
    String Servidor;
    apiConexion apiconexion;
    Vibrator vibrator;
    Context context;
    private Activity activity;

    public interface OnItemClickListener{
        void onItemClick(ordencompra_marca item);

    }

    public adapter_oc_marca(ArrayList<ordencompra_marca> listamarca, OnItemClickListener listener, Activity activity){
        this.listamarca = listamarca;
        this.listener = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public marca_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        apiconexion = new apiConexion(parent.getContext());
        Servidor = apiconexion.getServidor();
        vibrator = (Vibrator) parent.getContext().getSystemService(parent.getContext().VIBRATOR_SERVICE);
        context = parent.getContext();
        return new marca_vh(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_oc_detalle_marca,parent,false));
    }
    public void removeItem(int position) {
        listamarca.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(@NonNull marca_vh holder, @SuppressLint("RecyclerView") int position) {
        item = listamarca.get(position);


        holder.tv_marca.setText(item.getMarca());
        holder.tv_producto.setText(item.getProducto());
        holder.tv_precio.setText(item.getSubtotal() +" "+item.getMoneda());
        holder.rv_color.setAdapter(new adapter_oc_marca_color(item.getListacolor()));
        holder.rv_color.setLayoutManager(new LinearLayoutManager(holder.rv_color.getContext()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // listener.onItemClick(item);
            }
        });
        holder.ib_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Modificar: "+Integer.toString(position),Toast.LENGTH_SHORT).show();
            }
        });
        holder.ib_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordencompra_marca product = listamarca.get(position);
                AlertDialog.Builder dialogo = new AlertDialog.Builder(v.getContext());
                dialogo.setTitle("Quitar Producto de la Orden de Compra");
                dialogo.setMessage("¿Quitar el total de "+product.getProducto()+" "+product.getMarca()+"?");
                dialogo.setCancelable(false);

                dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        // pb_guardar.setVisibility(getView().VISIBLE);
                        onEnviarCompra(product.getOcompra_id(), product.getCat_codigo(), product.getSubcat_codigo(),
                                product.getPro_codigo(), product.getMarca_codigo(), product.getMoneda_codigo(), "2");
                        removeItem(position);
                        if (getItemCount() == 0) {
                            activity.onBackPressed();
                        }
                    }

                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        // TODO por ahora no hacemos nada...
                    }
                });
                dialogo.show();
            }
        });
        holder.ib_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordencompra_marca product = listamarca.get(position);
                AlertDialog.Builder dialogo = new AlertDialog.Builder(v.getContext());
                dialogo.setTitle("Enviar Producto de la Orden de Compra");
                dialogo.setMessage("¿Confirmas el total de "+product.getProducto()+" "+product.getMarca()+"?");
                dialogo.setCancelable(false);

                dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        // pb_guardar.setVisibility(getView().VISIBLE);
                        onEnviarCompra(product.getOcompra_id(), product.getCat_codigo(), product.getSubcat_codigo(),
                                product.getPro_codigo(), product.getMarca_codigo(), product.getMoneda_codigo(), "7");
                        removeItem(position);
                        if (getItemCount() == 0) {
                            activity.onBackPressed();
                        }
                    }

                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        // TODO por ahora no hacemos nada...
                    }
                });
                dialogo.show();
            }
        });
    }

    public void onEnviarCompra(String compra,String categoria,String subcategoria,
                               String producto,String marca,String moneda,String estado){
        String servicio;
        Map<String, String> data = new HashMap<>();

        servicio = Servidor+"/orden_compra";
        data.put("compra", compra);
        data.put("categoria", categoria);
        data.put("subcategoria", subcategoria);
        data.put("producto",producto);
        data.put("marca", marca);
        data.put("moneda", moneda);
        data.put("estado",estado);
        data.put("token", apiconexion.getToken());
        JSONObject jsonData = new JSONObject(data);

        RequestQueue queue = Volley.newRequestQueue(context);
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

                                //Toast.makeText(context,"OK",Toast.LENGTH_LONG).show();
                            }else{
                                vibrator.vibrate(400);
                                mensaje = Result.getString("error_message");
                                Toast.makeText(context,mensaje,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            //El servio no trajo nada
                            vibrator.vibrate(400);
                            e.printStackTrace();
                            Toast.makeText(context,"El servicio retorno un error inesperado",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        vibrator.vibrate(400);
                        Toast.makeText(context,"El servicio no está disponible",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });
        queue.add(postRequest);

    }

    @Override
    public int getItemCount() {
        return listamarca.size();
    }

    static class marca_vh extends RecyclerView.ViewHolder{

        TextView tv_marca,tv_producto,tv_precio;
        ImageButton ib_modificar,ib_eliminar,ib_enviar;
        RecyclerView rv_color;

        public marca_vh(@NonNull View itemView) {
            super(itemView);

            tv_marca = itemView.findViewById(R.id.tv_oc_marca);
            tv_producto = itemView.findViewById(R.id.tv_oc_marca_producto);
            tv_precio = itemView.findViewById(R.id.tv_oc_marca_precio);
            rv_color = itemView.findViewById(R.id.rv_oc_marca_color);
            ib_modificar = itemView.findViewById(R.id.ib_oc_modificar);
            ib_eliminar = itemView.findViewById(R.id.ib_oc_eliminar);
            ib_enviar = itemView.findViewById(R.id.ib_oc_enviar);

        }
    }
}
