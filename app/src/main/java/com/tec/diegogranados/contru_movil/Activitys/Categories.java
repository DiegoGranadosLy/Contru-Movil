package com.tec.diegogranados.contru_movil.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tec.diegogranados.contru_movil.Beans.Categoria;
import com.tec.diegogranados.contru_movil.Beans.Persona;
import com.tec.diegogranados.contru_movil.Beans.Result;
import com.tec.diegogranados.contru_movil.ListView.Order_Adapter_List;
import com.tec.diegogranados.contru_movil.ListView.Order_Entry_List;
import com.tec.diegogranados.contru_movil.Post.Communicator_Database;
import com.tec.diegogranados.contru_movil.R;
import com.tec.diegogranados.contru_movil.Threads.Thread_Sync;

import java.util.ArrayList;

public class Categories extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent siguiente;
    ListView listview;
    Communicator comunicador;
    Order_Adapter_List adapter;
    Button button_Create_Categories;
    Categoria[] categorias;
    boolean accion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        button_Create_Categories = (Button)findViewById(R.id.button_Create_Categories);
        button_Create_Categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente = new Intent(Categories.this, Registry_Category.class);
                siguiente.putExtra("Action","Add");
                startActivity(siguiente);
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main__page__app, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getText(R.string.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Categories.this, query, Toast.LENGTH_SHORT).show();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Categories.this.adapter.getFilter().filter(newText);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_clients) {
            siguiente = new Intent(Categories.this, Clients.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_products) {
            siguiente = new Intent(Categories.this, Products.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_orders) {
            siguiente = new Intent(Categories.this, Orders.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_providers) {
            siguiente = new Intent(Categories.this, Providers.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_categories) {
            siguiente = new Intent(Categories.this, Categories.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_vendors) {
            siguiente = new Intent(Categories.this, Vendors.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_mainpage) {
            siguiente = new Intent(Categories.this, Main_Page_App.class);
            startActivity(siguiente);
        } else if (id == R.id.nav_exit) {
            siguiente = new Intent(Categories.this, MainActivity.class);
            startActivity(siguiente);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class Communicator extends AsyncTask<String[][], String[][], String[][]> {
        public Communicator() {
        }

        @Override
        protected String[][] doInBackground(String[][]... strings) {
            listview = (ListView) findViewById(R.id.ListView_Categories);
            //Se llaman los metodos auxiliares del metodo.
            Set_Adapter(listview);
            return new String[0][];
        }


        @Override
        protected void onPostExecute(String[][] result) {
            AccionItemLista(listview, result);
        }
    }


    private void getCategorias(){
        boolean connection = Communicator_Database.isOnline(getApplicationContext());
        if (connection == true) {
            Communicator_Database com = new Communicator_Database();
            String message = com.peticion("//show//", "{\"type\":\"categories\"}");

            Gson gson = new Gson();
            categorias = gson.fromJson(message, Categoria[].class);
        }
        else {
            Categoria a = new Categoria();
            a.id =0;a.nombre="Caca";a.descripcion="Huele mal";
            Categoria b = new Categoria();
            b.id =0;b.nombre="Madera";b.descripcion="Color cafe";
            Categoria c = new Categoria();
            c.id =0;c.nombre="Metal";c.descripcion="Brillante";
            categorias = new Categoria[]{a,b,c};
        }
    }

    private void Set_Adapter(ListView lista){

        getCategorias();

        ArrayList<Order_Entry_List> datos = new ArrayList<Order_Entry_List>();
        for (int i = 0; i < categorias.length; i++) {
            datos.add(new Order_Entry_List(R.mipmap.ic_person_pin_black_24dp, categorias[i].nombre, categorias[i].descripcion));
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

        lista.setAdapter(adapter);
    }

    private void AccionItemLista(ListView lista, String[][] result){
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Order_Entry_List elegido = (Order_Entry_List) pariente.getItemAtPosition(posicion);

                //Seccion donde se muestra la informacion del usuario./////////////////////////////
                CharSequence texto = "Name : " + categorias[posicion].nombre + "\n"+
                        "Description : " + categorias[posicion].descripcion ;

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Categories.this);
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

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Categories.this);
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
                                siguiente = new Intent(Categories.this, Registry_Category.class);
                                siguiente.putExtra("Action","Update");
                                siguiente.putExtra("id",String.valueOf(categorias[(int)l].id));
                                siguiente.putExtra("Name",categorias[(int)l].nombre);
                                siguiente.putExtra("Descripcion",categorias[(int)l].descripcion);

                                startActivity(siguiente);
                            }
                        });

                builder1.setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ComDelete del = new ComDelete(categorias[(int) l]);
                                del.execute();
                                dialog.cancel();
                            }
                        });
                builder1.show();

                return false;
            }
        });

        button_Create_Categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente = new Intent(Categories.this, Registry_Category.class);
                siguiente.putExtra("Action","Add");
                startActivity(siguiente);
            }
        });
    }

    public class ComDelete extends AsyncTask<String[][], String[][], String[][]> {
        Categoria categorias;
        public ComDelete(Categoria pCategoria) {
            categorias = pCategoria;
        }

        @Override
        protected String[][] doInBackground(String[][]... strings) {
            if (Communicator_Database.isOnline(getApplicationContext())){
                if (accionDeleteEnBase(categorias))
                    accion=true;
                else{
                    if (accionDeleteEnTelefono(categorias))
                        accion=true;
                    else
                        accion=false;
                }
            }
            else{
                if (accionDeleteEnTelefono(categorias))
                    accion=true;
                else
                    accion=false;
            }
            return new String[0][0];
        }

        @Override
        protected void onPostExecute(String[][] result) {
            if (accion == true){
                siguiente = new Intent(getApplicationContext(),Clients.class);
                startActivity(siguiente);
            }
            else{
                Toast toast = Toast.makeText(Categories.this,
                        "We cannot delete this categorie in this moment.", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    //Metodo que borra un elemento de la base de datos.
    private boolean accionDeleteEnBase(Categoria categorias){
        return false;
    }

    //Metodo que borra un elemento de la base de datos del telefono.
    private boolean accionDeleteEnTelefono(Categoria categorias){
        Communicator_Database com = new Communicator_Database();

        Gson gson = new Gson();
        String message = com.peticion("//control//", "{\"operation\":\"delete\",\"type\":\"categories\"" +
                ",\"jsonObject\":"+ gson.toJson(categorias) +"}");

        Result Resultado = gson.fromJson(message, Result.class);
        return Resultado.success;
    }
}
