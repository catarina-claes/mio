package com.example.mio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "miodb";
    public static final String TABLE_NAME = "miochan";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //creating the table if not exists
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "name TEXT PRIMARY KEY, "
                + "cla TEXT, "
                + "time TEXT, "
                + "task TEXT);";
        db.execSQL(query);
    }

    //adding tasks to the table
    public void addTask(String name, String cla, String time, String task) {

        //getting the database
        SQLiteDatabase db =  this.getWritableDatabase();

        //getting the ContentValue and setting it
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("cla", cla);
        values.put("time", time);
        values.put("task", task);

        //inserting the ContentValue to the database and closing it
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //reading the tasks
    public ArrayList<String[]> readTask() {

        //getting the database
        SQLiteDatabase db = this.getReadableDatabase();

        //getting the cursor
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME, null);

        //creating List and adding the tasks to it
        ArrayList<String[]> itemList = new ArrayList<>();
        while(cursor.moveToNext()) {
            String[] item = {cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)};
            itemList.add(item);
        }
        cursor.close();

        //returning the List
        return itemList;
    }

    //Deleting the task
    public void deleteTask(String name, String clas, boolean metode) {

        //getting the database
        SQLiteDatabase db = this.getWritableDatabase();

        //simple delete it then close the database
        db.delete(TABLE_NAME,     "name = '" + name + "'", null);
        db.close();

        //getting the value of class
        int con = 0;
        switch (clas) {
            case "D":
                con = 30;
                break;
            case "C":
                con = 80;
                break;
            case "B":
                con = 145;
                break;
            case "A":
                con = 230;
                break;
            case "S":
                con = 345;
                break;
            default:
        }

        //change the point
        if (metode) {
            MainActivity.editor.putInt("point", MainActivity.sharedPref.getInt("point",0) + (con * 2));
            MainActivity.editor.putInt(clas, MainActivity.sharedPref.getInt(clas,0) + 1);
        } else  {
            MainActivity.editor.putInt("point", MainActivity.sharedPref.getInt("point",0) - con);
        }
        MainActivity.editor.apply();

    }

    //onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
