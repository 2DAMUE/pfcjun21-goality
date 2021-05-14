package com.dam.goality.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Map;

public class Equipo implements Parcelable {

    private String id;
    private String nombre;
    private String genero;
    private String rangoEdad;
    private Map<String, String> idJugadores;

    public Equipo(String id, String nombre, String genero, String rangoEdad, Map<String, String> idJugadores) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.rangoEdad = rangoEdad;
        this.idJugadores = idJugadores;
    }

    public Equipo(String id) {
        this.id = id;
    }

    public Equipo() {
    }

    protected Equipo(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        genero = in.readString();
        rangoEdad = in.readString();
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRangoEdad() {
        return rangoEdad;
    }

    public void setRangoEdad(String rangoEdad) {
        this.rangoEdad = rangoEdad;
    }

    public Map<String, String> getIdJugadores() {
        return idJugadores;
    }

    public void setIdJugadores(Map<String, String> idJugadores) {
        this.idJugadores = idJugadores;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(genero);
        dest.writeString(rangoEdad);
    }
}
