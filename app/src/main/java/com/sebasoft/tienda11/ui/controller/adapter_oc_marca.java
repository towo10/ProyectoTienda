package com.sebasoft.tienda11.ui.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.ordencompra_colores;
import com.sebasoft.tienda11.esquema.ordencompra_marca;

import java.util.ArrayList;

public class adapter_oc_marca extends RecyclerView.Adapter<adapter_oc_marca.marca_vh> {

    ArrayList<ordencompra_marca> listamarca;
    private ordencompra_marca item;
    final adapter_oc_marca.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(ordencompra_marca item);

    }

    public adapter_oc_marca(ArrayList<ordencompra_marca> listamarca, OnItemClickListener listener){
        this.listamarca = listamarca;
        this.listener = listener;

    }

    @NonNull
    @Override
    public marca_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new marca_vh(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_oc_detalle_marca,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull marca_vh holder, int position) {
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
                Toast.makeText(v.getContext(), "Modificar: "+ item.getOcompra_id(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.ib_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Eliminar",Toast.LENGTH_SHORT).show();
            }
        });
        holder.ib_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Enviar",Toast.LENGTH_SHORT).show();
            }
        });

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
