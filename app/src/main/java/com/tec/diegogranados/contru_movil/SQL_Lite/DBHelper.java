package com.tec.diegogranados.contru_movil.SQL_Lite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Diego Granados on 09/11/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME        = "Constru-Movil.sqlite";
    private static final int DB_SCHEME_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DataBaseManager.CT_PERSONA);
        db.execSQL(DataBaseManager.CT_PRODUCTO);
        db.execSQL(DataBaseManager.CT_PEDIDO);
        db.execSQL(DataBaseManager.CT_CATEGORIA);
        db.execSQL(DataBaseManager.CT_ORDEN);
        db.execSQL(DataBaseManager.CT_USUARIO);
        db.execSQL(DataBaseManager.CT_ROL);
        db.execSQL(DataBaseManager.CT_SUCURSAL);
        db.execSQL(DataBaseManager.CT_ACT_MATUTINA);
        db.execSQL(DataBaseManager.CT_OP_SIN_CONEXION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
