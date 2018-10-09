package com.httpsgocentralph.post_disaster.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.httpsgocentralph.post_disaster.Entity.Helper;

import java.io.File;

public class DatabaseHelper extends SQLiteOpenHelper {
    Context context;
    public DatabaseHelper(Context context) {
        super(context, Helper.DB_NAME, null, 1);
        this.context = context;
//        SQLiteDatabase db = this.getWritableDatabase();
//        onUpgrade(db, 1,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + Helper.TB_ACCOUNTS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, first_name text, last_name text, username text UNIQUE, password text, created_at timestamp, updated_at timestamp, deleted_at timestamp)");

        sqLiteDatabase.execSQL("create table " + Helper.TB_HOUSEHOLDS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, account_id INTEGER, under INTEGER, first_name text, last_name text, age text, mobile_number text, " +
                "address text, gender text, civil_status text, type text, relation text, status text,created_at timestamp, updated_at timestamp, deleted_at timestamp)");

        sqLiteDatabase.execSQL("create table " + Helper.TB_CALAMITIES +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, account_id INTEGER, name text, date text, damage_status text, damage_amount text, status text, created_at timestamp, updated_at timestamp, deleted_at timestamp)");

        sqLiteDatabase.execSQL("create table " + Helper.TB_CALAMITY_NAMES +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, calamity_id INTEGER, under INTEGER, household_name_id, status text, created_at timestamp, updated_at timestamp, deleted_at timestamp)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Helper.TB_ACCOUNTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Helper.TB_HOUSEHOLDS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Helper.TB_CALAMITIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Helper.TB_CALAMITY_NAMES);
        onCreate(sqLiteDatabase);
    }

    public long insert(String table, ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(table, null, contentValues);
        return result;
    }

    public boolean update(String table, ContentValues contentValues, String clause, String[] args){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.update(table, contentValues, clause, args);
        return  (result == -1) ? false : true;
    }

    public Cursor retrieve(String table, String condition, String sort){
        SQLiteDatabase db = this.getWritableDatabase();
        String extension = (sort.equals("")) ? "" : " ORDER BY " + sort;
        String cond = (condition.equals("")) ? "" : " AND " + condition;
        Cursor res = db.rawQuery("select * from " + table + " WHERE (deleted_at = null OR deleted_at = '')" + cond + extension, null);
        return res;
    }

    public void reset(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 1,1);
    }

    public void create(){
        SQLiteDatabase db = this.getWritableDatabase();
        File dbFile = context.getDatabasePath(Helper.DB_NAME);
        if(!dbFile.exists()){
            onCreate(db);
        }
    }
}
