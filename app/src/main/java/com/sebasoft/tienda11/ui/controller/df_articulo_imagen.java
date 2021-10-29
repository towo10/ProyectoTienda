package com.sebasoft.tienda11.ui.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.db.Usuario;
import com.sebasoft.tienda11.db.dbconfiguracion;
import com.sebasoft.tienda11.esquema.Producto;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class df_articulo_imagen extends DialogFragment {

    Producto producto;
    private String Servidor,title_imagen;
    private int idtienda,iduser;
    private ImageCarousel carousel;

    public df_articulo_imagen(Producto producto){
        this.producto = producto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.df_articulo_imagen, container);
        dbconfiguracion dbconf = new dbconfiguracion(view.getContext());
        Usuario cuser = new Usuario(getContext());
        cuser._iniciarDatosUsuario();
        Servidor = dbconf.getServidor();
        idtienda = cuser.getTienda();
        iduser = cuser.getUsuario();
        title_imagen = producto.getProducto();
        carousel = view.findViewById(R.id.carousel);
        carousel.registerLifecycle(getLifecycle());
        List<CarouselItem> list = new ArrayList<>();
        onCargarImagenes(list);
        return view;
    }

    private void onCargarImagenes(List<CarouselItem> list) {
        String servicio;
        servicio = Servidor+"/imagenes?tienda="+Integer.toString(idtienda)+
                "&categoria="+Integer.toString(producto.getIdcategoria())+
                "&subcategoria="+Integer.toString(producto.getIdsubcategoria())+
                "&producto="+Integer.toString(producto.getIdProducto());

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest postRequest = new StringRequest( Request.Method.GET, servicio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<String> categorias;
                        onListado(new String(response),list);
                        carousel.setData(list);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"No se pudo cargar imagenes",Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(postRequest);
    }
    public void onListado(String response,List<CarouselItem> list){
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                String url_imagen = Servidor+jsonArray.getJSONObject(i).getString("url_imagen");
                list.add(new CarouselItem(url_imagen,title_imagen));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
