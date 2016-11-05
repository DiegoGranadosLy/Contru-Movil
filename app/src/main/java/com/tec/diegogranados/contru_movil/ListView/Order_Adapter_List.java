package com.tec.diegogranados.contru_movil.ListView;

/**
 * Created by Diego Granados on 05/11/2016.
 */


import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;


import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public abstract class Order_Adapter_List extends BaseAdapter implements Filterable{

    private ArrayList<Order_Entry_List> entradas;
    private ArrayList<Order_Entry_List> copia;
    private int R_layout_IdView;
    private Context contexto;

    public Order_Adapter_List(Context contexto, int R_layout_IdView, ArrayList<Order_Entry_List> entradas) {
        super();
        this.contexto = contexto;
        this.entradas = entradas;
        this.copia = entradas;
        this.R_layout_IdView = R_layout_IdView;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup pariente) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
        }
        onEntrada (entradas.get(posicion), view);
        return view;
    }

    @Override
    public int getCount() {
        return entradas.size();
    }

    @Override
    public Object getItem(int posicion) {
        return entradas.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    /** Devuelve cada una de las entradas con cada una de las vistas a la que debe de ser asociada
     * @param entrada La entrada que será la asociada a la view. La entrada es del tipo del paquete/handler
     * @param view View particular que contendrá los datos del paquete/handler
     */
    public abstract void onEntrada (Object entrada, View view);


    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                entradas =(ArrayList<Order_Entry_List>)results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null && constraint.length() > 0) {
                    ArrayList<Order_Entry_List> filterList = new ArrayList<Order_Entry_List>();
                    for (int i = 0; i < copia.size(); i++) {
                        if ((copia.get(i).get_Titulo().toUpperCase()).contains(constraint.toString().toUpperCase())) {

                            Order_Entry_List lista_pedido = new Order_Entry_List(copia.get(i).get_idImagen()
                                    ,copia.get(i).get_Titulo() ,  copia.get(i).get_Descripcion());

                            filterList.add(lista_pedido);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = copia.size();
                    results.values = copia;
                }
                return results;
            }
        };
        return filter;
    }
}