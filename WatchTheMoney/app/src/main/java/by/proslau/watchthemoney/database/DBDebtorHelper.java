package by.proslau.watchthemoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;

/**
 * Created by user on 07.05.2018.
 */

public class DBDebtorHelper {
    private static final String DB_NAME = "database";
    private static final int DB_VERSION = 1;
    /*final String LOG_TAG = "myLogs";

    private static final String DB_NAME = "myDatabase";
    private static final String DB_TABLE = "arrears";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MONEY = "money";
    public static final String COLUMN_NAME = "name";

    //Переменная, которая хранит int как boolean. Если 0 - человек должен вам, если 1 - должны вы
    public static final String COLUMN_CHOISE = "choise";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_MONEY + " integer, " +
                    COLUMN_CHOISE + " integer" + ");";*/

    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DBDebtorHelper(Context mContext){
        context = mContext;
    }

    public void open() {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close(){
        if(dbHelper != null) dbHelper.close();
    }

    public Cursor getAllData(){
        return sqLiteDatabase.query(DBHelper.DB_DEPTOR_TABLE, null, null, null, null, null, null);
    }

    public Cursor getData(long num){
        return sqLiteDatabase.query(DBHelper.DB_DEPTOR_TABLE, null, "choise = " + num, null, null, null, null);
    }

    public void addRec(String text, double money, String check){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_DEPTOR_NAME, text);
        contentValues.put(DBHelper.COLUMN_DEPTOR_MONEY, money);
        contentValues.put(DBHelper.COLUMN_DEPTOR_CHOISE, check);
        sqLiteDatabase.insert(DBHelper.DB_DEPTOR_TABLE, null, contentValues);
    }

    public void delRec(long id){
        sqLiteDatabase.delete(DBHelper.DB_DEPTOR_TABLE, DBHelper.COLUMN_DEPTOR_ID + " = " + id, null);
    }


    /*private class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "===========");
            db.execSQL("create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_MONEY + " integer, " +
                    COLUMN_CHOISE + " integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }*/
}
