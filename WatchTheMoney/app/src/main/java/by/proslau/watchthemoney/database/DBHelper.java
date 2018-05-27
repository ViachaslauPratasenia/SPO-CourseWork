package by.proslau.watchthemoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 09.05.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_BIRTHDAYS_TABLE = "birthdays";
    public static final String COLUMN_BIRTHDAYS_ID = "_id";
    public static final String COLUMN_BIRTHDAYS_NAME = "name";
    public static final String COLUMN_BIRTHDAYS_DATE = "bDate";

    public static final String DB_DEPTOR_TABLE = "deptors";
    public static final String COLUMN_DEPTOR_ID = "_id";
    public static final String COLUMN_DEPTOR_MONEY = "money";
    public static final String COLUMN_DEPTOR_NAME = "name";
    public static final String COLUMN_DEPTOR_CHOISE = "choise";

    private static final String DB_CREATE_DEPTORS =
            "create table " + DB_DEPTOR_TABLE + "(" +
                    COLUMN_DEPTOR_ID + " integer primary key autoincrement, " +
                    COLUMN_DEPTOR_NAME + " text, " +
                    COLUMN_DEPTOR_MONEY + " real, " +
                    COLUMN_DEPTOR_CHOISE + " text" + ");";

    private static final String DB_CREATE_BIRTHDAYS = "create table " + DB_BIRTHDAYS_TABLE + "(" +
            COLUMN_BIRTHDAYS_ID + " integer primary key autoincrement, " +
            COLUMN_BIRTHDAYS_NAME + " text, " +
            COLUMN_BIRTHDAYS_DATE + " text" + ");";

    public static final String CATEGORY_TABLE = "category";
    public static final String CATEGORY_COLUMN_ID = "_id";
    public static final String CATEGORY_COLUMN_NAME = "name";

    private static final String CATEGORY_TABLE_CREATE = "create table "
            + CATEGORY_TABLE + "(" + CATEGORY_COLUMN_ID
            + " integer primary key, " + CATEGORY_COLUMN_NAME + " text);";


    public static final String COSTS_TABLE = "costs";
    public static final String COSTS_COLUMN_ID = "_id";
    public static final String COSTS_COLUMN_MONEY = "money";
    public static final String COSTS_COLUMN_DATE = "cDate";
    public static final String COSTS_COLUMN_NOTE = "note";
    public static final String COSTS_COLUMN_CATEGORY = "category";

    private static final String COSTS_TABLE_CREATE = "create table "
            + COSTS_TABLE + "(" + COSTS_COLUMN_ID + " integer primary key autoincrement, "
            + COSTS_COLUMN_MONEY + " real, " + COSTS_COLUMN_DATE + " text, "
            + COSTS_COLUMN_NOTE + " text, " + COSTS_COLUMN_CATEGORY + " integer);";

    public static final String DB_MONEYBOX_TABLE = "moneyBox";
    public static final String COLUMN_MONEYBOX_ID = "_id";
    public static final String COLUMN_MONEYBOX_MONEY = "money";
    public static final String COLUMN_MONEYBOX_DATE = "dateAdding";
    public static final String COLUMN_MONEYBOX_NOTE = "note";

    private static final String DB_CREATE_MONEYBOX = "create table " + DB_MONEYBOX_TABLE + "(" +
            COLUMN_MONEYBOX_ID + " integer primary key autoincrement, " +
            COLUMN_MONEYBOX_MONEY + " real, " +
            COLUMN_MONEYBOX_DATE + " text, " +
            COLUMN_MONEYBOX_NOTE + " text);";



    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE_DEPTORS);
        sqLiteDatabase.execSQL(DB_CREATE_BIRTHDAYS);
        sqLiteDatabase.execSQL(CATEGORY_TABLE_CREATE);
        sqLiteDatabase.execSQL(COSTS_TABLE_CREATE);
        sqLiteDatabase.execSQL(DB_CREATE_MONEYBOX);

        ContentValues contentValues = new ContentValues();
        String[] categories = new String[] {"Еда", "Дом", "Одежда и обувь", "Хобби",
                "Лекарства", "Интернет", "Образование", "Путешествия", "Телефонная связь", "Развлечения"};
        for(int i = 0; i < categories.length; i++){
            contentValues.put(CATEGORY_COLUMN_ID, i + 1);
            contentValues.put(CATEGORY_COLUMN_NAME, categories[i]);
            sqLiteDatabase.insert(CATEGORY_TABLE, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
