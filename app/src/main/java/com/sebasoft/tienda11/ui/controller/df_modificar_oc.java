package com.sebasoft.tienda11.ui.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.esquema.ordencompra_marca;

import java.util.ArrayList;

public class df_modificar_oc extends DialogFragment implements AdapterView.OnItemSelectedListener{
    private ordencompra_marca product;
    private String Servidor,ls_color,ls_talla;
    private Aplicacion app;
    private TextView tv_producto,tv_marca;
    private Spinner sp_color,sp_talla;
    private EditText et_cantidad;

    public df_modificar_oc(ordencompra_marca product){
        this.product = product;
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

        return view;
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
