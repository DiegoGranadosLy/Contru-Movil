package com.tec.diegogranados.contru_movil.Activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tec.diegogranados.contru_movil.R;

public class Registry extends AppCompatActivity {

    EditText EditText_Name_Registry;
    EditText EditText_Last_Name_Registry;
    EditText EditText_Nickname_Registry;
    EditText EditText_Card_Number_Registry;
    EditText EditText_Code_Registry;
    EditText EditText_Telephone_Registry;
    EditText EditText_Password_Registry;
    Button Button_Check_In;

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
        EditText_Code_Registry        = (EditText)findViewById(R.id.EditText_Code_Registry);
        EditText_Telephone_Registry   = (EditText)findViewById(R.id.EditText_Telephone_Registry);
        EditText_Password_Registry    = (EditText)findViewById(R.id.EditText_Password_Registry);
        Button_Check_In               = (Button) findViewById(R.id.button_Registry_Registrar);

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
            siguiente = new Intent(Registry.this, Main_Page_App.class);
            startActivity(siguiente);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }
}
