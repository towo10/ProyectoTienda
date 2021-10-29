package com.sebasoft.tienda11.esquema;

import android.app.Application;

public class Aplicacion extends Application {
    private int fragment = 0;
    private int articulo_info;
    private String servidor = "";
    private int idtienda = 0;
    private int iduser = 0;
    private String token ="";

    public String getServidor() {
        return servidor;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public void setIdtienda(int idtienda) {
        this.idtienda = idtienda;
    }

    public int getIdtienda() {
        return idtienda;
    }

    public int getIduser() {
        return iduser;
    }

    public String getToken() {
        return token;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public void setFragment(int fragment) {
        this.fragment = this.fragment;
    }

    public void  setFragmenadd(){
        this.fragment++;
    }
    public void  setFragmenremove(){
        this.fragment--;
    }

    public int getFragment() {
        return fragment;
    }

    public void setArticulo_info(int articulo_info) {
        this.articulo_info = articulo_info;
    }

    public int getArticulo_info() {
        return articulo_info;
    }
}