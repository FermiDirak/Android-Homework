package manuele.bryan.homework.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    Context context;

    final static int DB_VERSION = 1;
    final static String DB_NAME = "assignmentsdb.s3db";
    public static final String KEY_ID = "_id";

    public static final String KEY_TABLE_ASSIGNMENTS = "assignments";
    public static final String KEY_TITLE = "title";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_DUE = "due";

    public static final String KEY_TABLE_SUBJCTS = "subjects";
    public static final String KEY_SUBJECT2 ="subjecttwo";

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE " + KEY_TABLE_ASSIGNMENTS + " ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TITLE +" TEXT NOT NULL," +
                KEY_SUBJECT + " TEXT NOT NULL," +
                KEY_DUE + " TEXT NOT NULL)";
        db.execSQL(table1);

        String table2 = "CREATE TABLE " + KEY_TABLE_SUBJCTS + " ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_SUBJECT2 + " TEXT NOT NULL)";
        db.execSQL(table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + KEY_TABLE_ASSIGNMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_TABLE_SUBJCTS);

        onCreate(db);
    }
}
