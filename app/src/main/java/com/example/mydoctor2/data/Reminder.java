package com.example.mydoctor2.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "remainder")
public class Reminder implements Parcelable {

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "idRemainder")
    public String idRemainder;

    @NonNull
    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "detalii")
    private String detalii;


    @ColumnInfo(name = "data")
    private Date date;

    public Reminder(@NonNull String idRemainder, @NonNull String username, String detalii, Date date) {
        this.idRemainder = idRemainder;
        this.username = username;
        this.detalii = detalii;
        this.date = date;
    }

    protected Reminder(Parcel in) {
        idRemainder = in.readString();
        username = in.readString();
        detalii = in.readString();
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    @NonNull
    public String getIdRemainder() {
        return idRemainder;
    }

    public void setIdRemainder(@NonNull String idRemainder) {
        this.idRemainder = idRemainder;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idRemainder);
        parcel.writeString(username);
        parcel.writeString(detalii);
    }
}
