package com.tec.diegogranados.contru_movil.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.diegogranados.contru_movil.R;

public class Registry extends AppCompatActivity {

    EditText EditText_Name_Registry;
    EditText EditText_Last_Name_Registry;
    EditText EditText_Nickname_Registry;
    EditText EditText_Card_Number_Registry;
    EditText EditText_Telephone_Registry;
    EditText EditText_Password_Registry;
    TextView TextView_BornDate_Registry;
    Button Button_Date_Registry;
    Button Button_Check_In;

    String action;

    Intent siguiente;
    Communicator comunicador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        EditText_Name_Registry        = (EditText)findViewById(R.id.EditText_Name_Registry);
        EditText_Last_Name_Registry   = (EditText)findViewById(R.id.EditText_Last_Name_Registry);
        EditText_Nickname_Registry    = (EditText)findViewById(R.id.EditText_Nickname_Registry);
        EditText_Card_Number_Registry = (EditText)findViewById(R.id.EditText_Card_Number_Registry);
        EditText_Telephone_Registry   = (EditText)findViewById(R.id.EditText_Telephone_Registry);
        EditText_Password_Registry    = (EditText)findViewById(R.id.EditText_Password_Registry);
        TextView_BornDate_Registry    = (TextView)findViewById(R.id.TextView_Born_Date_Registry);
        Button_Date_Registry          = (Button)findViewById(R.id.Button_Date_Registry);
        Button_Check_In               = (Button) findViewById(R.id.button_Registry_Registrar);

        action = getIntent().getExtras().getString("Action");
        System.out.println("String de llegada:"+action);

        if (action.equals("Update")){//Accion de actualizacion de un cliente.
            EditText_Name_Registry.setText("XXXXXXXXXXXXXXX");
            EditText_Last_Name_Registry.setText("XXXXXXXXXXXXXXX");
            EditText_Nickname_Registry.setText("XXXXXXXXXXXXXXX");
            EditText_Card_Number_Registry.setText("XXXXXXXXXXXXXXX");
            EditText_Telephone_Registry.setText("XXXXXXXXXXXXXXX");
            EditText_Password_Registry.setText("XXXXXXXXXXXXXXX");
            TextView_BornDate_Registry.setText("XXXXXXXXXXXXXXX");
        }

        Button_Date_Registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(Registry.this);
                final View promptView = layoutInflater.inflate(R.layout.date, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registry.this);
                alertDialogBuilder.setView(promptView);

                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatePicker fecha = (DatePicker) promptView.findViewById(R.id.Date_Picker);
                                TextView_BornDate_Registry.setText(
                                        String.valueOf(fecha.getDayOfMonth())
                                                +"/"+fecha.getMonth()+"/"+fecha.getYear());

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });


        Button_Check_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicador = new Communicator();
                comunicador.execute("","","");
            }
        });

    }


    public class Communicator extends AsyncTask<String, String, String> {
        public Communicator() {
        }

        @Override
        protected String doInBackground(String... params){

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (action.equals("Update")){
                //Agregar accion de actualizacion de un cliente.
                Toast toast = Toast.makeText(Registry.this,
                        "Agregar accion de actualizacion de un cliente.", Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                //Agregar accion de agregar un cliente.
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Agregar accion de agregar un cliente.", Toast.LENGTH_LONG);
                toast.show();
            }
            siguiente = new Intent(getApplicationContext(), Main_Page_App.class);
            startActivity(siguiente);
        }
    }


}
