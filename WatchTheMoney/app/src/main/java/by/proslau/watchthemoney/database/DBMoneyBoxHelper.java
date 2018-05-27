package by.proslau.watchthemoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 10.05.2018.
 */

public class DBMoneyBoxHelper {
    private static final String DB_NAME = "HomeAccountingDB";
    private static final int DB_MONEYBOX_VERSION = 1;

    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DBMoneyBoxHelper(Context mContext) {
        context = mContext;
    }

    public void open() {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_MONEYBOX_VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close(){
        if(dbHelper != null) dbHelper.close();
    }

    public Cursor getAllData(){
        return sqLiteDatabase.query(DBHelper.DB_MONEYBOX_TABLE, null, null, null, null, null, null);
    }

    public void addRec(double money, String date, String note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_MONEYBOX_MONEY, money);
        contentValues.put(DBHelper.COLUMN_MONEYBOX_DATE, date);
        contentValues.put(DBHelper.COLUMN_MONEYBOX_NOTE, note);
        sqLiteDatabase.insert(DBHelper.DB_MONEYBOX_TABLE, null, contentValues);
    }

    public Cursor getMoney(long id){
        return sqLiteDatabase.query(DBHelper.DB_MONEYBOX_TABLE, new String[]{DBHelper.COLUMN_MONEYBOX_MONEY},
                DBHelper.COLUMN_MONEYBOX_ID + " = " + id,
                null, null, null, null);
    }

    public void delRec(long id){
        sqLiteDatabase.delete(DBHelper.DB_MONEYBOX_TABLE, DBHelper.COLUMN_MONEYBOX_ID + " = " + id, null);
    }
}
