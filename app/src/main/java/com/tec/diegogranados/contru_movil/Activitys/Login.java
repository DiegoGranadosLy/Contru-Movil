package com.tec.diegogranados.contru_movil.Activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tec.diegogranados.contru_movil.R;

public class Login extends AppCompatActivity {

    Communicator comunicador;
    Intent siguiente;
    Button Button_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button_Login = (Button) findViewById(R.id.button_Ingresar_Log_In);

        Button_Login.setOnClickListener(new View.OnClickListener() {
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
            siguiente = new Intent(Login.this, Main_Page_App.class);
            startActivity(siguiente);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }
}
