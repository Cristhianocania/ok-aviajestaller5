package com.example.taller5.data;

public class Usuario {

    int Id;
    String Nombre,Apellido,Usuario,Password;

    public Usuario() {

    }

    public Usuario(String nombre, String apellido, String usuario, String password) {
        Nombre = nombre;
        Apellido = apellido;
        Usuario = usuario;
        Password = password;
    }//metodos constructores


    public boolean isNull() { //valida campos vacios

        return !Nombre.equals("") || !Apellido.equals("") || !Usuario.equals("") || !Password.equals("");
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "Id=" + Id +
                ", Nombre='" + Nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                ", Usuario='" + Usuario + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
