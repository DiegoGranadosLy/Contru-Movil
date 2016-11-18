package com.tec.diegogranados.contru_movil.Activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tec.diegogranados.contru_movil.Beans.RequestLogin;
import com.tec.diegogranados.contru_movil.Post.Communicator_Database;
import com.tec.diegogranados.contru_movil.R;

public class Login extends AppCompatActivity {

    Communicator comunicador;
    Intent siguiente;
    Button Button_Login;
    EditText Name_Login;
    EditText Password_Login;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button_Login   = (Button) findViewById(R.id.button_Ingresar_Log_In);
        Name_Login     = (EditText) findViewById(R.id.Name_Login);
        Password_Login = (EditText) findViewById(R.id.Password_Login);

        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicador = new Communicator();
                username = String.valueOf(Name_Login.getText());
                password = String.valueOf(Password_Login.getText());
                comunicador.execute(new String[0][0],new String[0][0],new String[0][0]);
            }
        });
    }

    public class Communicator extends AsyncTask<String[][], String[][], String[][]> {

        boolean accion;

        public Communicator() {
        }

        @Override
        protected String[][] doInBackground(String[][]... params){
//            boolean connection = Communicator_Database.isOnline(getApplicationContext());
//            if (connection == true){
//                //Hay que mandar la informacion al servidor y a la base de datos propia.
//                Communicator_Database com = new Communicator_Database();
//                String message = com.peticion("//login//","{\"username\":\""+ username
//                                              +"\",\"password\":\""+password +"\"}");
//
//                System.out.println("El mensaje recibido es: "+ message);
//                Gson gson = new Gson();
//                RequestLogin login = gson.fromJson(message, RequestLogin.class);
//
//                if (login.success==true){
//                    accion = true;
//                }
//                else{
//                    accion = false;
//                }
//            }
//            else{
//                //Se envia la informacion a la base de datos del telefono para su posterior sincronizacion
//            }
            return null;
        }

        @Override
        protected void onPostExecute(String[][] result) {
//            if (accion == true){
                siguiente = new Intent(getApplicationContext(), Main_Page_App.class);
                startActivity(siguiente);
//            }
//            else{
//                    Toast toast = Toast.makeText(Login.this,
//                            "Unknown user", Toast.LENGTH_LONG);
//                    toast.show();
//            }
        }
    }
}


