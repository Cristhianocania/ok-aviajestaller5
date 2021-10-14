package com.example.taller5;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.taller5.AdaptadorFotos.GaleriaImagenesAdapter;
import com.example.taller5.data.Foto;
import com.example.taller5.data.Usuario;
import com.example.taller5.data.daoUsuario;


import java.util.ArrayList;

public class Galeria extends AppCompatActivity {

    GridView grilla;
    SQLiteDatabase sql;

    daoUsuario ux;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);


        ux =new daoUsuario(this);

        grilla = findViewById(R.id.grillaGaleria);



        ArrayList<Foto> lista = new ArrayList<>();


        GaleriaImagenesAdapter miadaptador = new GaleriaImagenesAdapter(this, R.layout.griditem, lista);
        grilla.setAdapter(miadaptador);

        miadaptador = new GaleriaImagenesAdapter(this, R.layout.griditem, lista);

        sql = this.openOrCreateDatabase("BDUsuarios", MODE_PRIVATE, null);

     

        Cursor cr = sql.rawQuery("select * from fotos  ", null);

        Foto foto = null;



        Usuario u ;
        Bundle b = getIntent().getExtras();
        int asd=b.getInt("idu");

        u = ux.getUsuarioPorId(asd);




        while (cr.moveToNext()) {
            foto = new Foto();
            foto.setFoto(cr.getString(1));
            foto.setDescripcion(cr.getString(2));
            foto.setDireccion(cr.getString(3));
            foto.setidUsu(cr.getInt(4));


           if(foto.getidUsu() == u.getId()) //si el Id de foto es = al id usuario "fiiltro por usaurio"
            lista.add(foto);
        }



            grilla.setAdapter(miadaptador);

    }




}
