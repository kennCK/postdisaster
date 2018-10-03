package com.httpsgocentralph.post_disaster.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.httpsgocentralph.post_disaster.Entity.Helper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, Helper.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + Helper.TB_HOUSEHOLDS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, first_name text, last_name text, age text, mobile_number text, " +
                "address text, gender text, civil_status text, type text, relation text, status text)");

        sqLiteDatabase.execSQL("create table " + Helper.TB_CALAMITIES +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, name text, date text, damage_status text, damage_amount text, status text)");

        sqLiteDatabase.execSQL("create table " + Helper.TB_CALAMITY_NAMES +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, calamity_id INTEGER, household_name_id, status text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Helper.TB_HOUSEHOLDS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Helper.TB_CALAMITIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Helper.TB_CALAMITY_NAMES);
        onCreate(sqLiteDatabase);
    }

    public boolean insert(String table, ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(table, null, contentValues);
        return  (result == -1) ? false : true;
    }

    public Cursor retrieve(String table, String condition){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + table, null);
        return res;
    }
}
