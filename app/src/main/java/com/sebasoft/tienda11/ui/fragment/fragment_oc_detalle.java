package com.sebasoft.tienda11.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.esquema.DetalleOrdenCompra;
import com.sebasoft.tienda11.esquema.OrdenCompra;
import com.sebasoft.tienda11.esquema.TableDynamic;
import com.sebasoft.tienda11.esquema.ordencompra_marca;
import com.sebasoft.tienda11.ui.controller.adapter_OC_rv;
import com.sebasoft.tienda11.ui.controller.adapter_oc_marca;

import java.util.ArrayList;

public class fragment_oc_detalle extends Fragment {
    private OrdenCompra item;
    private ArrayList<ordencompra_marca> listamarca;
    RecyclerView rv_listar;
    private Aplicacion app;
    private String Servidor,token,userid,tiendaid;
    //private TableLayout tb_datos;
    //private String[]header={"Talla","Cant.","Prec.Uni.","subTotal"};
    //private ArrayList<String[]> rows = new ArrayList<>();

    public fragment_oc_detalle(OrdenCompra item){
        this.item = item;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oc_detalle, container, false);

        app = (Aplicacion) view.getContext().getApplicationContext();
        Servidor = app.getServidor();
        userid = Integer.toString(app.getIduser());
        tiendaid = Integer.toString(app.getIdtienda());
        token = app.getToken();

        rv_listar = (RecyclerView) view.findViewById(R.id.rv_oc_marca2);
        rv_listar.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listamarca = item.getlistamarca();

        rv_listar.setAdapter(new adapter_oc_marca(listamarca, new adapter_oc_marca.OnItemClickListener() {
            @Override
            public void onItemClick(ordencompra_marca item) {
/*
                Fragment frag = new fragment_oc_detalle(item);
                FragmentManager fmanager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fmanager.beginTransaction();
                fragmentTransaction.replace(R.id.app_bar_main, frag);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                app.setFragmenadd();

 */


            }
        }));





        //listaDetalle = item.getListaoc();

        //detalle = listaDetalle.get(0);

        //TextView tv_marca = (TextView) view.findViewById(R.id.tv_oc_marca);
        //tb_datos = (TableLayout) view.findViewById(R.id.tb_oc_datos);

        //tv_marca.setText(detalle.getMarca());

        //TableDynamic tableDynamic = new TableDynamic(tb_datos,getContext());
        //tableDynamic.addHeader(header);
        //tableDynamic.addData(getOCdetalle());
        //tableDynamic.backGroundHeader(Color.rgb(55,0,179));
        //tableDynamic.backGroundData(Color.WHITE,Color.rgb(179,191,255));

        return view;
    }
    /*
    private ArrayList<String []> getOCdetalle(){
        rows.add(new String[]{"S","5","100","500"});
        rows.add(new String[]{"M","5","100","500"});
        rows.add(new String[]{"L","5","100","500"});
        rows.add(new String[]{"XL","5","100","500"});
        rows.add(new String[]{"XL","5","100","500"});
        rows.add(new String[]{"","","Total","2000"});

        return rows;
    }
    */


}
