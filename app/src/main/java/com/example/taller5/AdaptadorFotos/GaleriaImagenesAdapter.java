package com.example.taller5.AdaptadorFotos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taller5.R;
import com.example.taller5.data.Foto;

import java.util.List;

public class GaleriaImagenesAdapter extends BaseAdapter {
    private final Context context;
    private final int resourceLayout;
    private final List<Foto> lista;


    public GaleriaImagenesAdapter(@NonNull Context context, int resource, List<Foto> lista) {
        this.context = context;
        this.resourceLayout = resource;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).getId();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View view =  LayoutInflater.from(context).inflate(resourceLayout, null);

        ImageView imagen = view.findViewById(R.id.imCam);
        TextView ciudad= view.findViewById(R.id.tvCiudad);
        TextView descripcion= view.findViewById(R.id.tvdescripcion);
        ciudad.setText(lista.get(position).getDireccion());
        descripcion.setText(lista.get(position).getDescripcion());

        Bitmap bitmap = BitmapFactory.decodeFile(lista.get(position).getFoto());
        imagen.setImageBitmap(bitmap);

        return view;
    }
}