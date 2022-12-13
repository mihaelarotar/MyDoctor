package com.example.mydoctor2.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User implements Parcelable {

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "bloodPressure")
    private int bloodPressure;

    @ColumnInfo(name = "kg")
    private int kg;

    @ColumnInfo(name = "height")
    private int height;

    @ColumnInfo(name = "temperature")
    private int temperature;

    @ColumnInfo(name = "pulse")
    private int pulse;

    @ColumnInfo(name = "bodyIndex")
    private int bodyIndex;

    @ColumnInfo(name = "sex")
    private Sex sex;

    public User(@NonNull String username, @NonNull String email, @NonNull String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    protected User(Parcel in) {
        username = in.readString();
        email = in.readString();
        password = in.readString();
        bloodPressure = in.readInt();
        kg = in.readInt();
        height = in.readInt();
        temperature = in.readInt();
        pulse = in.readInt();
        bodyIndex = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public int getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getBodyIndex() {
        return bodyIndex;
    }
    public void setBodyIndex(int bmi) {
        this.bodyIndex = bmi;
    }
    public void setBodyIndex2(int kg, int height) {
        this.bodyIndex = kg/(height*height)*10000;
        System.out.println("kg=" + kg + "height=" + height + "rez = " + bodyIndex);
    }

    public void setBodyIndex3() {
        this.bodyIndex = 0;
    }
    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(String.valueOf(sex));
        dest.writeString(String.valueOf(bodyIndex));
        dest.writeString(String.valueOf(kg));
        dest.writeString(String.valueOf(height));
        dest.writeString(String.valueOf(temperature));
        dest.writeString(String.valueOf(pulse));
        dest.writeString(String.valueOf(bodyIndex));

    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bloodPressure=" + bloodPressure +
                ", kg=" + kg +
                ", height=" + height +
                ", temperature=" + temperature +
                ", pulse=" + pulse +
                ", bodyIndex=" + bodyIndex +
                ", sex=" + sex +
                '}';
    }
}
