package com.sebasoft.tienda11.ui.controller;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.TableDynamic;
import com.sebasoft.tienda11.esquema.ordencompra_colores;
import com.sebasoft.tienda11.esquema.ordencompra_talles;

import java.util.ArrayList;
import java.util.List;

public class adapter_oc_marca_color  extends RecyclerView.Adapter<adapter_oc_marca_color.color_vh> {
    ArrayList<ordencompra_colores> listacolores;


    public interface OnItemClickListener{
        void onItemClick(ordencompra_colores item);
    }

    public adapter_oc_marca_color(ArrayList<ordencompra_colores> listacolores){
        this.listacolores = listacolores;
    }

    @NonNull
    @Override
    public color_vh onCreateViewHolder(@NonNull ViewGroup parent,int ViewType){
        return new color_vh(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_oc_detalle_marca_color,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull color_vh holder, int position) {
        ordencompra_colores item = listacolores.get(position);
        ArrayList<ordencompra_talles> talles = item.getListatalle();
        ArrayList<String[]> rows = new ArrayList<>();

        holder.tv_color.setText(item.getColor());
        holder.tv_precio.setText(item.getSubtotal());
        TableDynamic tableDynamic = new TableDynamic(holder.tb_datos,holder.tb_datos.getContext());
        tableDynamic.addHeader(holder.header);
        tableDynamic.addData(getOCdetalle(talles));
        tableDynamic.backGroundHeader(Color.rgb(133,146,159));
        tableDynamic.backGroundData(Color.WHITE,Color.rgb(235,237,239));

    }

    @Override
    public int getItemCount() {
        return listacolores.size();
    }

    private ArrayList<String []> getOCdetalle(ArrayList<ordencompra_talles> p_talles){
        ArrayList<String[]> p_rows = new ArrayList<>();
        double total = 0;
        for(int i=0;i<p_talles.size();i++){
            p_rows.add(new String[]{
                    p_talles.get(i).getTalla(),
                    p_talles.get(i).getCantidad(),
                    p_talles.get(i).getPrecio(),
                    p_talles.get(i).getSubtotal(),
            });
            total = total + Double.parseDouble(p_talles.get(i).getSubtotal());
        }
        p_rows.add(new String[]{"","","Total",String.valueOf(total)});
        
        return p_rows;
    }

    static class color_vh extends RecyclerView.ViewHolder{

        TextView tv_color,tv_precio;
        private TableLayout tb_datos;
        private String[]header={"Talla","Cant.","Prec.Uni.","SubTotal"};

        public color_vh(@NonNull View itemView) {
            super(itemView);

            tv_color = itemView.findViewById(R.id.tv_oc_marca_color);
            tv_precio = itemView.findViewById(R.id.tv_oc_marca_color_precio);
            tb_datos = itemView.findViewById(R.id.tb_oc_marca_talle);

        }
    }

}
