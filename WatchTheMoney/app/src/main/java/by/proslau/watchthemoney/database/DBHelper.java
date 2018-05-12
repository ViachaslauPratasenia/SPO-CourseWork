package by.proslau.watchthemoney.database;

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

    //Переменная, которая хранит int как boolean. Если 0 - человек должен вам, если 1 - должны вы
    public static final String COLUMN_DEPTOR_CHOISE = "choise";

    private static final String DB_CREATE_DEPTORS =
            "create table " + DB_DEPTOR_TABLE + "(" +
                    COLUMN_DEPTOR_ID + " integer primary key autoincrement, " +
                    COLUMN_DEPTOR_NAME + " text, " +
                    COLUMN_DEPTOR_MONEY + " real, " +
                    COLUMN_DEPTOR_CHOISE + " real" + ");";

    private static final String DB_CREATE_BIRTHDAYS = "create table " + DB_BIRTHDAYS_TABLE + "(" +
            COLUMN_BIRTHDAYS_ID + " integer primary key autoincrement, " +
            COLUMN_BIRTHDAYS_NAME + " text, " +
            COLUMN_BIRTHDAYS_DATE + " text" + ");";

    public static final String DB_PAYMENTS_TABLE = "payments";
    public static final String COLUMN_PAYMENTS_ID = "_id";
    public static final String COLUMN_PAYMENTS_DATE = "dayOfPayments";
    public static final String COLUMN_PAYMENTS_MONEY = "money";
    public static final String COLUMN_PAYMENTS_CATEGORY = "category";
    public static final String COLUMN_PAYMENTS_NOTE = "note";

    private static final String DB_CREATE_PAYMENTS = "create table " + DB_PAYMENTS_TABLE + "(" +
            COLUMN_PAYMENTS_ID + " integer primary key autoincrement, " +
            COLUMN_PAYMENTS_CATEGORY + " text, " +
            COLUMN_PAYMENTS_MONEY + " real, " +
            COLUMN_PAYMENTS_DATE + " text, " +
            COLUMN_PAYMENTS_NOTE + " text);";

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
        sqLiteDatabase.execSQL(DB_CREATE_PAYMENTS);
        sqLiteDatabase.execSQL(DB_CREATE_MONEYBOX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
