package com.example.taller5.data;

public class Foto {
     int id;
        int idUsuario;
     String fotouri;
     String descripcion;
     String direccion;


    public Foto(int id) {
        this.id = id;
    }

    public Foto() {

    }

    public boolean isNullF() { //valida campos vacios

        return fotouri.equals("") ;
    }

    @Override
    public String toString() {
        return "Foto{" +
                "id='" + id + '\'' +
                ", foto='" + fotouri + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto() {
        return fotouri;
    }

    public void setFoto(String foto) {
        this.fotouri = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getidUsu() {
        return idUsuario;
    }

    public void setidUsu(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
