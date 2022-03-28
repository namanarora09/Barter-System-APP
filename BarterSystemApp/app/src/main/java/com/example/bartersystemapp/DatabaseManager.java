package com.example.bartersystemapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
