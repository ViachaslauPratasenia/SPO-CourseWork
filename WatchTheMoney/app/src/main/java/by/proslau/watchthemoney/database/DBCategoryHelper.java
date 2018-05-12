package by.proslau.watchthemoney.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 07.05.2018.
 */

public class DBCategoryHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "WTMDatabase";

    public DBCategoryHelper(Context context){
        super(context, DB_NAME, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*public static final String EAT = "eat";
    public static final String SPORT = "sport";
    public static final String CLOTHES = "clothes";
    public static final String GIFT = "gift";
    public static final String COMMUNICATION = "communication";
    public static final String TRANSPORT = "transport";
    public static final String MEDICINES = "medicines";

    private SQLiteDatabase db;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "categoryBD";
    public static final String TABLE_CATEGORY = "categorys";

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MONEY = "money";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_CATEGORY + "(" + KEY_ID
            + " integer primary key," + KEY_TITLE + " text," + KEY_MONEY + " integer" + ")");
        sqLiteDatabase.execSQL("insert into " + TABLE_CATEGORY + "(" + KEY_ID + ", " + KEY_TITLE + ","
                + KEY_MONEY + ") values (" + "1" + ", " + EAT + ", " + "0" + ")");
        sqLiteDatabase.execSQL("insert into " + TABLE_CATEGORY + "(" + KEY_ID + ", " + KEY_TITLE + ","
                + KEY_MONEY + ") values (" + "2" + ", " + SPORT + ", " + "0" + ")");
        sqLiteDatabase.execSQL("insert into " + TABLE_CATEGORY + "(" + KEY_ID + ", " + KEY_TITLE + ","
                + KEY_MONEY + ") values (" + "3" + ", " + CLOTHES + ", " + "0" + ")");
        sqLiteDatabase.execSQL("insert into " + TABLE_CATEGORY + "(" + KEY_ID + ", " + KEY_TITLE + ","
                + KEY_MONEY + ") values (" + "4" + ", " + GIFT + ", " + "0" + ")");
        sqLiteDatabase.execSQL("insert into " + TABLE_CATEGORY + "(" + KEY_ID + ", " + KEY_TITLE + ","
                + KEY_MONEY + ") values (" + "5" + ", " + COMMUNICATION + ", " + "0" + ")");
        sqLiteDatabase.execSQL("insert into " + TABLE_CATEGORY + "(" + KEY_ID + ", " + KEY_TITLE + ","
                + KEY_MONEY + ") values (" + "6" + ", " + TRANSPORT + ", " + "0" + ")");
        sqLiteDatabase.execSQL("insert into " + TABLE_CATEGORY + "(" + KEY_ID + ", " + KEY_TITLE + ","
                + KEY_MONEY + ") values (" + "7" + ", " + MEDICINES + ", " + "0" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_CATEGORY);
        onCreate(sqLiteDatabase);
    }

    public List<String> selectAll() {
        List<String> list = new ArrayList<String>();
        Cursor cursor = this.db.query(TABLE_CATEGORY, new String[] { "name" },
                null, null, null, null, "name desc");

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;*/
}
