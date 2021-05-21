package com.dam.goality.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Entrenamiento implements Parcelable {

    private String id;
    private String horaEntrenamiento;
    private String fechaEntrenamiento;
    private int duracion;
    private String lugar;
    private String descripcion;

    public Entrenamiento(String id, String horaEntrenamiento, String fechaEntrenamiento, int duracion, String lugar, String descripcion) {
        this.id = id;
        this.horaEntrenamiento = horaEntrenamiento;
        this.fechaEntrenamiento = fechaEntrenamiento;
        this.duracion = duracion;
        this.lugar = lugar;
        this.descripcion = descripcion;
    }

    public Entrenamiento() {
    }

    protected Entrenamiento(Parcel in) {
        id = in.readString();
        horaEntrenamiento = in.readString();
        fechaEntrenamiento = in.readString();
        duracion = in.readInt();
        lugar = in.readString();
        descripcion = in.readString();
    }

    public static final Creator<Entrenamiento> CREATOR = new Creator<Entrenamiento>() {
        @Override
        public Entrenamiento createFromParcel(Parcel in) {
            return new Entrenamiento(in);
        }

        @Override
        public Entrenamiento[] newArray(int size) {
            return new Entrenamiento[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoraEntrenamiento() {
        return horaEntrenamiento;
    }

    public void setHoraEntrenamiento(String horaEntrenamiento) {
        this.horaEntrenamiento = horaEntrenamiento;
    }

    public String getFechaEntrenamiento() {
        return fechaEntrenamiento;
    }

    public void setFechaEntrenamiento(String fechaEntrenamiento) {
        this.fechaEntrenamiento = fechaEntrenamiento;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(horaEntrenamiento);
        dest.writeString(fechaEntrenamiento);
        dest.writeInt(duracion);
        dest.writeString(lugar);
        dest.writeString(descripcion);
    }
}
