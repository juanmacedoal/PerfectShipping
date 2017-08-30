package com.exampledemo.parsaniahardik.getcontactdetailsdemonuts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by juanmacedo on 30/8/17.
 */

public class SqlHelper extends SQLiteOpenHelper {

    private static final String TAG = "DATABASE";
    private static final String TABLE_NAME = "contact_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "PHONE";
    private static final String COL_4 = "EMAIL";
    private static final String COL_5 = "STATE";
    private static final String COL_6 = "CITY";
    private static final String COL_7 = "STREET";
    private static final String COL_8 = "CODE";


    public SqlHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " TEXT, " + COL_3 + " INTEGER, " + COL_4 + " TEXT, " + COL_5 + " TEXT, "
                + COL_6 + " TEXT, " + COL_7 + " TEXT, " + COL_8 + " INTEGER)";
        sqLiteDatabase.execSQL(create_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean create_contact(String name, int phone, String email, String state, String city,
                                  String street, String code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, phone);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, state);
        contentValues.put(COL_6, city);
        contentValues.put(COL_7, street);
        contentValues.put(COL_8, code);

        Log.d(TAG, "addContact: " + name + " " + phone + " " + email + " "
                                  + state + " " + city + " " + street + " " + code);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result != -1)
            return true;
        else
            return false;

    }

    public boolean find_contact(int phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String select = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_3 + "=" + phone +")";

        Log.d(TAG, "findContact: " +  phone);

        Cursor result = db.rawQuery(select, null);

        if(result != null)
            return true;
        else
            return false;

    }
}
