package com.dam.goality.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Password implements Parcelable {

    private String id;
    private String password;

    public Password() {
    }

    public Password(String id, String password) {
        this.id = id;
        this.password = password;
    }

    protected Password(Parcel in) {
        id = in.readString();
        password = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static final Creator<Password> CREATOR = new Creator<Password>() {
        @Override
        public Password createFromParcel(Parcel in) {
            return new Password(in);
        }

        @Override
        public Password[] newArray(int size) {
            return new Password[size];
        }
    };

    @Override
    public String toString() {
        return "Password{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(password);
    }
}
