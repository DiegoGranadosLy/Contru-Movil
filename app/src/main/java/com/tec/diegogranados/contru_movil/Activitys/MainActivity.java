package com.tec.diegogranados.contru_movil.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tec.diegogranados.contru_movil.Post.Communicator_Database;
import com.tec.diegogranados.contru_movil.R;
import com.tec.diegogranados.contru_movil.SQL_Lite.DataBaseManager;
import com.tec.diegogranados.contru_movil.Threads.Thread_Sync;


public class MainActivity extends AppCompatActivity {

    Button Login_Main;
    Button Registry_Main;
    Intent accion;
    DataBaseManager DBM;
    boolean action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login_Main = (Button) findViewById(R.id.button_Login_Main);
        Registry_Main = (Button) findViewById(R.id.button_Registry_Main);
        action = true;
        DBM = new DataBaseManager(getApplicationContext());
        Cursor cursor = DBM.getActMat();
        System.out.println("Cuenta total:" +cursor.getCount());//////////////////////
        if (cursor.getCount()==0){//Si no existe una sincronizacion previa
            if (Communicator_Database.isOnline(getApplicationContext())==true){
                long a = DBM.insertarActMat(1);
                System.out.println("El valor long es de:" + a);
                Thread_Sync thread = new Thread_Sync(getApplicationContext());
                thread.start();
            }
            else{//En caso de no poder sincronizar con el servidor
                Toast toast = Toast.makeText(MainActivity.this, "We cant sync by first time.", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else{//NCuando no es la primer sincronizacion que realiza
            cursor.moveToFirst();
            for(int i =0;i<cursor.getCount();i++){//Ciclo que busca la fecha actual.
                if (cursor.getString(0).equals(getDate())&& cursor.getInt(1)==1){
                    action=false;
                    break;
                }
                cursor.moveToNext();
            }

            if (action){//Si debe sincronizar
                if (Communicator_Database.isOnline(getApplicationContext())==true){
                    //Agregar accion de sincronizacion matutina
                    Thread_Sync thread = new Thread_Sync(getApplicationContext());
                    thread.start();
                    try {
                        DBM.insertarActMat(1);
                    }catch(Exception e){System.out.println("No es posible insertar la fecha selecciona");}

                }
                else{
                    Toast toast = Toast.makeText(MainActivity.this, "We cant sync at this moment.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            else{//Si no debe sincronizar
                Toast toast = Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        accionButton();
    }

    private String getDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

    private void accionButton(){
        Login_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Thread_Sync.getSync()){
                    accion = new Intent(MainActivity.this, Login.class);
                    accion.putExtra("Action","Add");
                    startActivity(accion);
                }
                else{System.out.println("No puedo avanzar por el thread");}
            }
        });

        Registry_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Thread_Sync.getSync()){
                    accion = new Intent(MainActivity.this, Registry_Client.class);
                    accion.putExtra("Action","Add");
                    startActivity(accion);
                }
                else{System.out.println("No puedo avanzar por el thread");}
            }
        });
    }


}
