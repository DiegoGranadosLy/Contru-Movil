package com.tec.diegogranados.contru_movil.SQL_Lite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
//        db.execSQL(DataBaseManager.CT_PRODUCTO);
//        db.execSQL(DataBaseManager.CT_PEDIDO);
//        db.execSQL(DataBaseManager.CT_CATEGORIA);
//        db.execSQL(DataBaseManager.CT_ORDEN);
//        db.execSQL(DataBaseManager.CT_USUARIO);
//        db.execSQL(DataBaseManager.CT_ROL);
//        db.execSQL(DataBaseManager.CT_SUCURSAL);
//
//        db.execSQL(DataBaseManager.AT_PRODUCT_ADD_1);
//        db.execSQL(DataBaseManager.AT_PRODUCT_ADD_2);
//        db.execSQL(DataBaseManager.AT_PRODUCT_ADD_3);
//        db.execSQL(DataBaseManager.AT_PEDIDO_ADD_1);
//        db.execSQL(DataBaseManager.AT_PEDIDO_ADD_2);
//        db.execSQL(DataBaseManager.AT_ORDEN_ADD_1);
//        db.execSQL(DataBaseManager.AT_ORDEN_ADD_2);
//        db.execSQL(DataBaseManager.AT_USUARIO_ADD_1);
//        db.execSQL(DataBaseManager.AT_USUARIO_ADD_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
