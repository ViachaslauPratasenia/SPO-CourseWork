package by.proslau.watchthemoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 09.05.2018.
 */

public class DBBirthdaysHelper {
    private static final String DB_NAME = "WTMDatabase";
    private static final int DB_BIRTHDAYS_VERSION = 1;
    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DBBirthdaysHelper(Context mContext){
        context = mContext;
    }

    public void open() {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_BIRTHDAYS_VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close(){
        if(dbHelper != null) dbHelper.close();
    }

    public Cursor getAllData(){
        return sqLiteDatabase.query(DBHelper.DB_BIRTHDAYS_TABLE, null, null, null, null, null, null);
    }

    public void addRec(String text, String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_BIRTHDAYS_NAME, text);
        contentValues.put(DBHelper.COLUMN_BIRTHDAYS_DATE, date);
        sqLiteDatabase.insert(DBHelper.DB_BIRTHDAYS_TABLE, null, contentValues);
    }

    public void delRec(long id){
        sqLiteDatabase.delete(DBHelper.DB_BIRTHDAYS_TABLE, DBHelper.COLUMN_BIRTHDAYS_ID + " = " + id, null);
    }
}
