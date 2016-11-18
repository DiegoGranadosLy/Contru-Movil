package com.tec.diegogranados.contru_movil.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.diegogranados.contru_movil.R;
import com.tec.diegogranados.contru_movil.Threads.Thread_Sync;
import com.tec.diegogranados.contru_movil.User.User;

public class Main_Page_App extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView textUser;
    TextView textAccess;
    Thread_Sync thread_sync;
    Intent siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_button_refresh_Main_Page);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sinchronizing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                actionFloatingButton();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textUser   = (TextView)findViewById(R.id.Usuario_Main_Menu);
        textAccess = (TextView)findViewById(R.id.Acceso_Main_Menu);
        textUser.setText(User.NAME+ " " + User.APELLIDO);
        textAccess.setText(User.ACCESO);
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

        if (Thread_Sync.getSync()==true){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Aplicacion Sincronizando con la base de datos por favor espere.", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            if (id == R.id.nav_clients) {
                siguiente = new Intent(Main_Page_App.this, Clients.class);
                startActivity(siguiente);
            }
            else if (id == R.id.nav_products) {
                siguiente = new Intent(Main_Page_App.this, Products.class);
                startActivity(siguiente);
            } else if (id == R.id.nav_orders) {
                siguiente = new Intent(Main_Page_App.this, Orders.class);
                startActivity(siguiente);
            } else if (id == R.id.nav_providers) {
                siguiente = new Intent(Main_Page_App.this, Providers.class);
                startActivity(siguiente);
            } else if (id == R.id.nav_categories) {
                siguiente = new Intent(Main_Page_App.this, Categories.class);
                startActivity(siguiente);
            } else if (id == R.id.nav_vendors) {
                siguiente = new Intent(Main_Page_App.this, Vendors.class);
                startActivity(siguiente);
            } else if (id == R.id.nav_mainpage) {
                siguiente = new Intent(Main_Page_App.this, Main_Page_App.class);
                startActivity(siguiente);
           } else if (id == R.id.nav_exit) {
                siguiente = new Intent(Main_Page_App.this, MainActivity.class);
                startActivity(siguiente);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void actionFloatingButton(){
        Boolean testvar = Thread_Sync.getSync();
        if (testvar==null){
            thread_sync = new Thread_Sync(getApplicationContext());
            System.out.println("actionFloatingButton");
            thread_sync.start();
        }
        if (testvar==true){
            thread_sync = new Thread_Sync(getApplicationContext());
            System.out.println("Ya hubo una sincronizacion");
            thread_sync.start();
        }
        if (testvar==false){
            thread_sync = new Thread_Sync(getApplicationContext());
            System.out.println("Hay que comenzar la sincronizacion");
            thread_sync.start();
        }
    }
}
