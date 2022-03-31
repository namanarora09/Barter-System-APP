package com.example.bartersystemapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseManager extends SQLiteOpenHelper {

    //Initialize Variable
    public static final String DATABASE_NAME="barter_database";
    public static final String table1="Login";
    public static final String table2="Ads";
    public static final String table3="Offer";

    public DatabaseManager(Context context) {

        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating tables
        db.execSQL("create Table "+table1+"(Name TEXT not null,Phone TEXT not null,Email TEXT PRIMARY KEY,Zip_code TEXT not null,Password TEXT not null)");
        db.execSQL("create Table "+table2+"(Id INTEGER PRIMARY KEY AUTOINCREMENT,Email TEXT not null,Title TEXT not null,Category TEXT not null,Description TEXT not null,Image blob not null)");
        db.execSQL("create Table "+table3+"(Id INTEGER not null,Ad_owner TEXT not null,Customer TEXT not null,Offer TEXT not null,Mobile TEXT not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //deleting tables
        db.execSQL("DROP TABLE IF EXISTS "+table1);
        db.execSQL("DROP TABLE IF EXISTS "+table2);
        db.execSQL("DROP TABLE IF EXISTS "+table3);
    }

    public Boolean insertOffer(Integer Id,String Ad_owner,String Customer,String Offer,String Mobile)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Id",Id);
        contentValues.put("Ad_owner",Ad_owner);
        contentValues.put("Customer",Customer);
        contentValues.put("Offer",Offer);
        contentValues.put("Mobile",Mobile);
        long result=db.insert(table3,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
}
