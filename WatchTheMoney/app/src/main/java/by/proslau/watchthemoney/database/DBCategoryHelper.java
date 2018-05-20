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
    private static final String DB_NAME = "categoryDatabase";
    private static final int DB_VERSION = 1;

    private static final String CATEGORY_TABLE = "category";
    public static final String CATEGORY_COLUMN_ID = "_id";
    public static final String CATEGORY_COLUMN_NAME = "name";

    private static final String CATEGORY_TABLE_CREATE = "create table "
            + CATEGORY_TABLE + "(" + CATEGORY_COLUMN_ID
            + " integer primary key, " + CATEGORY_COLUMN_NAME + " text);";


    private static final String COSTS_TABLE = "costs";
    public static final String COSTS_COLUMN_ID = "_id";
    public static final String COSTS_COLUMN_MONEY = "money";
    public static final String COSTS_COLUMN_DATE = "cDate";
    public static final String COSTS_COLUMN_NOTE = "note";
    public static final String COSTS_COLUMN_CATEGORY = "category";

    private static final String COSTS_TABLE_CREATE = "create table "
            + COSTS_TABLE + "(" + COSTS_COLUMN_ID + " integer primary key autoincrement, "
            + COSTS_COLUMN_MONEY + " real, " + COSTS_COLUMN_DATE + " text, "
            + COSTS_COLUMN_NOTE + " text, " + COSTS_COLUMN_CATEGORY + " integer);";


    private final Context mContext;
    private DBCHelper dbcHelper;
    private SQLiteDatabase mDB;

    public DBCategoryHelper(Context context){
        mContext = context;
    }

    public void open(){
        dbcHelper = new DBCHelper(mContext, DB_NAME, null, DB_VERSION);
        mDB = dbcHelper.getWritableDatabase();
    }

    public void close(){
        if(dbcHelper != null){
            dbcHelper.close();
        }
    }

    public Cursor getCategoryData(){
        return mDB.query(CATEGORY_TABLE, null, null, null, null, null, null);
    }

    public Cursor getCosts(long categoryID){
        return mDB.query(COSTS_TABLE, null, COSTS_COLUMN_CATEGORY + " = " + categoryID,
                null, null, null, null);
    }

    public void addRec(Double money, String date, String note, int category){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COSTS_COLUMN_MONEY, money);
        contentValues.put(COSTS_COLUMN_DATE, date);
        contentValues.put(COSTS_COLUMN_NOTE, note);
        contentValues.put(COSTS_COLUMN_CATEGORY, category);
        mDB.insert(COSTS_TABLE, null, contentValues);
    }

    public void delRec(long id){
        mDB.delete(COSTS_TABLE, COSTS_COLUMN_ID + " = " + id, null);
    }

    /*public Cursor getInfoFromCategory(String table, String column, long categoryID){
        return mDB.query(table, null, column + " = " + categoryID,
                null, null,null, null);
    }*/



    private class DBCHelper extends SQLiteOpenHelper {
        public DBCHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            ContentValues contentValues = new ContentValues();
            String[] categories = new String[] {"Еда", "Дом", "Одежда и обувь", "Хобби",
            "Лекарства", "Интернет", "Образование", "Путешествия", "Телефонная связь", "Развлечения"};
            sqLiteDatabase.execSQL(CATEGORY_TABLE_CREATE);
            for(int i = 0; i < categories.length; i++){
                contentValues.put(CATEGORY_COLUMN_ID, i + 1);
                contentValues.put(CATEGORY_COLUMN_NAME, categories[i]);
                sqLiteDatabase.insert(CATEGORY_TABLE, null, contentValues);
            }

            sqLiteDatabase.execSQL(COSTS_TABLE_CREATE);

            String eatName = "milk";
            String eatDate = "today";
            Double eatMoney = 1.2;
            contentValues.clear();
            contentValues.put(COSTS_COLUMN_MONEY, eatMoney);
            contentValues.put(COSTS_COLUMN_DATE, eatDate);
            contentValues.put(COSTS_COLUMN_NOTE, eatName);
            contentValues.put(COSTS_COLUMN_CATEGORY, 1);
            sqLiteDatabase.insert(COSTS_TABLE, null, contentValues);

            /*sqLiteDatabase.execSQL(EAT_TABLE_CREATE);
            sqLiteDatabase.execSQL(HOME_TABLE_CREATE);
            sqLiteDatabase.execSQL(CLOTHING_TABLE_CREATE);
            sqLiteDatabase.execSQL(HOBBY_TABLE_CREATE);
            sqLiteDatabase.execSQL(MEDICAL_TABLE_CREATE);
            sqLiteDatabase.execSQL(INTERNET_TABLE_CREATE);
            sqLiteDatabase.execSQL(EDUCATION_TABLE_CREATE);
            sqLiteDatabase.execSQL(TRAVEL_TABLE_CREATE);
            sqLiteDatabase.execSQL(PHONE_TABLE_CREATE);
            sqLiteDatabase.execSQL(RELAX_TABLE_CREATE);*/
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}