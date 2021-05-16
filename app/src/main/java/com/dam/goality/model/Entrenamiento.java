package com.dam.goality.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Entrenamiento implements Parcelable {

    private String id;
    private String horaEntrenamiento;
    private String fechaEntrenamiento;

    public Entrenamiento(String id, String horaEntrenamiento, String fechaEntrenamiento) {
        this.id = id;
        this.horaEntrenamiento = horaEntrenamiento;
        this.fechaEntrenamiento = fechaEntrenamiento;
    }

    public Entrenamiento() {
    }

    protected Entrenamiento(Parcel in) {
        id = in.readString();
        horaEntrenamiento = in.readString();
        fechaEntrenamiento = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(horaEntrenamiento);
        dest.writeString(fechaEntrenamiento);
    }

    @Override
    public String toString() {
        return "Entrenamiento{" +
                "id='" + id + '\'' +
                ", horaEntrenamiento='" + horaEntrenamiento + '\'' +
                ", fechaEntrenamiento='" + fechaEntrenamiento + '\'' +
                '}';
    }
}
