package com.sebasoft.tienda11.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class dbconfiguracion extends dbHelper{
    Context context;
    public dbconfiguracion(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public void setDBconfig(String servidor,String version){
        if (existeConfig())
            updateDBconfig(servidor,version);
        else
            setservidor(servidor,version);
    }

    public long setservidor(String servidor,String version){
        long id = 0;
        try {

            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("servidor", servidor);
            values.put("version", version);

            id =  db.insert(TABLE_CONF, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return  id;
    }
    public String getServidor(){
        String  Query,Servidor;
        Cursor cur = null;
        Servidor = "";
        try {
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            Query = "select servidor from " + TABLE_CONF +
                    " where id = 1";
            cur = db.rawQuery(Query, null);
            if (cur.moveToFirst()) {
                do {
                    Servidor = cur.getString(0);
                }while (cur.moveToNext());
            }
        }catch (Exception ex){
            ex.toString();
        }
        return Servidor;
    }
    public void updateDBconfig(String servidor,String version){
        try{
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("servidor", servidor);
            cv.put("version", version);
            db.update(TABLE_CONF, cv, "id = ?", new String[]{"1"});
        }catch (Exception ex){
            ex.toString();
        }
    }

    public boolean existeConfig(){
        Cursor cur = null;
        int total = 0;
        try {
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            String Query = "select count(*) from " + TABLE_CONF +
                    " where id = 1";
            cur = db.rawQuery(Query, null);
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
