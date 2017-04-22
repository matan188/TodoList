package com.example.sharonaharoni.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by sharonaharoni on 22/04/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todolist.db";
    private static final String REMINDERS = "REMINDERS";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + REMINDERS + " ( ID INTEGER PRIMARY KEY," +
                "DESCRIPTION TEXT," +
                "DAY TEXT, " +
                "MONTH TEXT, " +
                "YEAR TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS REMINDERS;");
        onCreate(db);
    }

    public void insertReminder(Reminder reminder) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", reminder.getId());
        contentValues.put("DESCRIPTION", reminder.getDescription());
        contentValues.put("DAY", reminder.getDay());
        contentValues.put("MONTH", reminder.getMonth());
        contentValues.put("YEAR", reminder.getYear());
        this.getWritableDatabase().insertOrThrow(REMINDERS, "", contentValues);
    }

    public void  deleteReminder(Reminder reminder) {
        this.getWritableDatabase().delete(REMINDERS, "ID='" + reminder.getId() + "'", null);
    }

    public ArrayList<Reminder> allRemindrs() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + REMINDERS, null);
        ArrayList<Reminder> allReminders = new ArrayList<>();
        while (cursor.moveToNext()) {
            String desc = cursor.getString(1);
            int day = Integer.parseInt(cursor.getString(2));
            int month= Integer.parseInt(cursor.getString(3));
            int year = Integer.parseInt(cursor.getString(4));
            allReminders.add(new Reminder(desc, year, month, day));
        }
        return allReminders ;
    }
}
