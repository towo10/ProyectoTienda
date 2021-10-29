package com.sebasoft.tienda11.ui.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.esquema.ProductoNuevo;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class df_ProductoNuevo_Imagen extends DialogFragment {
    protected ProductoNuevo productoNuevo;
    String Servidor;
    Aplicacion app;
    JSONArray jimagen;
    private ImageCarousel carousel;

    public df_ProductoNuevo_Imagen(ProductoNuevo productoNuevo){
        this.productoNuevo = productoNuevo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.df_articulo_imagen, container);
        app = (Aplicacion) view.getContext().getApplicationContext();
        Servidor = app.getServidor();
        jimagen = productoNuevo.getJimagenes();
        String url_imagen,title_imagen;

        title_imagen = productoNuevo.getProducto();
        List<CarouselItem> list = new ArrayList<>();

        carousel = view.findViewById(R.id.carousel);
        carousel.registerLifecycle(getLifecycle());

        for (int i = 0; i < jimagen.length(); i++) {
            try {

                url_imagen = Servidor+jimagen.getJSONObject(i).getString("dsc_dir");
                list.add(new CarouselItem(url_imagen,title_imagen));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        carousel.setData(list);

        return view;
    }

}
