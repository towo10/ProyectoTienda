package com.sebasoft.tienda11.ui.controller;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.api.apiConexion;
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.esquema.ProductoNuevo;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class df_ProductoNuevo_modificar extends DialogFragment implements  AdapterView.OnItemSelectedListener{

    protected ProductoNuevo productoNuevo;
    String Servidor,ls_categoria,ls_subcategoria;
    Aplicacion app;
    JSONArray jimagen;
    Spinner sp_categoria,sp_subcategoria;
    EditText et_producto,et_modelo,et_observacion;
    ProgressBar pb_guardar;
    apiConexion apiconexion;
    private Button btnGaleria;
    private GridView gvImagenes;
    public df_ProductoNuevo_modificar(ProductoNuevo productoNuevo){
        this.productoNuevo = productoNuevo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.df_creararticulo, container);
        String url_imagen,title_imagen;

        app = (Aplicacion) view.getContext().getApplicationContext();
        Servidor = app.getServidor();
        jimagen = productoNuevo.getJimagenes();

        TabHost tabs = (TabHost) view.findViewById(R.id.tabhostnuevo);
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

        sp_categoria = (Spinner) view.findViewById(R.id.sp_categoria1);
        sp_subcategoria = (Spinner) view.findViewById(R.id.sp_subcategoria1);
        et_producto = (EditText) view.findViewById(R.id.et_producto1);
        et_modelo = (EditText) view.findViewById(R.id.et_modelo1);
        et_observacion = (EditText) view.findViewById(R.id.et_observacion1);
        pb_guardar = (ProgressBar) view.findViewById(R.id.pb_guardar1);
        btnGaleria = (Button) view.findViewById(R.id.btnGaleria1);
        gvImagenes = (GridView) view.findViewById(R.id.gvImagenes1);
        sp_categoria.setOnItemSelectedListener(this);
        sp_subcategoria.setOnItemSelectedListener(this);


        apiconexion = new apiConexion(getContext());
        apiconexion.setSpinnersSelected("categoria",sp_categoria,"",
                                        productoNuevo.getCategoria());
        apiconexion.setSpinnersSelected("subcategoria",sp_subcategoria,
                                        "&categoria="+productoNuevo.getCategoria(),
                                        productoNuevo.getSubcategoria());

        title_imagen = productoNuevo.getProducto();
        et_producto.setText(title_imagen);
        et_modelo.setText(productoNuevo.getModelo());
        et_observacion.setText(productoNuevo.getObservacion());
        sp_categoria.setSelected(true);



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
        FloatingActionButton fab_guardar = (FloatingActionButton) view.findViewById(R.id.fab_prod_guardar);
        fab_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO aca tenemos que actualizar los datos del producto
                // TODO tengo que ver como poder eliminarlo directamente
                Toast.makeText(getContext(),"Modificar",Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton fab_eliminar = (FloatingActionButton) view.findViewById(R.id.fab_prod_eliminar);
        fab_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO aca tenemos que actualizar los datos del producto
                // TODO tengo que ver como poder eliminarlo directamente
                Toast.makeText(getContext(),"Eliminar",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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
}
