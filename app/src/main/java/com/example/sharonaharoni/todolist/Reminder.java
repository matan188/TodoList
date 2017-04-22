package com.example.sharonaharoni.todolist;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by sharonaharoni on 18/04/2017.
 */

public class Reminder implements Parcelable {

    private static int idCounter = 0;
    private int id;
    private String description;
    private int year;
    private int month;
    private int day;

    Reminder(String description, int year, int month, int day) {
        idCounter++;
        this.id = idCounter;
        this.description = description;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getDate() {
        return this.day + "/" + this.month + "/" + this.year;
    }

    Reminder(Parcel in) {
        description = in.readString();
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
    }

    public static final Parcelable.Creator<Reminder> CREATOR = new Parcelable.Creator<Reminder>() {
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
