package com.tec.diegogranados.contru_movil.Activitys;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tec.diegogranados.contru_movil.R;

public class MainActivity extends AppCompatActivity {

    Button Login_Main;
    Button Registry_Main;
    Intent accion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login_Main = (Button) findViewById(R.id.button_Login_Main);
        Registry_Main = (Button) findViewById(R.id.button_Registry_Main);

        Login_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accion = new Intent(MainActivity.this, Login.class);
                startActivity(accion);
            }
        });

        Registry_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accion = new Intent(MainActivity.this, Registry_Client.class);
                accion.putExtra("Action","Add");
                startActivity(accion);
            }
        });
    }

}
