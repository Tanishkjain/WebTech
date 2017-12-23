package com.example.windows.webtech.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.windows.webtech.model.BrandData;

import java.util.ArrayList;

/**
 * Created by Windows on 12/22/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "brandDetails";

    // Contacts table name
    private static final String TABLE_Brands = "brand";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DES = "description";
    private static final String KEY_CREATEDAT = "createdAt";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BRAND_TABLE = "CREATE TABLE " + TABLE_Brands + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DES + " TEXT," + KEY_CREATEDAT + " TEXT" + ")";
        db.execSQL(CREATE_BRAND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addBrand(BrandData brandData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, brandData.getBrandName()); // Brand Name
        values.put(KEY_DES, brandData.getDescreption()); // brandDescr.
        values.put(KEY_CREATEDAT, brandData.getCreatedAt());//createdTime
        // Inserting Row
        db.insert(TABLE_Brands, null, values);
        db.close(); // Closing database connection

    }

    // Getting All Details
     public ArrayList<BrandData> getAllDetails() {
        ArrayList<BrandData> brandtList = new ArrayList<BrandData>();
        // Select All Query
         brandtList.clear();
        String selectQuery = "SELECT  * FROM " + TABLE_Brands;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BrandData data = new BrandData();

                data.setId((cursor.getString(0)));
                data.setBrandName(cursor.getString(1));
                data.setDescreption(cursor.getString(2));
                data.setCreatedAt(cursor.getString(3));

                brandtList.add(data);
            } while (cursor.moveToNext());
        }

        // return  list
        return brandtList;
    }
}
