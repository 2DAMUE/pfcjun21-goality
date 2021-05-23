package com.dam.goality.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CuerpoTecnico implements Parcelable {

    private String id;
    private String nombre;
    private String apellidos;
    private String fotoPerfilUrl;
    private int edad;
    private String fechaNacimiento;
    private String nacionalidad;
    private String cargo;
    private String role;

    public CuerpoTecnico() {
    }

    public CuerpoTecnico(String id, String nombre, String apellidos, String fotoPerfilUrl, int edad, String fechaNacimiento, String nacionalidad, String cargo, String role) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fotoPerfilUrl = fotoPerfilUrl;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.cargo = cargo;
        this.role = role;
    }

    protected CuerpoTecnico(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        fotoPerfilUrl = in.readString();
        edad = in.readInt();
        fechaNacimiento = in.readString();
        nacionalidad = in.readString();
        cargo = in.readString();
        role = in.readString();
    }

    public static final Creator<CuerpoTecnico> CREATOR = new Creator<CuerpoTecnico>() {
        @Override
        public CuerpoTecnico createFromParcel(Parcel in) {
            return new CuerpoTecnico(in);
        }

        @Override
        public CuerpoTecnico[] newArray(int size) {
            return new CuerpoTecnico[size];
        }
    };

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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
        dest.writeString(cargo);
        dest.writeString(role);
    }
}
