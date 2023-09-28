package com.example.veterinaria;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    final String VETERINARIA = "" +
            "CREATE TABLE 'clientes' (" +
            "'idcliente'       INTEGER NOT NULL," +
            "'apellidos'       TEXT NOT NULL," +
            "'nombres'         TEXT NOT NULL," +
            "'telefono'        TEXT NOT NULL," +
            "'email'           TEXT NOT NULL," +
            "'direccion'       TEXT NOT NULL ," +
            "'fechanacimiento' TEXT NOT NULL," +
            "PRIMARY KEY('idcliente' AUTOINCREMENT)" +
            ")";

    final String REGISTROS = "" +
            "CREATE TABLE 'mascotas' (" +
            "'idmascota'    INTEGER     NOT NULL," +
            "'tipo'         TEXT        NOT NULL," +
            "'raza'         TEXT        NOT NULL," +
            "'nombre'       TEXT        NOT NULL," +
            "'peso'         TEXT        NOT NULL," +
            "'color'        TEXT        NOT NULL," +
            "PRIMARY KEY ('idmascota' AUTOINCREMENT)" +
            ")";

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(VETERINARIA);
        sqLiteDatabase.execSQL(REGISTROS);
    }

        @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase,int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS clientes");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS mascotas");
        onCreate(sqLiteDatabase);
    }




}

