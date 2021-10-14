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

public class Main extends AppCompatActivity implements View.OnClickListener {
    EditText user,pass;
    Button btnEntrar,btnRegistrar;
    daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        user=(EditText)findViewById(R.id.User);
        pass=(EditText)findViewById(R.id.Pass);

        btnEntrar=(Button)findViewById(R.id.btnEntrar);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);

        btnEntrar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        dao = new daoUsuario(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnEntrar:

                String u=user.getText().toString();
                String p=pass.getText().toString();

                if(u.equals("")&&p.equals("")){ //si los campos estan vacios
                    Toast.makeText(this, "ERROR:Campos Vacios", Toast.LENGTH_SHORT).show();
                } else if(dao.login(u,p)==1){
                    Usuario ux= dao.getUsuario(u,p); //si la funcion login devuelve 1 es porque encontro un usuario
                    Toast.makeText(this, "DATOS CORRECTOS", Toast.LENGTH_SHORT).show();
                    Intent i2= new Intent(Main.this,    Inicio.class);
                    i2.putExtra("id",ux.getId());

                    startActivity(i2);

                    finish();
                }else {
                    Toast.makeText(this, "USUARIO Y/o Contraseña incorrectos", Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.btnRegistrar:
                Intent i= new Intent(this,Registrar.class);
                startActivity(i);
                break;

        }
    }
}