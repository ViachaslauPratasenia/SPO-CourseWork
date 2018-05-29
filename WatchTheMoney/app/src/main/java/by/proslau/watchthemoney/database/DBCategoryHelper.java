package by.proslau.watchthemoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 07.05.2018.
 */

/*
    1 - eat
    2 - home
    3 - clothing
    4 - hobby
    5 - medical
    6 - internet
    7 - education
    8 - travel
    9 - phone
    10 - relax
*/

public class DBCategoryHelper {
    private static final String DB_NAME = "HomeAccountingDB";
    private static final int DB_VERSION = 1;
    private final Context mContext;
    private DBHelper dbcHelper;
    private SQLiteDatabase mDB;

    public DBCategoryHelper(Context context){
        mContext = context;
    }

    public void open(){
        dbcHelper = new DBHelper(mContext, DB_NAME, null, DB_VERSION);
        mDB = dbcHelper.getWritableDatabase();
    }

    public void close(){
        if(dbcHelper != null){
            dbcHelper.close();
        }
    }

    public Cursor getCategoryData(){
        return mDB.query(DBHelper.CATEGORY_TABLE, null,
                null, null,null, null, null);
    }

    public Cursor getMoney(long id){
        return mDB.query(DBHelper.COSTS_TABLE, new String[]{DBHelper.COSTS_COLUMN_MONEY},
                DBHelper.COSTS_COLUMN_ID + " = " + id, null, null,
                null, null);
    }

    public Cursor getCosts(long categoryID){
        return mDB.query(DBHelper.COSTS_TABLE, null, DBHelper.COSTS_COLUMN_CATEGORY +
                " = " + categoryID, null, null, null, null);
    }

    public void addRec(Double money, String date, String note, int category){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COSTS_COLUMN_MONEY, money);
        contentValues.put(DBHelper.COSTS_COLUMN_DATE, date);
        contentValues.put(DBHelper.COSTS_COLUMN_NOTE, note);
        contentValues.put(DBHelper.COSTS_COLUMN_CATEGORY, category);
        mDB.insert(DBHelper.COSTS_TABLE, null, contentValues);
    }

    public void delRec(long id){
        mDB.delete(DBHelper.COSTS_TABLE, DBHelper.COSTS_COLUMN_ID + " = " + id, null);
    }
}