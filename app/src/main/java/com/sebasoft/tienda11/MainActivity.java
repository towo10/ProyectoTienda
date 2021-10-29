package com.sebasoft.tienda11;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.material.navigation.NavigationView;
import com.sebasoft.tienda11.databinding.ActivityMainBinding;
import com.sebasoft.tienda11.db.Usuario;
import com.sebasoft.tienda11.db.dbconfiguracion;
import com.sebasoft.tienda11.esquema.Aplicacion;
import com.sebasoft.tienda11.ui.fragment.fragment_BuscarArticulo;
import com.sebasoft.tienda11.ui.fragment.fragment_CrearArticulo;
import com.sebasoft.tienda11.ui.fragment.fragment_EscanearFactura;
import com.sebasoft.tienda11.ui.fragment.fragment_Inicio;
import com.sebasoft.tienda11.ui.fragment.fragment_ListarArticulo;
import com.sebasoft.tienda11.ui.fragment.fragment_ListarOC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, fragment_CrearArticulo.OnFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private dbconfiguracion dbconf = new dbconfiguracion(this);
    public String Servidor;
    public int idfragment = 1;
    public Aplicacion app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.content_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        Servidor = dbconf.getServidor();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);

        //Iniciamos la configuracion principal
        iniciarConfiguracion(hView);

        //Abrimos la ventana de home al iniciar;
        Fragment fragment = new fragment_Inicio();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.app_bar_main,fragment)
                .commit();
        getSupportActionBar().setTitle("Inicio");


    }

    /* Esto es para crear un menu en la parte superior derecha, nos puede servir para
    la par de configuracion
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    private void iniciarConfiguracion(View hView){
        String servicio;
        int idtienda,iduser;
        Usuario cuser = new Usuario(MainActivity.this);
        app = (Aplicacion) getApplicationContext();
        app.setFragment(0);
        app.setArticulo_info(0);
        app.setServidor(Servidor);
        cuser._iniciarDatosUsuario();
        idtienda = cuser.getTienda();
        iduser = cuser.getUsuario();
        app.setIdtienda(idtienda);
        app.setIduser(iduser);
        app.setToken(cuser.getToken());

        TextView nav_user = (TextView) hView.findViewById(R.id.txtnameuser);
        TextView nav_emailuser = (TextView) hView.findViewById(R.id.txtemailuser);
        ImageView imageView = (ImageView) hView.findViewById(R.id.imageView);
        servicio = Servidor+"/sesion?tienda="+Integer.toString(idtienda)+"&usuario="+Integer.toString(iduser);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest( Request.Method.GET, servicio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jdata = new JSONArray(new String(response));
                            String nombre = jdata.getJSONObject(0).getString("nombre");
                            String correo = jdata.getJSONObject(0).getString("correo");
                            String imagen = Servidor+jdata.getJSONObject(0).getString("imagen");

                            nav_user.setText(nombre);
                            nav_emailuser.setText(correo);
                            //Glide.with(MainActivity.this).load(imagen).into(imageView);

                            Glide.with(getBaseContext()).asBitmap().load(imagen).centerCrop().into(new BitmapImageViewTarget(imageView) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(getBaseContext().getResources(),resource);
                                    circularBitmapDrawable.setCircular(true);
                                    imageView.setImageDrawable(circularBitmapDrawable);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(postRequest);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        boolean FragmentTransaction = false;
        Fragment fragment = null;
        String titulo = "";

        switch (id){
            case R.id.nav_inicio:
                idfragment = 1;
                fragment = new fragment_Inicio();
                FragmentTransaction = true;
                titulo = "Inicio";
                break;
            case R.id.nav_nuevo:
                idfragment = 2;
                fragment = new fragment_CrearArticulo();
                FragmentTransaction = true;
                titulo = "Nuevo Producto";
                break;
            case R.id.nav_listarpro:
                idfragment = 3;
                fragment = new fragment_ListarArticulo();
                FragmentTransaction = true;
                titulo = "Productos Nuevos";
                break;
            case R.id.nav_buscar:
                idfragment = 4;
                fragment = new fragment_BuscarArticulo();
                FragmentTransaction = true;
                titulo = "Buscar Productos";
                break;
            case R.id.nav_listaroc:
                idfragment = 5;
                fragment = new fragment_ListarOC();
                FragmentTransaction = true;
                titulo = "Ordenes de Compra";
                break;
            case R.id.nav_escanear:
                idfragment = 6;
                fragment = new fragment_EscanearFactura();
                FragmentTransaction = true;
                titulo = "Factura";
                break;
        }

        if(FragmentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.app_bar_main,fragment)
                    .commit();
            getSupportActionBar().setTitle(titulo);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {

        if(app.getFragment() > 0) {
            getSupportFragmentManager().popBackStack();
            app.setFragmenremove();
        }else if(idfragment > 2){
            idfragment = 1;
            Fragment fragment = new fragment_Inicio();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.app_bar_main,fragment)
                    .commit();
            getSupportActionBar().setTitle("Inicio");
        }
        else {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Tienda");
            dialogo1.setMessage("¿ Desea Cerrar la Sesión ?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    System.exit(0);
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    return;
                }
            });
            dialogo1.show();
        }
        return;

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}