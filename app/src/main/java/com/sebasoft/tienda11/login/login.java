package com.sebasoft.tienda11.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sebasoft.tienda11.MainActivity;
import com.sebasoft.tienda11.R;
import com.sebasoft.tienda11.db.Usuario;
import com.sebasoft.tienda11.db.dbconfiguracion;
import com.sebasoft.tienda11.ui.fragment.asignar_servidor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity implements asignar_servidor.nuevocursorlistener,EditText.OnEditorActionListener{
    String Servidor;
    EditText etx_usuario;
    EditText etx_pass;
    TextView etv_mensaje;
    ProgressBar pb_iniciar;
    Integer flag= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etx_usuario = (EditText)  findViewById(R.id.et_usuario);
        etx_pass = (EditText) findViewById(R.id.et_pass);
        etv_mensaje = (TextView) findViewById(R.id.tv_mensaje);
        pb_iniciar = (ProgressBar) findViewById(R.id.pb_iniciar);
        etx_pass.setOnEditorActionListener(this);

        //Regresamos el nombre del servidor
        dbconfiguracion dbconf = new dbconfiguracion(login.this);

        Servidor = null;
        if (dbconf.existeConfig()){
            Servidor = dbconf.getServidor();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            // Aca vamos agregar el codigo
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(view.getContext());
                dialogo.setTitle("SERVIDOR");
                dialogo.setMessage("¿Ver o Modificar Servidor?");
                dialogo.setCancelable(false);

                dialogo.setPositiveButton("VER",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogo, int id){
                        String Resp;
                         if (Servidor==null){
                             Resp = "No hay un servidor asignado";
                         }else{
                             Resp = Servidor;
                         }
                        Snackbar.make(view, Resp, Snackbar.LENGTH_INDEFINITE)
                                .setAction("Action", null).show();
                    }

                });
                dialogo.setNegativeButton("MODIFICAR",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogo, int id){
                        asignar_servidor aserv = new asignar_servidor(Servidor);
                        aserv.show(getFragmentManager(),"Personal");

                        android.app.Fragment frag = getFragmentManager().findFragmentByTag("personal");
                        if (frag!=null){
                            getFragmentManager().beginTransaction().remove(frag).commit();
                        }return;
                    }

                });
                dialogo.show();
            }
        });
    }
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (etx_usuario.getText().toString().length()>0){
            if (etx_pass.getText().toString().length()>0){
                String Resp;
                if (Servidor==null){
                    Toast.makeText(login.this,"No hay un servidor asignado.", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        Conectar();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(login.this,"El error en el servicio.", Toast.LENGTH_LONG).show();
                    }
                }
            }else {
                Toast.makeText(login.this,"Ingresa una contraseña.", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(login.this,"Ingresa un usuario.", Toast.LENGTH_LONG).show();
        }
        return true;

    }

    public void Conectar() throws JSONException {
        String user,password,servicio;
        Map<String, String> data = new HashMap<>();

        user = etx_usuario.getText().toString();
        password = etx_pass.getText().toString();

        //Creamos el json que vamos a enviar
        data.put("user", user);
        data.put("password", password);
        JSONObject jsonData = new JSONObject(data);

        //Accionamos los controles
        etv_mensaje.setVisibility(View.VISIBLE);
        pb_iniciar.setVisibility(View.VISIBLE);
        etx_usuario.setEnabled(false);
        etx_pass.setEnabled(false);
        etv_mensaje.setText("Conectando...");
        flag = 1;
        Vibrator vibrator = (Vibrator) getSystemService(login.this.VIBRATOR_SERVICE);

        //consumimos el api
        servicio = Servidor + "/auth";


        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, servicio,
                jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int idtienda,idusuario;
                        String  token;
                        JSONObject Obj,Result;
                        Obj = response;
                        String  status;

                        try {
                            status = Obj.getString("status");
                            Result = Obj.getJSONObject("result");
                            if (status.equals("ok")){
                                // Post conexion
                                idtienda = Integer.parseInt(Result.getString("idtienda"));
                                idusuario = Integer.parseInt(Result.getString("idusuario"));
                                token = Result.getString("token");
                                Usuario usuario = new Usuario(login.this);
                                usuario.setUsuario(idtienda,idusuario,token);

                                startActivity(new Intent(login.this, MainActivity.class));
                                etv_mensaje.setText("");
                                etx_usuario.setText("");
                                etx_pass.setText("");
                                etv_mensaje.setVisibility(View.INVISIBLE);
                                etx_usuario.setEnabled(true);
                                etx_pass.setEnabled(true);
                                pb_iniciar.setVisibility(View.INVISIBLE);
                            }else{
                                    vibrator.vibrate(400);
                                    etv_mensaje.setText("Usuario o Contraseña Incorrecto");
                                    pb_iniciar.setVisibility(View.INVISIBLE);
                                    etx_usuario.setEnabled(true);
                                    etx_pass.setEnabled(true);
                                    findViewById(R.id.et_pass).requestFocus();
                            }
                        } catch (JSONException e) {
                            //El servio no trajo nada
                            e.printStackTrace();
                            vibrator.vibrate(400);
                            etv_mensaje.setText("Error en el Servicio API");
                            pb_iniciar.setVisibility(View.INVISIBLE);
                            etx_usuario.setEnabled(true);
                            etx_pass.setEnabled(true);
                            findViewById(R.id.et_pass).requestFocus();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        vibrator.vibrate(400);
                        etv_mensaje.setText("Servicio no disponible");
                        pb_iniciar.setVisibility(View.INVISIBLE);
                        etx_usuario.setEnabled(true);
                        etx_pass.setEnabled(true);
                        findViewById(R.id.et_pass).requestFocus();
                        Toast.makeText(login.this, "Servicio no disponible", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

        queue.add(postRequest);
    }

    @Override
    public void FINALIZA_CUADRO_DIALOGO(String texto) {
        if (texto.contains("http://") || texto.contains("https://")){
            Servidor = texto;
        }else{
            Servidor = "http://"+texto;
        }
        Snackbar.make(findViewById(R.id.fab),texto, Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show();
        try{
            dbconfiguracion dbconf = new dbconfiguracion(login.this);
            /**==============================================================================================VERSION DE LA APLICACION**/
            dbconf.setDBconfig(texto,getString(R.string.version));

        }catch (Exception ex){
            Toast.makeText(login.this, "Vuelva a intentar grabar el servidor", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed(){

        if (flag==0){
            System.exit(0);
        }else {
            etx_pass.setText("");
            findViewById(R.id.et_pass).requestFocus();
            etx_usuario.setEnabled(true);
            etx_pass.setEnabled(true);
            etv_mensaje.setVisibility(View.INVISIBLE);
            etx_usuario.setVisibility(View.VISIBLE);
            etx_pass.setVisibility(View.VISIBLE);
            pb_iniciar.setVisibility(View.INVISIBLE);
            flag=0;
            Toast.makeText(login.this, "Se canceló el ingreso.", Toast.LENGTH_SHORT).show();
        }
    }
}