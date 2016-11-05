package com.tec.diegogranados.contru_movil.ListView;

import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Diego Granados on 05/11/2016.
 */

public class Order_Entry_List {
    private int idImagen;
    private String Titulo;
    private String Descripcion;

    public Order_Entry_List(int idImagen, String pTitulo, String pDescripcion) {
        this.idImagen = idImagen;
        this.Titulo = pTitulo;
        this.Descripcion = pDescripcion;
    }

    public String get_Titulo() {
        return this.Titulo;
    }

    public String get_Descripcion() {
        return this.Descripcion;
    }

    public int get_idImagen() {
        return idImagen;
    }
}