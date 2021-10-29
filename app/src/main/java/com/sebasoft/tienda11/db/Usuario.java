package com.sebasoft.tienda11.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class Usuario extends dbHelper {
    Context context;
    String  sQuery,token;
    private int idtienda;
    private int iduser;
    public Usuario(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public void _iniciarDatosUsuario(){
        Cursor cur = null;
        try{
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            // sQuery = "select idtienda,idusuario,token from " + TABLE_USUARIO ;
            sQuery = "select b.idtienda,b.idusuario,b.token\n" +
                    "from "+TABLE_SESSION+" a,"+TABLE_USUARIO+" b\n" +
                    "where a.idtienda = b.idtienda and a.idusuario = b.idusuario";

            cur = db.rawQuery(sQuery, null);
            if (cur.moveToFirst()) {
                do{
                    this.idtienda = cur.getInt(0);
                    this.iduser = cur.getInt(1);
                    this.token = cur.getString(2);
                }while (cur.moveToNext());
            }
        }catch (Exception ex){
            ex.getMessage();
        }
    }
    public int getTienda(){
        return this.idtienda;
    }
    public int getUsuario(){
        return this.iduser;
    }
    public String getToken(){ return this.token; }

    public void setUsuario(int idtienda,int iduser, String token){

        if (existeSesion()){
            updateSesion(idtienda,iduser);
        }else{
            insertSession(idtienda,iduser);
        }
        if(existeConfig(idtienda,iduser)){
            updateUsuario(idtienda,iduser,token);
        }else{
            insertUsuario(idtienda,iduser,token);
        }
    }

    private void updateSesion(int idtienda, int iduser){
        try{

            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("idusuario",iduser);
            cv.put("idtienda",idtienda);
            db.update(TABLE_SESSION, cv, null,null);
        }catch (Exception ex){
            ex.toString();
        }
    }

    private long insertSession(int idtienda, int iduser){
        long id =0;
        try {
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("idtienda", idtienda);
            values.put("idusuario", iduser);
            id =  db.insert(TABLE_SESSION, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    private long insertUsuario(int idtienda, int iduser, String token){
        long id =0;
        try {
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("idtienda", idtienda);
            values.put("idusuario", iduser);
            values.put("token",token);
            id =  db.insert(TABLE_USUARIO, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    private void updateUsuario(int idtienda, int iduser, String token){
        try{
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("token",token);
            db.update(TABLE_USUARIO, cv, "idusuario = ? and idtienda = ?",
                    new String[]{Integer.toString(iduser),Integer.toString(idtienda)});
        }catch (Exception ex){
            ex.toString();
        }
    }


    public boolean existeConfig(int idtienda,int iduser){
        Cursor cur = null;
        int total = 0;
        try {
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            sQuery = "select count(*) from " + TABLE_USUARIO +
                    " where idtienda = ? and idusuario = ?";
            cur = db.rawQuery(sQuery, new String[]{Integer.toString(idtienda),Integer.toString(iduser)});
            if (cur.moveToFirst()) {
                do{
                    total = cur.getInt(0);
                }while (cur.moveToNext());
            }
            cur.close();

            if (total == 0) {

                return false;
            }
        }catch (Exception ex){
            ex.toString();
        }
        return true;
    }

    public boolean existeSesion(){
        Cursor cur = null;
        int total = 0;
        try {
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            sQuery = "select count(*) from " + TABLE_SESSION;
            cur = db.rawQuery(sQuery, null);
            if (cur.moveToFirst()) {
                do{
                    total = cur.getInt(0);
                }while (cur.moveToNext());
            }
            cur.close();
            if (total == 0) {
                return false;
            }
        }catch (Exception ex){
            ex.toString();
        }
        return true;
    }

}
