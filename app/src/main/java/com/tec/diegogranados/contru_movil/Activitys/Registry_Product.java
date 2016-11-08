package com.tec.diegogranados.contru_movil.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tec.diegogranados.contru_movil.R;

public class Registry_Product extends AppCompatActivity {

    EditText EditText_Name_Product_Registry;
    EditText EditText_Description_Registry_Product;
    EditText EditText_Quantity_Registry_Product;
    TextView TextView_Provider_Registry_Product;
    TextView TextView_Category_Registry_Product;
    Button button_BProvider_Registry_Product;
    Button button_BCategory_Registry_Product;
    Button button_Registry_Product_Registrar;
    RadioButton Yes;
    RadioButton No;
    Intent siguiente;

    String action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry__product);

        EditText_Name_Product_Registry        = (EditText)findViewById(R.id.EditText_Name_Product_Registry);
        EditText_Description_Registry_Product = (EditText)findViewById(R.id.EditText_Description_Registry_Product);
        EditText_Quantity_Registry_Product    = (EditText)findViewById(R.id.EditText_Quantity_Registry_Product);
        TextView_Provider_Registry_Product    = (TextView) findViewById(R.id.TextView_Provider_Registry_Product);
        TextView_Category_Registry_Product    = (TextView) findViewById(R.id.TextView_Category_Registry_Product);
        button_BProvider_Registry_Product     = (Button)findViewById(R.id.button_BProvider_Registry_Product);
        button_BCategory_Registry_Product     = (Button)findViewById(R.id.button_BCategory_Registry_Product);
        button_Registry_Product_Registrar     = (Button)findViewById(R.id.button_Registry_Product_Registrar);
        Yes                                   = (RadioButton)findViewById(R.id.rdbYes);
        No                                    = (RadioButton) findViewById(R.id.rdbNo);

        action = getIntent().getExtras().getString("Action");
        if (action.equals("Update")){
            EditText_Name_Product_Registry        .setText("XXXXXXXXXXXXXXXXXXXxx");
            EditText_Description_Registry_Product .setText("XXXXXXXXXXXXXXXXXXXxx");
            EditText_Quantity_Registry_Product    .setText("1111111111");
            TextView_Provider_Registry_Product    .setText("XXXXXXXXXXXXXXXXXXXxx");
            TextView_Category_Registry_Product    .setText("XXXXXXXXXXXXXXXXXXXxx");
            Yes                                   .setChecked(true);
            No                                    .setChecked(false);
        }
        accionBotones();
    }

    private void accionBotones(){
        button_BProvider_Registry_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String items[] = {"Trabajo Preeliminar","Cimientos","Paredes"
                        ,"Conreto Reforzado","Techos","Cielos","Repello","Entrepisos","Pisos",
                        "Enchapes","Instalacion Pluvial","Instalacion Sanitaria","Instalacion Electrica",
                        "Puertas","Cerrajeria","Ventanas"};
                AlertDialog.Builder Stages =new AlertDialog.Builder(Registry_Product.this);
                Stages.setItems(items,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView_Provider_Registry_Product.setText(items[which]);
                    }
                });
                Stages.show();
            }
        });

        button_BCategory_Registry_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String items[] = {"Trabajo Preeliminar","Cimientos","Paredes"
                        ,"Conreto Reforzado","Techos","Cielos","Repello","Entrepisos","Pisos",
                        "Enchapes","Instalacion Pluvial","Instalacion Sanitaria","Instalacion Electrica",
                        "Puertas","Cerrajeria","Ventanas"};
                AlertDialog.Builder Stages =new AlertDialog.Builder(Registry_Product.this);
                Stages.setItems(items,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView_Category_Registry_Product.setText(items[which]);
                    }
                });
                Stages.show();
            }
        });

        button_Registry_Product_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communicator comunicador = new Communicator();
                comunicador.execute(new String[0][0],new String[0][0],new String[0][0]);
            }
        });
    }


    public class Communicator extends AsyncTask<String[][], String[][], String[][]> {
        public Communicator() {
        }

        @Override
        protected String[][] doInBackground(String[][]... strings) {
            siguiente = new Intent(Registry_Product.this, Products.class);
            startActivity(siguiente);
            return new String[0][];
        }


        @Override
        protected void onPostExecute(String[][] result) {
        }
    }
}
