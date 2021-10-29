package com.sebasoft.tienda11.ui.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.OrdenCompra;

import java.util.ArrayList;

public class adapter_OC_rv extends RecyclerView.Adapter<adapter_OC_rv.OrdenCompra_vh> {

    ArrayList<OrdenCompra> listaOC;
    final adapter_OC_rv.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(OrdenCompra item);

    }

    public adapter_OC_rv(ArrayList<OrdenCompra> listaOC, OnItemClickListener listener){
        this.listaOC = listaOC;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrdenCompra_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrdenCompra_vh(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ordencompra,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrdenCompra_vh holder, int position) {
        OrdenCompra item = listaOC.get(position);
        holder.tv_codigo.setText(listaOC.get(position).getCodigo());
        holder.tv_fecha.setText(listaOC.get(position).getFecha());
        holder.tv_descripcion.setText(listaOC.get(position).getDescripcion());
        holder.tv_proveedor.setText(listaOC.get(position).getProveedor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaOC.size();
    }

    static class OrdenCompra_vh extends RecyclerView.ViewHolder{

        TextView tv_codigo;
        TextView tv_fecha,tv_descripcion,tv_proveedor;

        public OrdenCompra_vh(@NonNull View itemView) {
            super(itemView);
            tv_codigo = itemView.findViewById(R.id.tv_oc_codigo);
            tv_fecha = itemView.findViewById(R.id.tv_oc_fecha);
            tv_descripcion = itemView.findViewById(R.id.tv_oc_descripcion);
            tv_proveedor = itemView.findViewById(R.id.tv_oc_proveedor);
        }
    }
}
