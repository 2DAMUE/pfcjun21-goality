package com.dam.goality.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Partido implements Parcelable {

    private String id;
    private String local;
    private String visitante;
    private String horaPartido;
    private String fechaPartido;

    public Partido() {
    }

    public Partido(String id, String local, String visitante, String horaPartido, String fechaPartido) {
        this.id = id;
        this.local = local;
        this.visitante = visitante;
        this.horaPartido = horaPartido;
        this.fechaPartido = fechaPartido;
    }

    protected Partido(Parcel in) {
        id = in.readString();
        local = in.readString();
        visitante = in.readString();
        horaPartido = in.readString();
        fechaPartido = in.readString();
    }

    public static final Creator<Partido> CREATOR = new Creator<Partido>() {
        @Override
        public Partido createFromParcel(Parcel in) {
            return new Partido(in);
        }

        @Override
        public Partido[] newArray(int size) {
            return new Partido[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVisitante() {
        return visitante;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    public String getHoraPartido() {
        return horaPartido;
    }

    public void setHoraPartido(String horaPartido) {
        this.horaPartido = horaPartido;
    }

    public String getFechaPartido() {
        return fechaPartido;
    }

    public void setFechaPartido(String fechaPartido) {
        this.fechaPartido = fechaPartido;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(local);
        dest.writeString(visitante);
        dest.writeString(horaPartido);
        dest.writeString(fechaPartido);
    }
}
