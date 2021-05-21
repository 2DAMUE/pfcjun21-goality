package com.dam.goality.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Partido implements Parcelable {

    private String id;
    private String miEquipo;
    private String contrincante;
    private String imgMiEquipo;
    private String imgContrincante;
    private int golesMiEquipo;
    private int golesContrincante;
    private String condicion;
    private String horaPartido;
    private String fechaPartido;
    private String estadio;

    public Partido() {
    }

    public Partido(String id, String miEquipo, String contrincante, String imgMiEquipo, String imgContrincante, int golesMiEquipo, int golesContrincante, String condicion, String horaPartido, String fechaPartido, String estadio) {
        this.id = id;
        this.miEquipo = miEquipo;
        this.contrincante = contrincante;
        this.imgMiEquipo = imgMiEquipo;
        this.imgContrincante = imgContrincante;
        this.golesMiEquipo = golesMiEquipo;
        this.golesContrincante = golesContrincante;
        this.condicion = condicion;
        this.horaPartido = horaPartido;
        this.fechaPartido = fechaPartido;
        this.estadio = estadio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMiEquipo() {
        return miEquipo;
    }

    public void setMiEquipo(String miEquipo) {
        this.miEquipo = miEquipo;
    }

    public String getContrincante() {
        return contrincante;
    }

    public void setContrincante(String contrincante) {
        this.contrincante = contrincante;
    }

    public String getImgMiEquipo() {
        return imgMiEquipo;
    }

    public void setImgMiEquipo(String imgMiEquipo) {
        this.imgMiEquipo = imgMiEquipo;
    }

    public String getImgContrincante() {
        return imgContrincante;
    }

    public void setImgContrincante(String imgContrincante) {
        this.imgContrincante = imgContrincante;
    }

    public int getGolesMiEquipo() {
        return golesMiEquipo;
    }

    public void setGolesMiEquipo(int golesMiEquipo) {
        this.golesMiEquipo = golesMiEquipo;
    }

    public int getGolesContrincante() {
        return golesContrincante;
    }

    public void setGolesContrincante(int golesContrincante) {
        this.golesContrincante = golesContrincante;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
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

    protected Partido(Parcel in) {
        id = in.readString();
        miEquipo = in.readString();
        contrincante = in.readString();
        imgMiEquipo = in.readString();
        imgContrincante = in.readString();
        golesMiEquipo = in.readInt();
        golesContrincante = in.readInt();
        condicion = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(miEquipo);
        dest.writeString(contrincante);
        dest.writeString(imgMiEquipo);
        dest.writeString(imgContrincante);
        dest.writeInt(golesMiEquipo);
        dest.writeInt(golesContrincante);
        dest.writeString(condicion);
        dest.writeString(horaPartido);
        dest.writeString(fechaPartido);
        dest.writeString(estadio);
    }

    @Override
    public String toString() {
        return "Partido{" +
                "id='" + id + '\'' +
                ", miEquipo='" + miEquipo + '\'' +
                ", contrincante='" + contrincante + '\'' +
                ", imgMiEquipo='" + imgMiEquipo + '\'' +
                ", imgContrincante='" + imgContrincante + '\'' +
                ", golesMiEquipo=" + golesMiEquipo +
                ", golesContrincante=" + golesContrincante +
                ", condicion='" + condicion + '\'' +
                ", horaPartido='" + horaPartido + '\'' +
                ", fechaPartido='" + fechaPartido + '\'' +
                ", estadio='" + estadio + '\'' +
                '}';
    }
}
