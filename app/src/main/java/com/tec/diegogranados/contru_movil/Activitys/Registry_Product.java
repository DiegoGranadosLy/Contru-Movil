package com.tec.diegogranados.contru_movil.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tec.diegogranados.contru_movil.Beans.Categoria;
import com.tec.diegogranados.contru_movil.Beans.Persona;
import com.tec.diegogranados.contru_movil.Beans.Producto;
import com.tec.diegogranados.contru_movil.Beans.RequestNewClient;
import com.tec.diegogranados.contru_movil.Beans.Result;
import com.tec.diegogranados.contru_movil.Beans.Sucursal;
import com.tec.diegogranados.contru_movil.Beans.Table_Producto;
import com.tec.diegogranados.contru_movil.Beans.Usuario;
import com.tec.diegogranados.contru_movil.Post.Communicator_Database;
import com.tec.diegogranados.contru_movil.R;
import com.tec.diegogranados.contru_movil.SQL_Lite.DataBaseManager;

/**
 * Clase en la que se visualizan los
 * elementos o productos de la base de datos.
 */
public class Registry_Product extends AppCompatActivity {

    EditText EditText_Name_Product_Registry;
    EditText EditText_Description_Registry_Product;
    EditText EditText_Quantity_Registry_Product;
    EditText EditText_Exent_Registry_Product;
    TextView TextView_Provider_Registry_Product;
    TextView TextView_Category_Registry_Product;
    TextView TextView_Branch_Registry_Product;
    Button button_BProvider_Registry_Product;
    Button button_BCategory_Registry_Product;
    Button button_BBranch_Registry_Product;
    Button button_Registry_Product_Registrar;
    Intent siguiente;
    String action;
    boolean accion;
    int id;
    Sucursal[] sucursales;
    Categoria[] categorias;
    Persona[] proveedores;
    int id_sucursal;
    String id_proveedor;
    int id_categoria;


    /**
     * Metodo que inicializa todas las
     * instancias de la ventana.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry__product);

        EditText_Name_Product_Registry        = (EditText)findViewById(R.id.EditText_Name_Product_Registry);
        EditText_Description_Registry_Product = (EditText)findViewById(R.id.EditText_Description_Registry_Product);
        EditText_Quantity_Registry_Product    = (EditText)findViewById(R.id.EditText_Quantity_Registry_Product);
        EditText_Exent_Registry_Product       = (EditText)findViewById(R.id.EditText_Exent_Registry_Product);
        TextView_Provider_Registry_Product    = (TextView) findViewById(R.id.TextView_Provider_Registry_Product);
        TextView_Category_Registry_Product    = (TextView) findViewById(R.id.TextView_Category_Registry_Product);
        TextView_Branch_Registry_Product    = (TextView) findViewById(R.id.TextView_Branch_Registry_Product);
        button_BProvider_Registry_Product     = (Button)findViewById(R.id.button_BProvider_Registry_Product);
        button_BCategory_Registry_Product     = (Button)findViewById(R.id.button_BCategory_Registry_Product);
        button_BBranch_Registry_Product     = (Button)findViewById(R.id.button_BBranch_Registry_Product);
        button_Registry_Product_Registrar     = (Button)findViewById(R.id.button_Registry_Product_Registrar);


        action = getIntent().getExtras().getString("Action");
        if (action.equals("Update")){
            EditText_Name_Product_Registry        .setText(getIntent().getExtras().getString("nombre"));
            EditText_Description_Registry_Product .setText(getIntent().getExtras().getString("descripcion"));
            EditText_Quantity_Registry_Product    .setText(getIntent().getExtras().getString("cantidad"));
            EditText_Exent_Registry_Product       .setText(getIntent().getExtras().getString("precio"));
            TextView_Provider_Registry_Product    .setText(getIntent().getExtras().getString("proveedor"));
            TextView_Category_Registry_Product    .setText(getIntent().getExtras().getString("categoria"));
            TextView_Branch_Registry_Product      .setText(getIntent().getExtras().getString("sucursal"));
            id = Integer.parseInt(getIntent().getExtras().getString("id"));

        }
        CommunicatorGet get = new CommunicatorGet();
        get.execute(0,0,0);
    }

    /**
     * Metodo en el que se definen las acciones
     * para cada uno de los botones asociados.
     */
    private void accionBotones(){
        button_BProvider_Registry_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String items[] = new String[proveedores.length];
                for (int i = 0;i<items.length;i++)
                    items[i] = proveedores[i].nombre;

                AlertDialog.Builder Stages =new AlertDialog.Builder(Registry_Product.this);
                Stages.setItems(items,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView_Provider_Registry_Product.setText(items[which]);
                    }
                });
                Stages.show();
            }
        });

        button_BCategory_Registry_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String items[] = new String[categorias.length];
                for (int i = 0;i<items.length;i++)
                    items[i] = categorias[i].nombre;

                AlertDialog.Builder Stages =new AlertDialog.Builder(Registry_Product.this);
                Stages.setItems(items,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView_Category_Registry_Product.setText(items[which]);
                    }
                });
                Stages.show();
            }
        });

        button_BBranch_Registry_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String items[] = new String[sucursales.length];
                for (int i = 0;i<items.length;i++)
                    items[i] = sucursales[i].nombre;

                AlertDialog.Builder Stages =new AlertDialog.Builder(Registry_Product.this);
                Stages.setItems(items,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView_Branch_Registry_Product.setText(items[which]);
                    }
                });
                Stages.show();
            }
        });

        button_Registry_Product_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communicator comunicador = new Communicator();
                comunicador.execute(new String[0][0],new String[0][0],new String[0][0]);
            }
        });
    }

    /**
     * Metodo para obtener las sucursales
     * de la base de datos.
     */
    private void getSucursales() {
        boolean connection = Communicator_Database.isOnline(getApplicationContext());
        if (connection == true) {
            Communicator_Database com = new Communicator_Database();
            String message = com.peticion("//show//", "{\"type\":\"branches\"}");

            Gson gson = new Gson();
            sucursales = gson.fromJson(message, Sucursal[].class);
        }
        else {
            DataBaseManager DBM = new DataBaseManager(getApplicationContext());
            Cursor cursor= DBM.getSucursal();
            sucursales = new Sucursal[cursor.getCount()];
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                Sucursal cat = new Sucursal();
                cat.id       = cursor.getInt(0);
                cat.nombre   = cursor.getString(1);
                cat.ubicacion= cursor.getString(2);
                cursor.moveToNext();
                sucursales[i] = cat;
            }
        }
    }

    /**
     * Metodo para obtener las categorias
     * de la base de datos.
     */
    private void getCategorias() {
        boolean connection = Communicator_Database.isOnline(getApplicationContext());
        if (connection == true) {
            Communicator_Database com = new Communicator_Database();
            String message = com.peticion("//show//", "{\"type\":\"categories\"}");

            Gson gson = new Gson();
            categorias = gson.fromJson(message, Categoria[].class);
        }
        else {
            DataBaseManager DBM = new DataBaseManager(getApplicationContext());
            Cursor cursor= DBM.getSucursal();
            categorias = new Categoria[cursor.getCount()];
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                Categoria cat   = new Categoria();
                cat.id          = cursor.getInt(0);
                cat.nombre      = cursor.getString(1);
                cat.descripcion = cursor.getString(2);
                cursor.moveToNext();
                categorias[i] = cat;
            }
        }
    }

    /**
     * Metodo para obtener loos proveedores con ayuda
     * la conexion a la red.
     */
    private void getProveedores() {
        boolean connection = Communicator_Database.isOnline(getApplicationContext());
        if (connection == true) {
            Communicator_Database com = new Communicator_Database();
            String message = com.peticion("//show//", "{\"type\":\"providers\"}");

            Gson gson = new Gson();
            proveedores = gson.fromJson(message, Persona[].class);
        }
        else {
            Persona a = new Persona();
            a.id = "321654987";a.nombre = "Javier";a.apellido = "Sancho";
            a.residencia="San Jose"; a.f_nacimiento = "13/3/1994";a.telefono = 88447766;
            Persona b = new Persona();
            b.id = "123456789";b.nombre = "Ernesto";b.apellido = "Ulate";
            b.residencia="San Carlos"; b.f_nacimiento = "1/10/1995";b.telefono = 88563266;
            Persona c = new Persona();
            c.id = "304860692";c.nombre = "Diego";c.apellido = "Granados";
            c.residencia="Cartago"; c.f_nacimiento = "13/5/1995";c.telefono = 88447766;
            Persona d = new Persona();
            d.id = "304860695";d.nombre = "Fabian";d.apellido = "Astorga";
            d.residencia="Oreamuno"; d.f_nacimiento = "13/5/1996";d.telefono = 61447766;

            proveedores = new Persona[]{a,b,c,d};
        }
    }


    /**
     * Clase interna que ayudara a
     * manejar las tareas en segundo plano.
     */
    public class Communicator extends AsyncTask<String[][], String[][], String[][]> {
        public Communicator() {
        }

        @Override
        protected String[][] doInBackground(String[][]... strings) {
            boolean connection = Communicator_Database.isOnline(getApplicationContext());
            if (action.equals("Update")){
                //Agregar la accion de actualizacion directo a la base.
                if (connection == true){
                    if (accionActualizarEnBase()) //Se actualiza directo la base
                        accion = true;
                    else{//Se actualiza en telefono
                        if (accionActualizarBaseTelefono())
                            accion = true;
                        else//No se pudo actualizar.
                            accion = false;
                    }
                }
                else{//En caso de no haber conexion se agrega al telefono
                    if (accionActualizarBaseTelefono())
                        accion = true;
                    else//No se logra actualizar.
                        accion = false;
                }
            }
            else{
                //Agregar la accion de creacion de un cliente.
                if (connection == true){
                    if (accionAgregarEnBase())//Creacion directa a la base de datos
                        accion = true;
                    else{//En caso de tener que agregar en el telefono
                        if (accionAgregarBaseTelefono())
                            accion = true;
                        else//No se logro agregar a ninguna base.
                            accion = false;
                    }
                }
                else{//Se agrega a la base del telefono.
                    if (accionAgregarBaseTelefono())
                        accion = true;
                    else//No se logra agregar al usuario.
                        accion = false;
                }
            }
            return null;
        }


        /**
         * Metodo en primer plano que es ejecutado
         * luego del do in background.
         * @param result
         */
        @Override
        protected void onPostExecute(String[][] result) {
            if (accion == true){
                if (action.equals("Update"))
                    siguiente = new Intent(getApplicationContext(), Products.class);
                else
                    siguiente = new Intent(getApplicationContext(), Main_Page_App.class);
                startActivity(siguiente);
            }
            else{
                Toast toast = Toast.makeText(Registry_Product.this,
                        "We cannot create or update a product in this moment.", Toast.LENGTH_LONG);
                toast.show();
            }
        }

    }

    /**
     * Clase interna para obtener las listas internas
     * de la ventana.
     */
    public class CommunicatorGet extends AsyncTask<Integer,Integer,Integer> {
        public CommunicatorGet() {
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            getSucursales();
            getProveedores();
            getCategorias();
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            accionBotones();
        }
    }

    /**
     * Metodo para agregar un producto
     * a la base de datos web.
     * @return
     */
    private boolean accionAgregarEnBase(){
        Table_Producto producto = new Table_Producto();
        findData();
        producto.id          = id;
        producto.nombre      = String.valueOf(EditText_Name_Product_Registry.getText());
        producto.descripcion = String.valueOf(EditText_Description_Registry_Product.getText());
        producto.precio      = Integer.parseInt(String.valueOf(EditText_Exent_Registry_Product.getText()));
        producto.disponible  = Integer.parseInt(String.valueOf(EditText_Quantity_Registry_Product.getText()));
        producto.id_sucursal = id_sucursal;
        producto.id_proveedor= id_proveedor;
        producto.id_categoria= id_categoria;

        System.out.println("id_sucursal:"+id_sucursal);
        System.out.println("id_proveedor:"+id_proveedor);
        System.out.println("id_categoria:"+id_categoria);
        Communicator_Database com = new Communicator_Database();

        Gson gson = new Gson();
        String message = com.peticion("//control//", "{\"operation\":\"create\",\"type\":\"product\"" +
                ",\"jsonObject\":"+ gson.toJson(producto) +"}");

        Result Resultado = gson.fromJson(message, Result.class);
        return Resultado.success;
    }

    /**
     * Metodo para agregar un
     * producto a la base de dtos del telefono.
     * @return
     */
    private boolean accionAgregarBaseTelefono(){
        DataBaseManager m = new DataBaseManager(getApplicationContext());
//        m.getProveedores();
        return false;
    }

    /**
     * Metodo para actualizar un producto delntro
     * dentro de la base de datos del telefono.
     * @return
     */
    private boolean accionActualizarEnBase(){
        findData();
        Table_Producto producto = new Table_Producto();
        producto.id          = id;
        producto.nombre      = String.valueOf(EditText_Name_Product_Registry.getText());
        producto.descripcion = String.valueOf(EditText_Description_Registry_Product.getText());
        producto.precio      = Integer.parseInt(String.valueOf(EditText_Exent_Registry_Product.getText()));
        producto.disponible  = Integer.parseInt(String.valueOf(EditText_Quantity_Registry_Product.getText()));
        producto.id_sucursal = id_sucursal;
        producto.id_proveedor= id_proveedor;
        producto.id_categoria= id_categoria;

        System.out.println("id_sucursal:"+id_sucursal);
        System.out.println("id_proveedor:"+id_proveedor);
        System.out.println("id_categoria:"+id_categoria);
        Communicator_Database com = new Communicator_Database();

        Gson gson = new Gson();
        String message = com.peticion("//control//", "{\"operation\":\"edit\",\"type\":\"product\"" +
                ",\"jsonObject\":"+ gson.toJson(producto) +"}");

        Result Resultado = gson.fromJson(message, Result.class);
        System.out.println("Actualizacion de producto:"+ Resultado.success);
        return Resultado.success;
    }

    private boolean accionActualizarBaseTelefono(){

        return false;
    }


    /**
     * Metodos auxiliares
     * para la recoleccion de datos.
     */
    private void findData(){
        for(int i =0;i<sucursales.length;i++){
            if(sucursales[i].nombre.equals(TextView_Branch_Registry_Product.getText())){
                id_sucursal = sucursales[i].id;
                break;
            }
        }

        for(int i =0;i<proveedores.length;i++){
            if((proveedores[i].nombre).equals(TextView_Provider_Registry_Product.getText().toString().split(" ")[0])){
                id_proveedor = proveedores[i].id;
                break;
            }
        }

        for(int i =0;i<categorias.length;i++){
            if(categorias[i].nombre.equals(TextView_Category_Registry_Product.getText())){
                id_categoria = categorias[i].id;
                break;
            }
        }
    }

}
