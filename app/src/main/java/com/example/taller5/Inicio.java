package com.example.taller5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taller5.data.Foto;
import com.example.taller5.data.Usuario;
import com.example.taller5.data.daoFoto;
import com.example.taller5.data.daoUsuario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    Button btnEditar, btnEliminar, btnMostrar, btnSalir,btnGaleria;
    EditText etDescrip;
    TextView labelnombre, tvLong, tvLat, tvCity;
    ImageButton IbtnSacarFoto, IbtnContinuar, IbShare;
    ImageView imV1;
    String rutaimagen;//nos ayuda a identificar la ruta creada para loas fotos
    private LocationManager ubicacion;


    int id,idf = 0;
    Usuario u;
    daoUsuario dao;


    daoFoto daof;

    final int CAPTURA_IMAGEN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);



        labelnombre = (TextView) findViewById(R.id.ILabelTit);
        tvLat = (TextView) findViewById(R.id.tvLatitud);
        tvLong = (TextView) findViewById(R.id.tvLongitud);
        tvCity = (TextView) findViewById(R.id.tvCity);
        btnEditar = (Button) findViewById(R.id.btnEditarI);
        btnGaleria = (Button) findViewById(R.id.btnGaleria);
        btnEliminar = (Button) findViewById(R.id.btnEliminarI);
       // btnMostrar = (Button) findViewById(R.id.btnMostrarI);
        btnSalir = (Button) findViewById(R.id.btnSalirI);
        IbtnContinuar = (ImageButton) findViewById(R.id.IBtnContinuar);
        IbtnSacarFoto = (ImageButton) findViewById(R.id.IBtnFoto);
        IbShare = (ImageButton) findViewById(R.id.ibcompartir);
        imV1 = (ImageView) findViewById(R.id.imageInicio);
        etDescrip= (EditText)findViewById(R.id.etDescrip);

        boolean b1 =false;
        IbtnContinuar.setColorFilter(1234);
        IbShare.setColorFilter(1234);
        IbtnContinuar.setEnabled(b1);
        IbShare.setEnabled(b1);

        btnEditar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnGaleria.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
        IbtnSacarFoto.setOnClickListener(this);
        IbtnContinuar.setOnClickListener(this);
        IbShare.setOnClickListener(this);



        Bundle b = getIntent().getExtras();
        id = b.getInt("id");



        dao = new daoUsuario(this);
        u = dao.getUsuarioPorId(id);

        daof = new daoFoto(this);


        labelnombre.setText("Bienvenido " + u.getNombre() + " " + u.getApellido() );





        estadoGPS();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnEditarI:
                Intent a = new Intent(Inicio.this, Editar.class);
                a.putExtra("id", id);
                startActivity(a);
                break;
            case R.id.btnEliminarI:
                //dailogo para eliminar registro
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setMessage("¿Estas seguro de ELIMINAR tu cuenta?");
                b.setCancelable(false);
                b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (dao.deleteUsuario(id)) {
                            Toast.makeText(Inicio.this, "Se elimino correctamente", Toast.LENGTH_LONG).show();
                            Intent a = new Intent(Inicio.this, Main.class);
                            startActivity(a);
                            finish();
                        } else {
                            Toast.makeText(Inicio.this, "Error:No se elimino la cuenta", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                b.show();

                break;
           /* case R.id.btnMostrarI:
                Intent c = new Intent(Inicio.this, Mostrar.class);
                startActivity(c);
                break;*/

            case R.id.btnSalirI:
                Intent i2 = new Intent(Inicio.this, Main.class);
                startActivity(i2);
                finish();
                break;

            case R.id.IBtnFoto:


                estadoGPS();
                registrarLocalizacion();

                tomarFoto(v);





                break;

            case R.id.IBtnContinuar:

                Foto f = new Foto();
                f.setFoto(rutaimagen);
                f.setDescripcion(etDescrip.getText().toString());
                f.setDireccion(tvCity.getText().toString());
                f.setidUsu(id);

                if (daof.insertFoto(f)){
                    Toast.makeText(this,"FOTO EXITOSA",Toast.LENGTH_LONG).show();



                } else {
                    Toast.makeText(this,"TOME UNA FOTO PARA GUARDAR",Toast.LENGTH_LONG).show();
                }



                //limpieza de datos cada vez que tomo una foto :)
                Uri uriImage = Uri.parse("android.resource://" + getPackageName() +"/"+R.drawable.paisajito);
                imV1.setImageURI(uriImage);
                etDescrip.setText("");
                IbShare.setEnabled(false);
                IbShare.setColorFilter(0);
                IbtnContinuar.setEnabled(false);
                IbtnContinuar.setColorFilter(0);




                break;

            case R.id.ibcompartir:



                Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


                startActivityForResult(galeria, 1);
                String text = tvCity.getText().toString();
                Uri pictureUri = Uri.parse(rutaimagen);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                shareIntent.putExtra(Intent.EXTRA_STREAM, pictureUri);
                shareIntent.setType("image/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Compartir foto de viaje con ..."));


                break;


            case R.id.btnGaleria:
                Intent aim = new Intent(Inicio.this, Galeria.class);
                aim.putExtra("idu", id);

                startActivity(aim);


        }

    }




    public void tomarFoto(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File imagenarchivo = null;
            try {
                imagenarchivo = crearImagen();
            } catch (IOException ex) {
                Log.e("Error", ex.toString());
            }
            if (imagenarchivo != null) {

                Uri fotoUri = FileProvider.getUriForFile(this, "com.example.taller5.fileprovider",
                        imagenarchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intent, CAPTURA_IMAGEN); //  nos devuelve la imagen que se toma
            }

        }

    }

    private File crearImagen() throws IOException { //metodo para crear un archivo temporal de la imagen

        String nombreImagen = "foto";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        rutaimagen = imagen.getAbsolutePath();//rutaabsoluta donde guarda el archivo y su nombre


        return imagen;
    }


    private String crearNombreArchivoJPG() {

        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        return fecha + ".JPG";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURA_IMAGEN && resultCode == RESULT_OK) {
            Bitmap bitmap1 = BitmapFactory.decodeFile(rutaimagen);
            imV1.setImageBitmap(bitmap1); //se pasa el bitmap al
            IbShare.clearColorFilter();
            IbShare.setEnabled(true);
            IbtnContinuar.setEnabled(true);
            IbtnContinuar.clearColorFilter();
            try {

                FileOutputStream fos = openFileOutput(crearNombreArchivoJPG(), Context.MODE_PRIVATE); //para guardar en memoria
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void estadoGPS (){

        AlertDialog.Builder b = new AlertDialog.Builder(this);

        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!ubicacion.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            b.setMessage("ACTIVE EL GPS POR FAVOR");
            b.setCancelable(false);
            b.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            b.show();
        }
    }





    private void registrarLocalizacion() {
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        ubicacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, new milocalizacionListener());




    }
    private class milocalizacionListener implements LocationListener{


        @Override
        public void onLocationChanged(@NonNull Location location) {

            String lat = " "+location.getLatitude();
            String lon = " "+location.getLongitude();

            tvLong.setText(lat);
            tvLat.setText(lon);
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault()); // covertir coordenadas en direccion
            try {
                List<Address> direccion = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                tvCity.setText(direccion.get(0).getAddressLine(0));

                String s = tvCity.getText().toString();

                Intent ii=new Intent(Inicio.this, Galeria.class);
                ii.putExtra("ciudad", s);


            } catch (IOException e) {
                e.printStackTrace();
            }



        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }


        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }





}