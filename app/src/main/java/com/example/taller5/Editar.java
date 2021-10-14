package com.example.taller5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taller5.data.Usuario;
import com.example.taller5.data.daoUsuario;

public class Editar extends AppCompatActivity implements View.OnClickListener {
    EditText ediUser,ediPass,ediNombre,ediApellido;
    Button btnActualizar,btnCancelar;
    int id=0;
    Usuario u;
    daoUsuario dao;
    Intent x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);




        ediUser=(EditText)findViewById(R.id.EditUser);
        ediPass=(EditText)findViewById(R.id.EditPass);
        ediNombre=(EditText)findViewById(R.id.EditNombre);
        ediApellido=(EditText)findViewById(R.id.EditApellido);
        btnActualizar=(Button)findViewById(R.id.btnEditActualizar);
        btnCancelar=(Button)findViewById(R.id.btnEditCancelar);

        btnActualizar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id=b.getInt("id");
        dao=new daoUsuario(this);
        u=dao.getUsuarioPorId(id);
        ediUser.setText(u.getUsuario());
        ediPass.setText(u.getPassword());
        ediNombre.setText(u.getNombre());
        ediApellido.setText(u.getApellido());



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnEditActualizar:
                u.setUsuario(ediUser.getText().toString());
                u.setPassword(ediPass.getText().toString());
                u.setNombre(ediNombre.getText().toString());
                u.setApellido(ediApellido.getText().toString());

                if (!u.isNull()) {
                    Toast.makeText(this, "ERROR:Campos Vacios", Toast.LENGTH_LONG).show();
                }
                else if (dao.updateUsuario(u)){
                    Toast.makeText(this,"Actualizacion Exitosa   ",Toast.LENGTH_LONG).show();
                    Intent c = new Intent(Editar.this, Inicio.class);
                    c.putExtra("id",u.getId()); //obtiene el id para la actualizacion
                    startActivity(c);
                    finish();
                } else {
                    Toast.makeText(this,"No se puede actualizar",Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.btnEditCancelar:
                Intent i2= new Intent(Editar.this,Inicio.class);
                i2.putExtra("id",u.getId());
                startActivity(i2);
                finish();
                break;
        }

    }
}