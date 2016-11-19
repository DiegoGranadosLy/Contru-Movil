package com.tec.diegogranados.contru_movil.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tec.diegogranados.contru_movil.Beans.Categoria;
import com.tec.diegogranados.contru_movil.Beans.Persona;
import com.tec.diegogranados.contru_movil.Beans.Producto;
import com.tec.diegogranados.contru_movil.Beans.Result;
import com.tec.diegogranados.contru_movil.ListView.Order_Adapter_List;
import com.tec.diegogranados.contru_movil.ListView.Order_Entry_List;
import com.tec.diegogranados.contru_movil.Post.Communicator_Database;
import com.tec.diegogranados.contru_movil.R;
import com.tec.diegogranados.contru_movil.SQL_Lite.DataBaseManager;

import java.util.ArrayList;

/**
 * Clase que maneja la ventana
 * de categorias de la aplicacion.
 */
public class Products extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent siguiente;
    ListView listview;
    Button button_Create;
    Communicator comunicador;
    Order_Adapter_List adapter;
    Producto[] productos;
    boolean accion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        accion = false;
        button_Create = (Button)findViewById(R.id.button_Create_Products);
        comunicador = new Communicator();
        comunicador.execute(new String[0][0],new String[0][0],new String[0][0]);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Metodo sobreescrito que crea los diferentes
     * menus que se muestran en la ventana de la aplicacion.
     * @param menu es el archivo xml que recibe para cargar
     * la vista de menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main__page__app, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getText(R.string.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Products.this, query, Toast.LENGTH_SHORT).show();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Products.this.adapter.getFilter().filter(newText);
//                Order.this.lista.getAdapter();
                return true;
            }
        });
        //Fin de la accion de busqueda
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo que define las acciones a tomar con cada uno de los
     * elementos del menu desplegable.Todos son redireccionamientos.
     * @param item es el item seleccionado del menu.
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_clients) {
            siguiente = new Intent(Products.this, Clients.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_products) {
            siguiente = new Intent(Products.this, Products.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_orders) {
            siguiente = new Intent(Products.this, Orders.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_providers) {
            siguiente = new Intent(Products.this, Providers.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_categories) {
            siguiente = new Intent(Products.this, Categories.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_vendors) {
            siguiente = new Intent(Products.this, Vendors.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_mainpage) {
            siguiente = new Intent(Products.this, Main_Page_App.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_exit) {
            siguiente = new Intent(Products.this, MainActivity.class);
            startActivity(siguiente);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Clase interna que seteara los valores
     * de los listViews e incorporara
     * gran parte de la logica de la ventana.
     */
    public class Communicator extends AsyncTask<String[][], String[][], String[][]> {
        public Communicator() {
        }

        /**
         * Metodo sobreescrito que se ejecuta
         * en segundo plano. En el se establecen
         * los adaptadores y se solicicita la
         * informacion para cargar en el Activity
         * @param strings
         * @return
         */
        @Override
        protected String[][] doInBackground(String[][]... strings) {

            listview = (ListView) findViewById(R.id.ListView_Products);
            //Se llaman los metodos auxiliares del metodo.
            Set_Adapter(listview);
            AccionButtonCrear();
            return new String[0][];
        }


        /**
         * Metodo que se ejecuta luego del
         * doInBackground. Define las
         * acciones al presionar un
         * elememto de la lista.
         * @param result
         */
        @Override
        protected void onPostExecute(String[][] result) {
            AccionItemLista(listview, result);
        }
    }

    /**
     * Metodo que realiza un query
     * para obtener las categorias
     * presentes de la app.
     * Puede ser con o sin red.
     */
    private Producto[] getProductosBase(){
        boolean connection = Communicator_Database.isOnline(getApplicationContext());
        if (connection == true) {
            Communicator_Database com = new Communicator_Database();
            String message = com.peticion("//show//", "{\"type\":\"products\"}");

            Gson gson = new Gson();
            productos = gson.fromJson(message, Producto[].class);
        }
        else {
            System.out.println("Estoy entrando por aca...!!");
            getProducts();
        }
        return productos;
    }

    /**
     * Metodo en el que se establece el adapter
     * y la lista de elementos que se añadiran
     * al listView
     * @param lista es el listView cargado desde el XML
     */
    private void Set_Adapter(ListView lista){

        getProductosBase();

        ArrayList<Order_Entry_List> datos = new ArrayList<Order_Entry_List>();
        for(int i = 0; i < productos.length; i++){
            if (productos[i].disponible>0){
                datos.add(new Order_Entry_List(R.mipmap.ic_check_black_24dp,productos[i].nombre,Integer.toString(productos[i].precio)));
            }
            else{
                datos.add(new Order_Entry_List(R.mipmap.ic_clear_black_24dp,productos[i].nombre,Integer.toString(productos[i].precio)));
            }
        }
        adapter = new Order_Adapter_List(getApplicationContext(), R.layout.order_list_view, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                TextView texto_superior_entrada = (TextView) view.findViewById(R.id.Obra_Pedidos);
                texto_superior_entrada.setText(((Order_Entry_List) entrada).get_Titulo());

                TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.Etapa_Pedidos);
                texto_inferior_entrada.setText(((Order_Entry_List) entrada).get_Descripcion());

                ImageView imagen_entrada = (ImageView) view.findViewById(R.id.Check);
                imagen_entrada.setImageResource(((Order_Entry_List) entrada).get_idImagen());
            }
        };
    }

    /**
     * Metodo en el que se definen las acciones
     * para cada uno de los elementos de la lista.
     * @param lista ListView de la app
     * @param result Valor agregado.
     */
    private void AccionItemLista(ListView lista, String[][] result){
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Order_Entry_List elegido = (Order_Entry_List) pariente.getItemAtPosition(posicion);

                //Seccion donde se muestra la informacion del usuario./////////////////////////////
                CharSequence texto = "Branch Office : " + productos[posicion].sucursal + "\n"+
                        "Provider : " + productos[posicion].proveedor + "\n"+
                        "Category : "+ productos[posicion].categoria + "\n"+
                        "Exempt : " + productos[posicion].precio + "\n"+
                        "Quantity : " + productos[posicion].disponible+ "\n"+
                        "Description : "+ productos[posicion].descripcion;

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Products.this);
                builder1.setTitle(elegido.get_Titulo());
                builder1.setMessage(texto);
                builder1.setCancelable(true);
                builder1.setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder1.show();
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, final long l) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Products.this);
                builder1.setTitle("Action");
                builder1.setMessage("¿What would you like to do?");
                builder1.setCancelable(true);
                builder1.setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder1.setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                siguiente = new Intent(Products.this,Registry_Product.class);
                                siguiente.putExtra("Action", "Update");
                                siguiente.putExtra("nombre",productos[(int) l].nombre);
                                siguiente.putExtra("descripcion",productos[(int) l].descripcion);
                                siguiente.putExtra("cantidad",Integer.toString(productos[(int) l].disponible));
                                siguiente.putExtra("precio",Integer.toString(productos[(int) l].precio));
                                siguiente.putExtra("proveedor",productos[(int) l].proveedor);
                                siguiente.putExtra("categoria",productos[(int) l].categoria);
                                siguiente.putExtra("sucursal",productos[(int) l].sucursal);
                                siguiente.putExtra("id",Integer.toString(productos[(int) l].id));
                                startActivity(siguiente);
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Agregar accion de borrar un cliente.
                                ComDelete del = new ComDelete(productos[(int) l]);
                                del.execute();
                                dialog.cancel();
                            }
                        });
                builder1.show();

                return false;
            }
        });
    }

    /**
     * Metodo para obtener los productos
     * sin necesidad de conectarse a la red.
     */
    private void getProducts(){
        DataBaseManager DBM = new DataBaseManager(getApplicationContext());
        Cursor cursor= DBM.getProducto();
        System.out.println("Cantidad de productos: "+cursor.getCount());
        productos = new Producto[cursor.getCount()];
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++){
            Producto cat = new Producto();
            cat.id = cursor.getInt(0);
            cat.nombre = cursor.getString(1);
            cat.descripcion = cursor.getString(2);
            cat.disponible = cursor.getInt(3);
            cat.precio = cursor.getInt(4);
            cat.id_sucursal = cursor.getInt(5);
            cat.id_proveedor = cursor.getString(6);
            cat.id_categoria = cursor.getInt(7);
            cursor.moveToNext();
            productos[i] = cat;
        }
    }

    /**
     * Metodo que define la accion
     * de crear un nuevo producto.
     */
    private void AccionButtonCrear(){
        button_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente = new Intent(Products.this, Registry_Product.class);
                siguiente.putExtra("Action","Add");
                startActivity(siguiente);
            }
        });
    }

    /**
     * Clase que incorpora una ejecucion
     * en paralelo a la principal
     * para poder eliminar una categoria.
     */
    public class ComDelete extends AsyncTask<String[][], String[][], String[][]> {
        Producto producto;
        public ComDelete(Producto pProducto) {
            producto = pProducto;
        }
        /**
         * Logica para borrar
         * con red o sin ella.
         * @param strings
         * @return
         */
        @Override
        protected String[][] doInBackground(String[][]... strings) {
            if (Communicator_Database.isOnline(getApplicationContext())){
                if (accionDeleteEnBase(producto))
                    accion=true;
                else{
                    if (accionDeleteEnTelefono(producto))
                        accion=true;
                    else
                        accion=false;
                }
            }
            else{
                if (accionDeleteEnTelefono(producto))
                    accion=true;
                else
                    accion=false;
            }
            return new String[0][0];
        }
        /**
         * Acorde a la decision tomada en el background
         * mostrara un toast u otro.
         * @param result
         */
        @Override
        protected void onPostExecute(String[][] result) {
            if (accion == true){
                siguiente = new Intent(getApplicationContext(),Products.class);
                startActivity(siguiente);
            }
            else{
                Toast toast = Toast.makeText(Products.this,
                        "We cannot delete a product in this moment.", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
    /**
     * Metodo que borra un elemento de la base de datos del telefono.
     * @param producto wrapper a eliminar.
     * @return
     */
    //Metodo que borra un elemento de la base de datos.
    private boolean accionDeleteEnBase(Producto producto){

        Communicator_Database com = new Communicator_Database();

        Gson gson = new Gson();
        String message = com.peticion("//control//", "{\"operation\":\"delete\",\"type\":\"product\"" +
                ",\"jsonObject\":"+ gson.toJson(producto) +"}");

        Result Resultado = gson.fromJson(message, Result.class);
        return Resultado.success;
    }
    /**
     * Metodo que elimina un elemento de la
     * base de datos real.
     * @param producto Wrapper del elemento
     *                   a eliminar.
     * @return
     */
    //Metodo que borra un elemento de la base de datos del telefono.
    private boolean accionDeleteEnTelefono(Producto producto){
        DataBaseManager DBM = new DataBaseManager(getApplicationContext());
        int exito = DBM.deleteProducto(producto.id);
        if (exito==-1)
            return false;
        else{
            return true;
        }
    }
}
