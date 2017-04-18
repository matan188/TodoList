package com.example.sharonaharoni.todolist;

import java.io.Serializable;

/**
 * Created by sharonaharoni on 18/04/2017.
 */

public class Reminder implements Serializable {

    private String description;
    private int year;
    private int month;
    private int day;

    Reminder(String description, int year, int month, int day) {
        this.description = description;
        this.year = year;
        this.month = month;
        this.day = day;
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

}
