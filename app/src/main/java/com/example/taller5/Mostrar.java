package com.example.taller5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.taller5.data.Usuario;
import com.example.taller5.data.daoUsuario;

import java.util.ArrayList;

public class Mostrar extends AppCompatActivity {

    ListView lista;
    daoUsuario dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar);

        lista = (ListView)findViewById(R.id.ListaUsuReg);

        dao =new daoUsuario(this);
        ArrayList<Usuario> l = dao.selectUsuarios(); //recupoera los datos de la Bdd por medio de el objeto dao

        ArrayList<String> list = new ArrayList<>(); //los asignamos a otro array de cadena

        for (Usuario u:l) {

            list.add(u.getId()+" Nombre: "+u.getNombre()+" , "+
                    " Apellido: "+u.getApellido()+", \n"+
                    "  Usuario: "+u.getUsuario()+" , "+
                    " Contraseña: "+u.getPassword()+". ");
            
        }

        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, list);
        lista.setAdapter(a); //del array adapter se lo pasamos al listview

    }
}