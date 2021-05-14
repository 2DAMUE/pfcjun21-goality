package com.dam.goality.model;

import android.os.Parcel;

public class CuerpoTecnico extends Usuario {

    private String cargo;

    public CuerpoTecnico(String id, String nombre, String apellidos, String fotoPerfilUrl, int edad, String fechaNacimiento, String nacionalidad, String cargo) {
        super(id, nombre, apellidos, fotoPerfilUrl, edad, fechaNacimiento, nacionalidad);
        this.cargo = cargo;
    }

    public CuerpoTecnico() {}

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
    }
}
