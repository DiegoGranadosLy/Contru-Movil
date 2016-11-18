package com.tec.diegogranados.contru_movil.SQL_Lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import com.tec.diegogranados.contru_movil.Beans.Categoria;
import com.tec.diegogranados.contru_movil.Beans.Orden;
import com.tec.diegogranados.contru_movil.Beans.Pedido;
import com.tec.diegogranados.contru_movil.Beans.Persona;
import com.tec.diegogranados.contru_movil.Beans.Producto;
import com.tec.diegogranados.contru_movil.Beans.Rol;
import com.tec.diegogranados.contru_movil.Beans.Sucursal;
import com.tec.diegogranados.contru_movil.Beans.Usuario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Diego Granados on 09/11/2016.
 */

public class DataBaseManager {

    public static final String TABLE_PERSONA         = "persona";
    public static final String TABLE_PRODUCTO        = "producto";
    public static final String TABLE_PEDIDO          = "pedido";
    public static final String TABLE_CATEGORIA       = "categoria";
    public static final String TABLE_ORDEN           = "orden";
    public static final String TABLE_USUARIO         = "usuario";
    public static final String TABLE_ROL             = "rol";
    public static final String TABLE_SUCURSAL        = "sucursal";
    public static final String TABLE_ACT_MATUTINA    = "actualizacion_matutina";
    public static final String TABLE_OP_SIN_CONEXION = "op_sin_conexion";

    //Inicio de la declaracion de atributos de las tablas
    public static final String TPERSONA_ID            = "id";
    public static final String TPERSONA_NOMBRE        = "nombre";
    public static final String TPERSONA_APELLIDO      = "apellido";
    public static final String TPERSONA_RESIDENCIA    = "residencia";
    public static final String TPERSONA_F_NACIMIENTO  = "f_nacimiento";
    public static final String TPERSONA_TELEFONO      = "telefono";

    public static final String TPRODUCTO_ID           = "id";
    public static final String TPRODUCTO_NOMBRE       = "nombre";
    public static final String TPRODUCTO_DESCRIPCION  = "descripcion";
    public static final String TPRODUCTO_DISPONIBLE   = "disponible";
    public static final String TPRODUCTO_PRECIO       = "precio";
    public static final String TPRODUCTO_ID_SUCURSAL  = "id_sucursal";
    public static final String TPRODUCTO_ID_PROVEEDOR = "id_proveedor";
    public static final String TPRODUCTO_ID_CATEGORIA = "id_categoria";

    public static final String T_PEDIDO_ID            = "id";
    public static final String T_PEDIDO_CREACION      = "creacion";
    public static final String T_PEDIDO_ID_CLIENTE    = "id_cliente";
    public static final String T_PEDIDO_PRECIO        = "precio";

    public static final String TCATEGORIA_ID          = "id";
    public static final String TCATEGORIA_NOMBRE      = "nombre";
    public static final String TCATEGORIA_DESCRIPCION = "descripcion";

    public static final String TORDEN_ID             = "id";
    public static final String TORDEN_ID_PRODUCTO    = "id_producto";
    public static final String TORDEN_ID_PEDIDO      = "id_pedido";
    public static final String TORDEN_CANTIDAD       = "cantidad";

    public static final String TUSUARIO_USERNAME    = "username";
    public static final String TUSUARIO_PASSWORD    = "password";
    public static final String TUSUARIO_ID_PERSONA  = "id_persona";
    public static final String TUSUARIO_ID_ROL      = "id_rol";

    public static final String TROL_ID              = "id";
    public static final String TROL_TIPO            = "tipo";

    public static final String TSUCURSAL_ID         = "id";
    public static final String TSUCURSAL_NOMBRE     = "nombre";
    public static final String TSUCURSAL_UBICACION  = "sucursal";

    public static final String TACT_MATUTINA_DIA    = "dia";
    public static final String TACT_MATUTINA_CHECKED= "checked";

    public static final String TOP_ID               = "id";
    public static final String TOP_OBJETO           = "objeto";

    //Fin  de la declaracion de atributos de las tablas


    //Inicio de las creaciones de tablas de SQL_Lite

    public static final String CT_PERSONA   = "CREATE TABLE " + TABLE_PERSONA+ " ("
            + TPERSONA_ID           +" TEXT PRIMARY KEY NOT NULL,"
            + TPERSONA_NOMBRE       +" TEXT NOT NULL,"
            +TPERSONA_APELLIDO      +" TEXT NOT NULL,"
            +TPERSONA_RESIDENCIA    +" TEXT NOT NULL,"
            +TPERSONA_F_NACIMIENTO  +" TEXT NOT NULL,"
            +TPERSONA_TELEFONO      +" INT  NOT NULL);";

    public static final String CT_PRODUCTO   = "CREATE TABLE " + TABLE_PRODUCTO+ " ("
            + TPRODUCTO_ID          +" INT PRIMARY KEY,"
            + TPRODUCTO_NOMBRE      +" TEXT NOT NULL,"
            +TPRODUCTO_DESCRIPCION  +" TEXT,"
            +TPRODUCTO_DISPONIBLE   +" INT,"
            +TPRODUCTO_PRECIO       +" INT NOT NULL,"
            +TPRODUCTO_ID_SUCURSAL  +" INT NOT NULL,"
            +TPRODUCTO_ID_PROVEEDOR +" TEXT NOT NULL,"
            +TPRODUCTO_ID_CATEGORIA +" INT NOT NULL);";

    public static final String CT_PEDIDO   = "CREATE TABLE " + TABLE_PEDIDO+ " ("
            + T_PEDIDO_ID         +" INT PRIMARY KEY,"
            + T_PEDIDO_CREACION   +" DATETIME,"
            +T_PEDIDO_ID_CLIENTE  +" TEXT NOT NULL,"
            +T_PEDIDO_PRECIO      +" INT NOT NULL);";

    public static final String CT_CATEGORIA   = "CREATE TABLE " + TABLE_CATEGORIA+ " ("
            + TCATEGORIA_ID         +" INT PRIMARY KEY,"
            + TCATEGORIA_NOMBRE     +" TEXT UNIQUE NOT NULL,"
            +TCATEGORIA_DESCRIPCION +" TEXT);";

    public static final String CT_ORDEN   = "CREATE TABLE " + TABLE_ORDEN+ " ("
            + TORDEN_ID          +" INT PRIMARY KEY,"
            + TORDEN_ID_PRODUCTO +" INT NOT NULL,"
            +TORDEN_ID_PEDIDO    +" INT NOT NULL,"
            +TORDEN_CANTIDAD     +" INT NOT NULL);";

    public static final String CT_USUARIO   = "CREATE TABLE " + TABLE_USUARIO+ " ("
            + TUSUARIO_USERNAME  +" TEXT PRIMARY KEY,"
            + TUSUARIO_PASSWORD  +" TEXT NOT NULL,"
            +TUSUARIO_ID_PERSONA +" TEXT NOT NULL,"
            +TUSUARIO_ID_ROL     +" INT NOT NULL);";

    public static final String CT_ROL   = "CREATE TABLE " + TABLE_ROL+ " ("
            + TROL_ID    +" INT PRIMARY KEY,"
            + TROL_TIPO  +" TEXT NOT NULL);";

    public static final String CT_SUCURSAL   = "CREATE TABLE " + TABLE_SUCURSAL+ " ("
            + TSUCURSAL_ID       +" INT PRIMARY KEY,"
            + TSUCURSAL_NOMBRE   +" TEXT NOT NULL,"
            +TSUCURSAL_UBICACION +" TEXT NOT NULL);";

    public static final String CT_ACT_MATUTINA   = "CREATE TABLE " + TABLE_ACT_MATUTINA+ " ("
            + TACT_MATUTINA_DIA      +" TEXT PRIMARY KEY,"
            + TACT_MATUTINA_CHECKED  +" INT NOT NULL);";

    public static final String CT_OP_SIN_CONEXION   = "CREATE TABLE " + TABLE_OP_SIN_CONEXION+ " ("
            + TOP_ID       +" INT PRIMARY KEY,"
            +TOP_OBJETO    +" TEXT NOT NULL);";

    //Inicio de strings de Alter Table
//    public static final String AT_PRODUCT_ADD_1  = TABLE_PRODUCTO +" ADD"
//            +" FOREIGN KEY" + "("+ TPRODUCTO_ID_PROVEEDOR +") "
//            +" REFERENCES " + TABLE_PERSONA + "("+ TPERSONA_ID +")";
//
//    public static final String AT_PRODUCT_ADD_2  = TABLE_PRODUCTO +" ADD"
//            +" FOREIGN KEY" + "("+ TPRODUCTO_ID_CATEGORIA +") "
//            +" REFERENCES " + TABLE_CATEGORIA + "("+ TCATEGORIA_ID +")";
//
//    public static final String AT_PRODUCT_ADD_3  = TABLE_PRODUCTO +" ADD"
//            +" FOREIGN KEY" + "("+ TPRODUCTO_ID_SUCURSAL +") "
//            +" REFERENCES " + TABLE_SUCURSAL + "("+ TSUCURSAL_ID +")";
//
//    public static final String AT_PEDIDO_ADD_1  = TABLE_PEDIDO +" ADD"
//            +" FOREIGN KEY" + "("+ T_PEDIDO_ID_CLIENTE +") "
//            +" REFERENCES " + TABLE_PERSONA + "("+ TPERSONA_ID +")";
//
//    public static final String AT_PEDIDO_ADD_2  = TABLE_PEDIDO +" ADD"
//            +" FOREIGN KEY" + "("+ TPRODUCTO_ID_SUCURSAL +") "
//            +" REFERENCES " + TABLE_SUCURSAL + "("+ TSUCURSAL_ID +")";
//
//    public static final String AT_ORDEN_ADD_1  = TABLE_ORDEN +" ADD"
//            +" FOREIGN KEY" + "("+ TORDEN_ID_PEDIDO +") "
//            +" REFERENCES " + TABLE_PEDIDO + "("+ T_PEDIDO_ID +")";
//
//    public static final String AT_ORDEN_ADD_2  = TABLE_ORDEN +" ADD"
//            +" FOREIGN KEY" + "("+ TORDEN_ID_PRODUCTO +") "
//            +" REFERENCES " + TABLE_PRODUCTO + "("+ TPRODUCTO_ID +")";
//
//    public static final String AT_USUARIO_ADD_1  = TABLE_USUARIO +" ADD"
//            +" FOREIGN KEY" + "("+ TUSUARIO_ID_PERSONA +") "
//            +" REFERENCES " + TABLE_PERSONA + "("+ TPERSONA_ID +")";
//
//    public static final String AT_USUARIO_ADD_2  = TABLE_USUARIO +" ADD"
//            +" FOREIGN KEY" + "("+ TUSUARIO_ID_ROL +") "
//            +" REFERENCES " + TABLE_ROL + "("+ TROL_ID +")";

    //Fin de strings de Alter Table


    DBHelper helper;
    public SQLiteDatabase db;
    public DataBaseManager(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insertarPersona(Persona persona){
        ContentValues valores = new ContentValues();
        valores.put(TPERSONA_ID,persona.id);
        valores.put(TPERSONA_NOMBRE,persona.nombre);
        valores.put(TPERSONA_APELLIDO,persona.apellido);
        valores.put(TPERSONA_RESIDENCIA,persona.residencia);
        valores.put(TPERSONA_F_NACIMIENTO,persona.f_nacimiento);
        valores.put(TPERSONA_TELEFONO,persona.telefono);

        return db.insert(TABLE_PERSONA,null,valores);
    }

    public long insertarProducto(Producto producto){
        ContentValues valores = new ContentValues();

        valores.put(TPRODUCTO_ID,producto.id);
        valores.put(TPRODUCTO_NOMBRE,producto.nombre);
        valores.put(TPRODUCTO_DESCRIPCION,producto.descripcion);
        valores.put(TPRODUCTO_DISPONIBLE,producto.disponible);
        valores.put(TPRODUCTO_PRECIO,producto.precio);
        valores.put(TPRODUCTO_ID_SUCURSAL,producto.sucursal);
        valores.put(TPRODUCTO_ID_PROVEEDOR,producto.proveedor);
        valores.put(TPRODUCTO_ID_CATEGORIA,producto.categoria);

        return db.insert(TABLE_PRODUCTO,null,valores);
    }

    public long insertarPedido(Pedido pedido){
        ContentValues valores = new ContentValues();

        valores.put(T_PEDIDO_ID,pedido.id);
        valores.put(T_PEDIDO_CREACION,pedido.creacion);
        valores.put(T_PEDIDO_ID_CLIENTE,pedido.id_cliente);
        valores.put(T_PEDIDO_PRECIO,pedido.precio);

        return db.insert(TABLE_PEDIDO,null,valores);
    }

    public long insertarCategoria(Categoria categoria){
        ContentValues valores = new ContentValues();

        valores.put(TCATEGORIA_ID,categoria.id);
        valores.put(TCATEGORIA_NOMBRE,categoria.nombre);
        valores.put(TCATEGORIA_DESCRIPCION,categoria.descripcion);

        return db.insert(TABLE_CATEGORIA,null,valores);
    }

    public long insertarOrden(Orden orden){
        ContentValues valores = new ContentValues();

        valores.put(TORDEN_ID,orden.id);
        valores.put(TORDEN_ID_PRODUCTO,orden.id_string);
        valores.put(TORDEN_ID_PEDIDO,orden.id_pedido);
        valores.put(TORDEN_CANTIDAD,orden.cantidad);

        return db.insert(TABLE_ORDEN,null,valores);
    }

    public long insertarUsuario(Usuario usuario){
        ContentValues valores = new ContentValues();

        valores.put(TUSUARIO_USERNAME,usuario.username);
        valores.put(TUSUARIO_PASSWORD,usuario.password);
        valores.put(TUSUARIO_ID_PERSONA,usuario.id_persona);
        valores.put(TUSUARIO_ID_ROL,usuario.id_rol);

        return db.insert(TABLE_USUARIO,null,valores);
    }

    public long insertarRol(Rol rol){
        ContentValues valores = new ContentValues();

        valores.put(TROL_ID,rol.id);
        valores.put(TROL_TIPO,rol.tipo);

        return db.insert(TABLE_ROL,null,valores);
    }

    public long insertarSucursal(Sucursal sucursal){
        ContentValues valores = new ContentValues();

        valores.put(TSUCURSAL_ID,sucursal.id);
        valores.put(TSUCURSAL_NOMBRE,sucursal.nombre);
        valores.put(TSUCURSAL_UBICACION,sucursal.ubicacion);
        return db.insert(TABLE_SUCURSAL,null,valores);
    }

    public long insertarOP(String object, int id){
        ContentValues valores = new ContentValues();

        valores.put(TOP_ID,id);
        valores.put(TOP_OBJETO,object);

        return db.insert(TABLE_ROL,null,valores);
    }

    public long insertarActMat(int checked){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());
        ContentValues valores = new ContentValues();

        valores.put(TACT_MATUTINA_DIA,strDate);
        valores.put(TACT_MATUTINA_CHECKED,checked);
        return db.insert(TABLE_ACT_MATUTINA,null,valores);
    }

    public int deleteInTables(String tablename,int id, String column){
        return db.delete(tablename,column+"=?",new String[]{Integer.toString(id)});
    }

    public int deleteUsuario(String username){
        return db.delete(TABLE_USUARIO,TUSUARIO_USERNAME+"=?",new String[]{username});
    }

    public long updatePersona(Persona persona){
        ContentValues valores = new ContentValues();
        valores.put(TPERSONA_ID,persona.id);
        valores.put(TPERSONA_NOMBRE,persona.nombre);
        valores.put(TPERSONA_APELLIDO,persona.apellido);
        valores.put(TPERSONA_RESIDENCIA,persona.residencia);
        valores.put(TPERSONA_F_NACIMIENTO,persona.f_nacimiento);
        valores.put(TPERSONA_TELEFONO,persona.telefono);

        return db.update(TABLE_PERSONA,valores,TPERSONA_ID+"=?",new String[]{persona.id});
    }

    public long updateProducto(Producto producto){
        ContentValues valores = new ContentValues();

        valores.put(TPRODUCTO_ID,producto.id);
        valores.put(TPRODUCTO_NOMBRE,producto.nombre);
        valores.put(TPRODUCTO_DESCRIPCION,producto.descripcion);
        valores.put(TPRODUCTO_DISPONIBLE,producto.disponible);
        valores.put(TPRODUCTO_PRECIO,producto.precio);
        valores.put(TPRODUCTO_ID_SUCURSAL,producto.sucursal);
        valores.put(TPRODUCTO_ID_PROVEEDOR,producto.proveedor);
        valores.put(TPRODUCTO_ID_CATEGORIA,producto.categoria);

        return db.update(TABLE_PRODUCTO,valores,TPRODUCTO_ID+"=?",new String[]{String.valueOf(producto.id)});
    }

    public long updatePedido(Pedido pedido){
        ContentValues valores = new ContentValues();

        valores.put(T_PEDIDO_ID,pedido.id);
        valores.put(T_PEDIDO_CREACION,pedido.creacion);
        valores.put(T_PEDIDO_ID_CLIENTE,pedido.id_cliente);
        valores.put(T_PEDIDO_PRECIO,pedido.precio);

        return db.update(TABLE_PEDIDO,valores,T_PEDIDO_ID+"=?",new String[]{String.valueOf(pedido.id)});
    }

    public long updateCategoria(Categoria categoria){
        ContentValues valores = new ContentValues();

        valores.put(TCATEGORIA_ID,categoria.id);
        valores.put(TCATEGORIA_NOMBRE,categoria.nombre);
        valores.put(TCATEGORIA_DESCRIPCION,categoria.descripcion);

        return db.update(TABLE_CATEGORIA,valores,TCATEGORIA_ID+"=?",new String[]{String.valueOf(categoria.id)});
    }

    public long updateOrden(Orden orden){
        ContentValues valores = new ContentValues();

        valores.put(TORDEN_ID,orden.id);
        valores.put(TORDEN_ID_PRODUCTO,orden.id_string);
        valores.put(TORDEN_ID_PEDIDO,orden.id_pedido);
        valores.put(TORDEN_CANTIDAD,orden.cantidad);

        return db.update(TABLE_ORDEN,valores,TORDEN_ID+"=?",new String[]{String.valueOf(orden.id)});
    }

    public long updateUsuario(Usuario usuario){
        ContentValues valores = new ContentValues();

        valores.put(TUSUARIO_USERNAME,usuario.username);
        valores.put(TUSUARIO_PASSWORD,usuario.password);
        valores.put(TUSUARIO_ID_PERSONA,usuario.id_persona);
        valores.put(TUSUARIO_ID_ROL,usuario.id_rol);

        return db.update(TABLE_USUARIO,valores,TUSUARIO_ID_PERSONA+"=?",new String[]{usuario.id_persona});
    }

    public long updateRol(Rol rol){
        ContentValues valores = new ContentValues();

        valores.put(TROL_ID,rol.id);
        valores.put(TROL_TIPO,rol.tipo);

        return db.update(TABLE_ROL,valores,TROL_ID+"=?",new String[]{String.valueOf(rol.id)});
    }

    public long updateSucursal(Sucursal sucursal){
        ContentValues valores = new ContentValues();

        valores.put(TSUCURSAL_ID,sucursal.id);
        valores.put(TSUCURSAL_NOMBRE,sucursal.nombre);
        valores.put(TSUCURSAL_UBICACION,sucursal.ubicacion);
        return db.update(TABLE_SUCURSAL,valores,TSUCURSAL_ID+"=?",new String[]{String.valueOf(sucursal.id)});
    }

    public Cursor getPersona(){
        String[] columns = {};
        return db.query(TABLE_PERSONA,columns,null,null,null,null,null);
    }

    public Cursor getProducto(){
        String[] columns = {};
        return db.query(TABLE_PRODUCTO,columns,null,null,null,null,null);
    }

    public Cursor getPedido(){
        String[] columns = {};
        return db.query(TABLE_PEDIDO,columns,null,null,null,null,null);
    }

    public Cursor getCategoria(){
        String[] columns = {};
        return db.query(TABLE_CATEGORIA,columns,null,null,null,null,null);
    }

    public Cursor getOrden(){
        String[] columns = {};
        return db.query(TABLE_ORDEN,columns,null,null,null,null,null);
    }

    public Cursor getUsuario(){
        String[] columns = {};
        return db.query(TABLE_USUARIO,columns,null,null,null,null,null);
    }

    public Cursor getRol(){
        String[] columns = {};
        return db.query(TABLE_ROL,columns,null,null,null,null,null);
    }

    public Cursor getOP(){
        String[] columns = {};
        return db.query(TABLE_OP_SIN_CONEXION,columns,null,null,null,null,null);
    }

    public Cursor getActMat(){
        String[] columns = {};
        return db.query(TABLE_ACT_MATUTINA,columns,null,null,null,null,null);
    }

    public Cursor getSucursal(){
        String[] columns = {};
        return db.query(TABLE_SUCURSAL,columns,null,null,null,null,null);
    }

    public void show(){
        System.out.println("Ruta del archivo SQLITE: "+db.getPath());

        String[] columns;
        Cursor cursor1 = getPersona();
        System.out.println("Registro en la tabla Persona:");
        columns = cursor1.getColumnNames();
        for(int i = 0;i<columns.length;i++)
            System.out.println(columns[i]+" ");

        System.out.println("");

        Cursor cursor2 = getProducto();
        System.out.println("Registro en la tabla Producto:");
        columns = cursor2.getColumnNames();
        for(int i = 0;i<columns.length;i++)
            System.out.println(columns[i]+" ");

        System.out.println("");

        Cursor cursor3 = getPedido();
        System.out.println("Registro en la tabla Pedido:");
        columns = cursor3.getColumnNames();
        for(int i = 0;i<columns.length;i++)
            System.out.println(columns[i]+" ");

        System.out.println("");

        Cursor cursor4 = getCategoria();
        System.out.println("Registro en la tabla Categoria:");
        columns = cursor4.getColumnNames();
        for(int i = 0;i<columns.length;i++)
            System.out.println(columns[i]+" ");

        System.out.println("");

        Cursor cursor5 = getOrden();
        System.out.println("Registro en la tabla Orden:");
        columns = cursor5.getColumnNames();
        for(int i = 0;i<columns.length;i++)
            System.out.println(columns[i]+" ");

        System.out.println("");

        Cursor cursor6 = getUsuario();
        System.out.println("Registro en la tabla usuarios:");
        columns = cursor6.getColumnNames();
        for(int i = 0;i<columns.length;i++)
            System.out.println(columns[i]+" ");

        System.out.println("");

        Cursor cursor7 = getRol();
        System.out.println("Registro en la tabla Rol:");
        columns = cursor7.getColumnNames();
        for(int i = 0;i<columns.length;i++)
            System.out.println(columns[i]+" ");

        System.out.println("");

        Cursor cursor8 = getSucursal();
        System.out.println("Registro en la tabla sucursal:");
        columns = cursor8.getColumnNames();
        for(int i = 0;i<columns.length;i++)
            System.out.println(columns[i]+" ");

        System.out.println("");

        Cursor cursor9 = getActMat();
        System.out.println("Registro en la tabla Actualizacion matutina:");
        columns = cursor9.getColumnNames();
        for(int i = 0;i<columns.length;i++)
            System.out.println(columns[i]+" ");

        System.out.println("");

        Cursor cursor10 = getOP();
        System.out.println("Registro en la tabla Operacion sin conexion:");
        columns = cursor10.getColumnNames();
        for(int i = 0;i<columns.length;i++)
            System.out.println(columns[i]+" ");
    }
}


/*
bd.insert (Tabla, nullColumnHack, ContentValues)
bd.delete (Tabla, clausula where, argumentos where)
bd.update (Tabla, Content values, clausula where, Argumentos where)
bd.query (String Table, String[] columns, String Selection, String[] selection args, String groupby,String having, string orderby)
 */
