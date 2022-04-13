package com.example.bartersystemapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.execSQL("create Table "+table1+"(Name TEXT,Phone TEXT,Email TEXT PRIMARY KEY,Zip_code TEXT,Password TEXT)");
        db.execSQL("create Table "+table2+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,Email TEXT ,Title TEXT ,Category TEXT ,Description TEXT ,Image TEXT)");
        db.execSQL("create Table "+table3+"(ID TEXT,Title TEXT ,Ad_owner TEXT ,Customer TEXT ,Offer TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //deleting tables
        db.execSQL("DROP TABLE IF EXISTS "+table1);
        db.execSQL("DROP TABLE IF EXISTS "+table2);
        db.execSQL("DROP TABLE IF EXISTS "+table3);
    }

    public Boolean insertLogin(String Name,String Phone,String Email,String Zip_code,String Password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Name",Name);
        values.put("Phone",Phone);
        values.put("Email",Email);
        values.put("Zip_code",Zip_code);
        values.put("Password",Password);
        long result=db.insert(table1,null,values);
        if(result==1)
            return false;
        else
            return true;
    }

    public Boolean checkuser(String Email,String Password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String[] columns={"Name"};
        String selection="Email =?"+" and "+"Password=?";
        String[] selectionargs={Email,Password};
        Cursor cursor=db.query(table1,columns,selection,selectionargs,null,null,null);
        int count= cursor.getCount();
        if(count>0)
            return true;
        else
            return false;
    }

    public Boolean insertAds(String Email,String Title,String Category,String Description,String Image)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Email",Email);
        contentValues.put("Title",Title);
        contentValues.put("Category",Category);
        contentValues.put("Description",Description);
        contentValues.put("Image",Image);
        long result=db.insert(table2,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public void deleteAds(String Title)
    {
        SQLiteDatabase db=getWritableDatabase();
        String sQuery="delete from "+table2+" where Title='"+Title+"'";
        db.execSQL(sQuery);
    }


    Cursor readAds(){
        String query="SELECT * FROM "+table2+" where Email!='"+LoginPage.emailId+ "'";
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    public Boolean insertOffer(String ID,String Title,String Ad_owner,String Customer,String Offer)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ID",ID);
        contentValues.put("Title",Title);
        contentValues.put("Ad_owner",Ad_owner);
        contentValues.put("Customer",Customer);
        contentValues.put("Offer",Offer);
        long result=db.insert(table3,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public void deleteOffers(String Title)
    {
        SQLiteDatabase db = getWritableDatabase ();
        String sQuery = "delete from " + table3 + " where Title='" + Title + "'";
        db.execSQL(sQuery);
    }

    Cursor readOffers(){
        String query="SELECT * FROM "+table3+" where Ad_owner='"+LoginPage.emailId+ "'";
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

}
