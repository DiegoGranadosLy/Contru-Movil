package com.tec.diegogranados.contru_movil.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tec.diegogranados.contru_movil.Beans.Persona;
import com.tec.diegogranados.contru_movil.Beans.Producto;
import com.tec.diegogranados.contru_movil.Beans.Result;
import com.tec.diegogranados.contru_movil.Beans.Sucursal;
import com.tec.diegogranados.contru_movil.Post.Communicator_Database;
import com.tec.diegogranados.contru_movil.R;
import java.util.ArrayList;

public class Registry_Order extends AppCompatActivity {

    TextView TextView_Branch_Registry_Order;
    Button Button_BBranch_Registry_Order;
    TextView TextView_Client_Registry_Order;
    Button   Button_BClient_Registry_Order;
    EditText EditText_Telephone_Registry_Order;
    TextView TextView_Time_Registry_Order;
    Button   Button_BTime_Registry_Order;
    Button   Button_BAddProduct_Registry_Order;
    Button   Button_BBuyProduct_Registry_Order;
    ListView ListView_OrdersProducts;

    ArrayAdapter<String> adaptador;

    String action;
    boolean accion;
    Sucursal[] sucursales;
    Persona [] clientes;
    Producto[] productos;
    ArrayList<Producto> productosComprados;
    ArrayList<String> productosCompradosString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry__order);

        productosCompradosString=new ArrayList();
        productosComprados      =new ArrayList();

        TextView_Branch_Registry_Order    = (TextView) findViewById(R.id.TextView_Branch_Registry_Order);
        Button_BBranch_Registry_Order     = (Button) findViewById(R.id.Button_BBranch_Registry_Order);
        TextView_Client_Registry_Order    = (TextView) findViewById(R.id.TextView_Client_Registry_Order);
        Button_BClient_Registry_Order     = (Button) findViewById(R.id.Button_BClient_Registry_Order);
        EditText_Telephone_Registry_Order = (EditText) findViewById(R.id.EditText_Telephone_Registry_Order);
        TextView_Time_Registry_Order      = (TextView) findViewById(R.id.TextView_Time_Registry_Order);
        Button_BTime_Registry_Order       = (Button) findViewById(R.id.Button_BTime_Registry_Order);
        Button_BAddProduct_Registry_Order = (Button) findViewById(R.id.Button_BAddProduct_Registry_Order);
        Button_BBuyProduct_Registry_Order = (Button) findViewById(R.id.Button_BBuyProduct_Registry_Order);
        ListView_OrdersProducts           = (ListView) findViewById(R.id.ListView_OrdersProducts);

        action = getIntent().getExtras().getString("Action");
        accion = false;
        if (action.equals("Update")){
            TextView_Branch_Registry_Order    .setText("XxXxXxX");
            TextView_Client_Registry_Order    .setText("XxXxXxX");
            EditText_Telephone_Registry_Order .setText("XxXxXxX");
            TextView_Time_Registry_Order      .setText("XxXxXxX");
        }
        CommunicatorList comList = new CommunicatorList();
        comList.execute();

    }


    private void accionBotones(){
        Button_BBranch_Registry_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Cantidad de sucursales:" + sucursales.length);
                final String items[] = new String[sucursales.length];
                for (int i = 0;i<items.length;i++)
                    items[i] = sucursales[i].nombre;

                AlertDialog.Builder Stages =new AlertDialog.Builder(Registry_Order.this);
                Stages.setItems(items,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView_Branch_Registry_Order.setText(items[which]);
                    }
                });
                Stages.show();
            }
        });

        Button_BClient_Registry_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String items[] = new String[clientes.length];
                for (int i = 0;i<items.length;i++)
                    items[i] = clientes[i].nombre;

                AlertDialog.Builder Stages =new AlertDialog.Builder(Registry_Order.this);
                Stages.setItems(items,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView_Client_Registry_Order.setText(items[which]);
                    }
                });
                Stages.show();
            }
        });

        Button_BTime_Registry_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf.format(c.getTime());
                TextView_Time_Registry_Order.setText(strDate);
            }
        });
        Button_BAddProduct_Registry_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String items[] = new String[productos.length];
                for (int i = 0;i<items.length;i++)
                    items[i] = productos[i].nombre;

                AlertDialog.Builder Stages =new AlertDialog.Builder(Registry_Order.this);
                Stages.setItems(items,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        productosCompradosString.add(productos[which].nombre);
                        adaptador.notifyDataSetChanged();
                        final Producto prod = new Producto();
                        prod.id          = productos[which].id;
                        prod.nombre      = productos[which].nombre;
                        prod.descripcion = productos[which].descripcion;
                        prod.precio      = productos[which].precio;
                        prod.disponible  = productos[which].disponible;
                        prod.sucursal    = productos[which].sucursal;
                        prod.proveedor   = productos[which].proveedor;
                        prod.categoria   = productos[which].categoria;

                        LayoutInflater layoutInflater = LayoutInflater.from(Registry_Order.this);
                        View promptView = layoutInflater.inflate(R.layout.comments, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registry_Order.this);
                        alertDialogBuilder.setView(promptView);

                        final EditText editText = (EditText) promptView.findViewById(R.id.Edit_Text_Comentar);
                        final TextView comentario = (TextView) promptView.findViewById(R.id.Text_View_Comentario);
                        comentario.setText("Disponibles: " +productos[which].disponible);
                        // setup a dialog window
                        alertDialogBuilder.setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (Integer.parseInt(String.valueOf(editText.getText()))>productos[which].disponible){
                                            prod.disponible  = productos[which].disponible;
                                        }
                                        else {
                                            prod.disponible = Integer.parseInt(String.valueOf(editText.getText()));
                                        }
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

                        productosComprados.add(prod);
                    }
                });
                Stages.show();
            }
        });
        Button_BBuyProduct_Registry_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communicator comunicador = new Communicator();
                comunicador.execute(0,0,0);
            }
        });
    }

    private void getSucursales(){
        boolean connection = Communicator_Database.isOnline(getApplicationContext());
        if (connection == true) {
            Communicator_Database com = new Communicator_Database();
            String message = com.peticion("//show//", "{\"type\":\"branches\"}");

            Gson gson = new Gson();
            sucursales = gson.fromJson(message, Sucursal[].class);
        }
        else {
            Sucursal a = new Sucursal();
            a.id=0;a.nombre="Coyol de Alajuela";a.ubicacion="Alajuela";
            Sucursal b = new Sucursal();
            b.id=1;b.nombre="Coyol de Heredia";b.ubicacion="Heredia";
            Sucursal c = new Sucursal();
            c.id=2;c.nombre="Coyol de San Jose";c.ubicacion="San Jose";
            sucursales = new Sucursal[]{a,b,c};
        }
    }

    private void getClientes(){
        boolean connection = Communicator_Database.isOnline(getApplicationContext());
        if (connection == true) {
            Communicator_Database com = new Communicator_Database();
            String message = com.peticion("//show//", "{\"type\":\"clients\"}");

            Gson gson = new Gson();
            clientes = gson.fromJson(message, Persona[].class);
        }
        else {
            Persona a = new Persona();
            a.id = "321654987";a.nombre = "Javier";a.apellido = "Sancho";
            a.residencia="San Jose"; a.f_nacimiento = "13/3/1994";a.telefono = 88447766;
            Persona b = new Persona();
            b.id = "123456789";b.nombre = "Ernesto";b.apellido = "Ulate";
            b.residencia="San Carlos"; b.f_nacimiento = "1/10/1995";b.telefono = 88563266;
            Persona c = new Persona();
            c.id = "304860692";c.nombre = "Diego";c.apellido = "Granados";
            c.residencia="Cartago"; c.f_nacimiento = "13/5/1995";c.telefono = 88447766;
            Persona d = new Persona();
            d.id = "304860695";d.nombre = "Fabian";d.apellido = "Astorga";
            d.residencia="Oreamuno"; d.f_nacimiento = "13/5/1996";d.telefono = 61447766;

            clientes = new Persona[]{a,b,c,d};
        }
    }

    private void getProductos(){
        boolean connection = Communicator_Database.isOnline(getApplicationContext());
        if (connection == true) {
            Communicator_Database com = new Communicator_Database();
            String message = com.peticion("//show//", "{\"type\":\"products\"}");

            Gson gson = new Gson();
            productos = gson.fromJson(message, Producto[].class);
        }
        else {
            Producto a = new Producto();
            a.id = 1;a.nombre = "Taladro Hiperbolico";a.descripcion = "Taladro";a.precio = 70000;a.disponible = 100;a.sucursal = "Ferreteria Brenes";a.proveedor = "Sesalassey Marley";a.categoria = "Herramientas Electricas";
            Producto b = new Producto();
            b.id = 2;b.nombre = "Martillo";b.descripcion = "Martillo Casual";b.precio = 75000;b.disponible = 70;b.sucursal = "El Pochote";b.proveedor = "Sesalassey Marley";b.categoria = "Herramientas";
            Producto c = new Producto();
            c.id = 3;c.nombre = "Galon Pintura";c.descripcion = "Para pintar";c.precio = 12000;c.disponible = 500;c.sucursal = "Pinturas Sur";c.proveedor = "Sesalassey Marley";c.categoria = "Pinturas";
            productos = new Producto[]{a,b,c};
        }
    }

    private void getProductosPedidos(){

    }

    public class CommunicatorList extends AsyncTask<Integer,Integer,Integer> {
        public CommunicatorList() {
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            getSucursales();
            getClientes();
            getProductos();
            getProductosPedidos();
            adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, productosCompradosString);///Add
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            ListView_OrdersProducts.setAdapter(adaptador);
            accionBotones();
        }
    }


    public class Communicator extends AsyncTask<Integer,Integer,Integer> {
        public Communicator() {
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            boolean connection = Communicator_Database.isOnline(getApplicationContext());
            if (action.equals("Update")){
                //Agregar la accion de actualizacion directo a la base.
                if (connection == true){
                    if (accionActualizarEnBase()) //Se actualiza directo la base
                        accion = true;
                    else{//Se actualiza en telefono
                        if (accionActualizarBaseTelefono())
                            accion = true;
                        else//No se pudo actualizar.
                            accion = false;
                    }
                }
                else{//En caso de no haber conexion se agrega al telefono
                    if (accionActualizarBaseTelefono())
                        accion = true;
                    else//No se logra actualizar.
                        accion = false;
                }
            }
            else{
                //Agregar la accion de creacion de un cliente.
                if (connection == true){
                    if (accionAgregarEnBase())//Creacion directa a la base de datos
                        accion = true;
                    else{//En caso de tener que agregar en el telefono
                        if (accionAgregarBaseTelefono())
                            accion = true;
                        else//No se logro agregar a ninguna base.
                            accion = false;
                    }
                }
                else{//Se agrega a la base del telefono.
                    if (accionAgregarBaseTelefono())
                        accion = true;
                    else//No se logra agregar al usuario.
                        accion = false;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Intent siguiente;
            if (accion == true){
                if (action.equals("Update"))
                    siguiente = new Intent(getApplicationContext(), Orders.class);
                else
                    siguiente = new Intent(getApplicationContext(), Main_Page_App.class);
                startActivity(siguiente);
            }
            else{
                Toast toast = Toast.makeText(Registry_Order.this,
                        "We cannot create a order in this moment.", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }


    private boolean accionAgregarEnBase(){
//        CART: {"id_producto":int,"cantidad":int};
//        Comprar productos.
//        POST: shop
//        Mensaje: {"id_cliente":string,"cart":[CART]}
//        Retorna: RESULT

        Communicator_Database com = new Communicator_Database();
        Gson gson = new Gson();

        return false;
    }

    private boolean accionAgregarBaseTelefono(){
        return false;
    }

    private boolean accionActualizarEnBase(){
        return false;
    }

    private boolean accionActualizarBaseTelefono(){

        return false;
    }
}
