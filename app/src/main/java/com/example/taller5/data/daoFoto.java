package com.example.taller5.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoFoto {

    Context c;
    Foto f;

    ArrayList<Foto> lista;
    SQLiteDatabase sql;

    String bd="BDUsuarios";
    String tabla="create table if not exists fotos(id integer primary key autoincrement," +
            " fotouri String, descripcion text, direccion text,idduenio integer)";


    public daoFoto(Context c){

        this.c=c;
        sql=c.openOrCreateDatabase(bd, Context.MODE_PRIVATE,null); //abre BDD
        sql.execSQL(tabla); //crear la tabla
        f= new Foto();

    }




    public boolean insertFoto(Foto f){
        if(buscarF(f.getFoto())==0)
        {
            ContentValues cv= new ContentValues();
            cv.put("fotouri",f.getFoto());
            cv.put("descripcion",f.getDescripcion());
            cv.put("direccion",f.getDireccion());
            cv.put("idduenio",f.getidUsu());


            return ( sql.insert("fotos",null,cv)>0); }

        else
            return false; //no lo pudo insertar en la bdd

    }



    public int buscarF (String f) {
        int x = 0;
        lista = selectFotos();

        for (Foto fs : lista) {

            if (fs.getFoto().equals(f))
            {//para verificar que no haya otro usuario registrado de igual manera
                x++; //si hay algun usuario me va a regresar uno dentro de x
            }
        }
        return x; //si me regresa cero es que no hay usuarios registrados con ese nombre
    }



    public ArrayList<Foto> selectFotos()
    { //nos retorna todos las fotos que hay en la BDD
        ArrayList<Foto> lista = new ArrayList<>();
        Cursor cr = sql.rawQuery("select * from fotos", null);

        if (cr != null && cr.moveToFirst())
        { //preguntamos si esta vacio

            do {//si nos devuelve al m,enos un registro
                Foto f = new Foto();
                f.setId(cr.getInt(0));
                f.setFoto(cr.getString(1));
                f.setDescripcion(cr.getString(2));
                f.setDireccion(cr.getString(3));//extraigo
                f.setidUsu(cr.getInt(4));

                lista.add(f);

            } while (cr.moveToNext());
        }

        return lista;

    }




    public Foto getFoto(String f){

        lista= selectFotos();

        for (Foto fs : lista) {
            if(fs.getFoto().equals(f)){
                return fs;
            }
        }
        return null;
    }

    public Foto getFotoPorId(int id){

        lista= selectFotos();

        for (Foto fs : lista) {
            if(fs.getId()==id ){
                return fs;
            }
        }
        return null;
    }

    public boolean updateFoto (Foto f)
    {

        ContentValues cv= new ContentValues();
        cv.put("fotouri",f.getFoto());
        cv.put("descripcion",f.getDescripcion());
        cv.put("direccion",f.getDireccion());
        cv.put("idduenio",f.getidUsu());


        return ( sql.update("fotos",cv,"id="+f.getId(),null)>0);
    }


    public boolean deleteFoto(int id){

        return(sql.delete("fotos","id="+id,null)>0);
    }

}
