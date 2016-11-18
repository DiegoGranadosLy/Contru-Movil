package com.tec.diegogranados.contru_movil.Activitys;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.tec.diegogranados.contru_movil.Beans.Persona;
import com.tec.diegogranados.contru_movil.Post.Communicator_Database;
import com.tec.diegogranados.contru_movil.R;
import com.tec.diegogranados.contru_movil.SQL_Lite.DBHelper;
import com.tec.diegogranados.contru_movil.SQL_Lite.DataBaseManager;
import com.tec.diegogranados.contru_movil.Threads.Thread_Sync;

public class MainActivity extends AppCompatActivity {

    Button Login_Main;
    Button Registry_Main;
    Intent accion;
    DataBaseManager DBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBM = new DataBaseManager(getApplicationContext());

        if (true){
            if (Communicator_Database.isOnline(getApplicationContext())==true){
                //Agregar accion de sincronizacion matutina
                Thread_Sync thread = new Thread_Sync(getApplicationContext());
                thread.start();
            }
            else{
                //Agregar mensajito de que no se pudo realizar la sincronizacion matutina.
            }
        }
        else{
            //No es necesario realizar la sincronizacion matutina
        }


        Login_Main = (Button) findViewById(R.id.button_Login_Main);
        Registry_Main = (Button) findViewById(R.id.button_Registry_Main);

        Login_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Ruta del archivo SQLITE: "+DBM.db.getPageSize());
                accion = new Intent(MainActivity.this, Login.class);
                startActivity(accion);
            }
        });

        Registry_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("///////////////: "+DBM.db.getPath());
//                Persona per = new Persona();
//
//                Cursor cursor = DBM.getUsuarios();
//                System.out.println("Cantidad de registro en la tabla usuarios: "+cursor.getCount());
////                per.id = "115770840";
////                per.nombre =  "Javier";
////                per.apellido = "Sancho";
////                per.residencia = "San Jose";
////                per.f_nacimiento = "19940804";
////                per.telefono = 87484526;
////
////                DBM.insertarPersona(per);
////
////                per.id = "304960870";
////                per.nombre =  "Fabian";
////                per.apellido = "Astorga";
////                per.residencia = "Cartago";
////                per.f_nacimiento = "19960906";
////                per.telefono = 84333333;
////
////                DBM.insertarPersona(per);
////
////                per.id = "207440546";
////                per.nombre =  "Ernesto";
////                per.apellido = "Ulate";
////                per.residencia = "San Carlos";
////                per.f_nacimiento = "19950918";
////                per.telefono = 88649896;
////
////                DBM.insertarPersona(per);
////
////                per.id = "304860692";
////                per.nombre =  "Diego";
////                per.apellido = "Granados";
////                per.residencia = "Cartago";
////                per.f_nacimiento = "19950513";
////                per.telefono = 83053346;
////
////                DBM.insertarPersona(per);
//
////                per.id = "153542322";
////                per.nombre =  "Alejandro";
////                per.apellido = "Alpizar";
////                per.residencia = "San Cayetano";
////                per.f_nacimiento = "19800415";
////                per.telefono = 84535465;
////
////                DBM.insertarPersona(per);
////
////                per.id = "754165186";
////                per.nombre =  "Selassie";
////                per.apellido = "Marley";
////                per.residencia = "Limon";
////                per.f_nacimiento = "19751013";
////                per.telefono = 88888888;
////
////                DBM.insertarPersona(per);
//
////                Cursor cursor = DBM.getUsuarios();
////                System.out.println("Cantidad de registro en la tabla usuarios: "+cursor.getCount());
                accion = new Intent(MainActivity.this, Registry_Client.class);
                accion.putExtra("Action","Add");
                startActivity(accion);
            }
        });
    }

}
