package com.dam.goality.model;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Usuario implements Parcelable {

    private String id;
    private String nombre;
    private String apellidos;
    private String fotoPerfilUrl;
    private int edad;
    private String fechaNacimiento;
    private String nacionalidad;

    protected Usuario(String id, String nombre, String apellidos, String fotoPerfilUrl, int edad, String fechaNacimiento, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fotoPerfilUrl = fotoPerfilUrl;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
    }

    protected Usuario() {}

    protected Usuario(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        fotoPerfilUrl = in.readString();
        edad = in.readInt();
        fechaNacimiento = in.readString();
        nacionalidad = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(fotoPerfilUrl);
        dest.writeInt(edad);
        dest.writeString(fechaNacimiento);
        dest.writeString(nacionalidad);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fotoPerfilUrl='" + fotoPerfilUrl + '\'' +
                ", edad=" + edad +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                '}';
    }
}

