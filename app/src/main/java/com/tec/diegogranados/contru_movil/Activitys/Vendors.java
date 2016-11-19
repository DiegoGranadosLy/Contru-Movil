package com.tec.diegogranados.contru_movil.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tec.diegogranados.contru_movil.Beans.Persona;
import com.tec.diegogranados.contru_movil.ListView.Order_Adapter_List;
import com.tec.diegogranados.contru_movil.ListView.Order_Entry_List;
import com.tec.diegogranados.contru_movil.Post.Communicator_Database;
import com.tec.diegogranados.contru_movil.R;

import java.util.ArrayList;

/**
 * Clase que maneja la ventana
 * de categorias de la aplicacion.
 */
public class Vendors extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Intent siguiente;
    ListView listview;
    Communicator comunicador;
    Order_Adapter_List adapter;
    Persona[] vendedores;
    boolean accion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        accion =false;
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
                Toast.makeText(Vendors.this, query, Toast.LENGTH_SHORT).show();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Vendors.this.adapter.getFilter().filter(newText);
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
            siguiente = new Intent(Vendors.this, Clients.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_products) {
            siguiente = new Intent(Vendors.this, Products.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_orders) {
            siguiente = new Intent(Vendors.this, Orders.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_providers) {
            siguiente = new Intent(Vendors.this, Providers.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_categories) {
            siguiente = new Intent(Vendors.this, Categories.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_vendors) {
            siguiente = new Intent(Vendors.this, Vendors.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_mainpage) {
            siguiente = new Intent(Vendors.this, Main_Page_App.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_exit) {
            siguiente = new Intent(Vendors.this, MainActivity.class);
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
            listview = (ListView) findViewById(R.id.ListView_vendors);
            //Se llaman los metodos auxiliares del metodo.
            Set_Adapter(listview);
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
    private Persona[] getClientes(){

        boolean connection = Communicator_Database.isOnline(getApplicationContext());
        if (connection == true) {
            Communicator_Database com = new Communicator_Database();
            String message = com.peticion("//show//", "{\"type\":\"sellers\"}");

            Gson gson = new Gson();
            vendedores = gson.fromJson(message, Persona[].class);

        }
        else{
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
            vendedores = new Persona[]{a, b, c, d};
        }
        return vendedores;
    }

    /**
     * Metodo en el que se establece el adapter
     * y la lista de elementos que se añadiran
     * al listView
     * @param lista es el listView cargado desde el XML
     */
    private void Set_Adapter(ListView lista){

        vendedores = getClientes();

        ArrayList<Order_Entry_List> datos = new ArrayList<Order_Entry_List>();
        for (int i = 0; i < vendedores.length; i++) {
            datos.add(new Order_Entry_List(R.mipmap.ic_person_pin_black_24dp, vendedores[i].nombre, vendedores[i].residencia));
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

                CharSequence texto = "Name : " + vendedores[posicion].nombre + "\n"+
                        "Last Name : " + vendedores[posicion].apellido + "\n"+
                        "Card Number : "+ vendedores[posicion].id + "\n"+
                        "Place : " + vendedores[posicion].residencia + "\n"+
                        "Born date : " + vendedores[posicion].f_nacimiento + "\n"+
                        "Telephone : "+ vendedores[posicion].telefono;

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Vendors.this);
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
    }
}
