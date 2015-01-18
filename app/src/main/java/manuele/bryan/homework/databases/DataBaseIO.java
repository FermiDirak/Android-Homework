package manuele.bryan.homework.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import manuele.bryan.homework.adapters.ListModel;
import manuele.bryan.homework.helpers.DatesHelper;

public class DataBaseIO {
    Context context;

    public DataBaseIO(Context context) {
        this.context = context;
    }

    public List<ListModel> getAssignments() {
        List<ListModel> data = new ArrayList<>();

        DataBase dataBase = new DataBase(context);
        SQLiteDatabase qdb = dataBase.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DataBase.KEY_TABLE_ASSIGNMENTS;
        Cursor cursor = qdb.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                ListModel listModel = new ListModel(cursor.getString(
                        cursor.getColumnIndex(DataBase.KEY_TITLE)),
                        cursor.getString(
                                cursor.getColumnIndex(DataBase.KEY_SUBJECT)),
                        DatesHelper.stringToDate(cursor.getString(
                                cursor.getColumnIndex(DataBase.KEY_DUE))));

                data.add(listModel);
            }
        }

        return data;
    }

    public void addAssignment(String title, String subject, String dueDate) {
        DataBase database = new DataBase(context);
        SQLiteDatabase qdb = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBase.KEY_TITLE, title);
        values.put(DataBase.KEY_SUBJECT, subject);
        values.put(DataBase.KEY_DUE, dueDate);

        qdb.insert(DataBase.KEY_TABLE_ASSIGNMENTS, null, values);
    }

    public void deleteAssignment(List<ListModel> assignments, int position) {
        DataBase database = new DataBase(context);
        SQLiteDatabase qdb = database.getWritableDatabase();

        qdb.delete(DataBase.KEY_TABLE_ASSIGNMENTS,
                DataBase.KEY_TITLE + " = ?", new String[]{assignments.get(position).title});
        qdb.close();
    }

    public List<String> getSubjects() {
        List<String> subjects = new ArrayList<>();

        DataBase dataBase = new DataBase(context);
        SQLiteDatabase qdb = dataBase.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DataBase.KEY_TABLE_SUBJCTS;
        Cursor cursor = qdb.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String subject = cursor.getString(
                        cursor.getColumnIndex(DataBase.KEY_SUBJECT2));

                subjects.add(subject);
            }
        }

        return subjects;
    }

    public void addSubject(String subject) {
        DataBase database = new DataBase(context);
        SQLiteDatabase qdb = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBase.KEY_SUBJECT2, subject);

        qdb.insert(DataBase.KEY_TABLE_SUBJCTS, null, values);

    }

    public void deleteSubject(List<String> subjects, int position) {
        DataBase database = new DataBase(context);
        SQLiteDatabase qdb = database.getWritableDatabase();

        qdb.delete(DataBase.KEY_TABLE_SUBJCTS,
                DataBase.KEY_SUBJECT2 + " = ?", new String[] {subjects.get(position)});
        qdb.close();
    }

}
