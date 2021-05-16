package com.dam.goality.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Partido implements Parcelable {

    private String id;
    private String local;
    private String imgLocal;
    private String visitante;
    private String imgVisitante;
    private String horaPartido;
    private String fechaPartido;
    private String estadio;

    public Partido() {
    }

    public Partido(String id, String local, String imgLocal, String visitante, String imgVisitante, String horaPartido, String fechaPartido, String estadio) {
        this.id = id;
        this.local = local;
        this.imgLocal = imgLocal;
        this.visitante = visitante;
        this.imgVisitante = imgVisitante;
        this.horaPartido = horaPartido;
        this.fechaPartido = fechaPartido;
        this.estadio = estadio;
    }

    protected Partido(Parcel in) {
        id = in.readString();
        local = in.readString();
        imgLocal = in.readString();
        visitante = in.readString();
        imgVisitante = in.readString();
        horaPartido = in.readString();
        fechaPartido = in.readString();
        estadio = in.readString();
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

    public String getImgLocal() {
        return imgLocal;
    }

    public void setImgLocal(String imgLocal) {
        this.imgLocal = imgLocal;
    }

    public String getVisitante() {
        return visitante;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    public String getImgVisitante() {
        return imgVisitante;
    }

    public void setImgVisitante(String imgVisitante) {
        this.imgVisitante = imgVisitante;
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
        dest.writeString(local);
        dest.writeString(imgLocal);
        dest.writeString(visitante);
        dest.writeString(imgVisitante);
        dest.writeString(horaPartido);
        dest.writeString(fechaPartido);
        dest.writeString(estadio);
    }
}
