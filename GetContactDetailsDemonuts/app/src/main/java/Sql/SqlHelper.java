package Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " TEXT, "
                + COL_6 + " TEXT, " + COL_7 + " TEXT, " + COL_8 + " INTEGER)";
        sqLiteDatabase.execSQL(create_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean create_contact(String name, String phone, String email, String state, String city,
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

    public int find_contact(String phone){
        SQLiteDatabase db = this.getWritableDatabase();

        String select = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_3 + "='" + phone +"'";

        Log.d(TAG, "findContact: " +  phone);

        Cursor result = db.rawQuery(select, null);

        if(result != null)
            return 0;
        else
            return 1;

    }

    public void update_contact(String name, String phone, String email, String state, String city,
                                  String street, String code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String update = "UPDATE " + TABLE_NAME + " SET " + COL_2 + "='" + name + "' AND "
                + COL_3 + "=" + phone + " AND " + COL_4 + "='" + email + "' AND "
                + COL_5 + "='" + state + "' AND " + COL_6 + "='" + city + "' AND "
                + COL_7 + "='" + street + "' AND " + COL_8 + "='" + code +  "' WHERE "
                + COL_3 + "='" + phone +"'";


        Log.d(TAG, "updateContact: " +  phone);

        db.execSQL(update);

    }

    public void delete_contact(String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String update = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_3 + "='" + phone +"'";


        Log.d(TAG, "deleteContact: " +  phone);

        db.execSQL(update);

    }

    public JSONArray getData() throws JSONException {

        JSONArray contacts = new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{COL_1, COL_2, COL_3, COL_4, COL_4, COL_5, COL_6, COL_7, COL_8};
        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
        String result = "";

        int iRow = c.getColumnIndex(COL_1);
        int iName = c.getColumnIndex(COL_2);
        int iphone = c.getColumnIndex(COL_3);
        int iemail = c.getColumnIndex(COL_4);
        int istate = c.getColumnIndex(COL_5);
        int icity = c.getColumnIndex(COL_6);
        int istreet = c.getColumnIndex(COL_7);
        int icode = c.getColumnIndex(COL_8);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            JSONObject contactList = new JSONObject();
            contactList.put("id", c.getString(iRow));
            contactList.put("name", c.getString(iName));
            contactList.put("phone", c.getString(iphone));
            contactList.put("email", c.getString(iemail));
            contactList.put("state", c.getString(istate));
            contactList.put("city", c.getString(icity));
            contactList.put("street", c.getString(istreet));
            contactList.put("code", c.getString(icode));
            contacts.put(Integer.parseInt(c.getString(iRow)), contactList);

        }

        return contacts;
    }


}
