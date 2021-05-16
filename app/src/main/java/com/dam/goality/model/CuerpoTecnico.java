package com.dam.goality.model;

import android.os.Parcel;

public class CuerpoTecnico extends Usuario {

    private String cargo;
    private String role;

    public CuerpoTecnico(String id, String nombre, String apellidos, String fotoPerfilUrl, int edad, String fechaNacimiento, String nacionalidad, String cargo, String role) {
        super(id, nombre, apellidos, fotoPerfilUrl, edad, fechaNacimiento, nacionalidad);
        this.cargo = cargo;
        this.role = role;
    }

    public CuerpoTecnico() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public static Creator<CuerpoTecnico> getCREATOR() {
        return CREATOR;
    }

    private CuerpoTecnico(Parcel in) {
        super(in);
        this.cargo = in.readString();
        this.role = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cargo);
        dest.writeString(role);
    }
}
