package com.sebasoft.tienda11.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NOMBRE = "comercio.db";
    public  static final String TABLE_CONF = "config";
    public  static final String TABLE_USUARIO = "usuario";
    public  static final String TABLE_SESSION = "sesion";
    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Query;
        try {
            Query = "CREATE TABLE " + TABLE_CONF + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "servidor TEXT not null," +
                    "version TEXT not null)";

            sqLiteDatabase.execSQL(Query);
            Query = "CREATE TABLE " + TABLE_USUARIO + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idtienda INTEGER not null," +
                    "idusuario INTEGER not null," +
                    "token TEXT not null)";
            sqLiteDatabase.execSQL(Query);
            Query = "CREATE TABLE " + TABLE_SESSION + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idtienda INTEGER not null," +
                    "idusuario INTEGER not null)";
            sqLiteDatabase.execSQL(Query);

        }catch (Exception ex){
            ex.getMessage();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists "+TABLE_CONF);
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_USUARIO);
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_SESSION);
        onCreate(sqLiteDatabase);
    }
}
