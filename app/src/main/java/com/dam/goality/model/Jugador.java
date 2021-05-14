package com.dam.goality.model;

import android.os.Parcel;

public class Jugador extends Usuario {

    private String posicion;
    private double peso;
    private double estatura;
    private int dorsal;

    public Jugador(String id, String nombre, String apellidos, String fotoPerfilUrl, int edad, String fechaNacimiento, String nacionalidad, String posicion, double peso, double estatura, int dorsal) {
        super(id, nombre, apellidos, fotoPerfilUrl, edad, fechaNacimiento, nacionalidad);
        this.posicion = posicion;
        this.peso = peso;
        this.estatura = estatura;
        this.dorsal = dorsal;
    }

    public Jugador() {
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    private Jugador(Parcel in) {
        super(in);
        this.posicion = in.readString();
        this.peso = in.readDouble();
        this.estatura = in.readDouble();
        this.dorsal = in.readInt();
    }


    public static final Creator<Jugador> CREATOR = new Creator<Jugador>() {
        @Override
        public Jugador createFromParcel(Parcel in) {
            return new Jugador(in);
        }

        @Override
        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(posicion);
        dest.writeDouble(peso);
        dest.writeDouble(estatura);
        dest.writeInt(dorsal);
    }

    @Override
    public String toString() {
        return super.toString() + "Jugador{" +
                "posicion='" + posicion + '\'' +
                ", peso=" + peso +
                ", estatura=" + estatura +
                ", dorsal=" + dorsal +
                '}';
    }
}
