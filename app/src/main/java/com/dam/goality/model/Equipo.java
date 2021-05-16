package com.dam.goality.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipo implements Parcelable {

    private String id;
    private String imageUrl;
    private String nombre;
    private String estadio;

    public Equipo() {
    }

    public Equipo(String id, String imageUrl, String nombre, String estadio) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.nombre = nombre;
        this.estadio = estadio;
    }

    protected Equipo(Parcel in) {
        id = in.readString();
        imageUrl = in.readString();
        nombre = in.readString();
        estadio = in.readString();
    }

    public static final Creator<Equipo> CREATOR = new Creator<Equipo>() {
        @Override
        public Equipo createFromParcel(Parcel in) {
            return new Equipo(in);
        }

        @Override
        public Equipo[] newArray(int size) {
            return new Equipo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(imageUrl);
        dest.writeString(nombre);
        dest.writeString(estadio);
    }
}
