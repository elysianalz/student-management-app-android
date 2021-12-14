package com.example.a1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
// data base manager for exam database
public class ExamManager {
    public static final String DB_NAME = "exams";
    public static final String DB_TABLE = "exam";

    public static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE +
            "(" +
            "StudentId INTEGER," +
            "Name TEXT, " +
            "Date TEXT, " +
            "Time TEXT," +
            "Location Text); ";

    private ExamManager.SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public ExamManager(Context context)
    {
        this.context = context;
        helper = new SQLHelper(context);
        this.db = helper.getWritableDatabase();
    }

    public ExamManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close()
    {
        helper.close();
    }

    public void addRow(int studentId, String name, String date, String time, String location)
    {
        ContentValues newExam = new ContentValues();
        newExam.put("StudentId", studentId);
        newExam.put("Name", name);
        newExam.put("Date", date);
        newExam.put("Time", time);
        newExam.put("Location", location);

        try {
            db.insertOrThrow(DB_TABLE, null, newExam);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
        }
    }

    public void deleteSelected(String[] exams, int id)
    {
        for(int i = 0; i < exams.length; i++)
        {
            String where = "Name = '"+ exams[i] + "' AND StudentId = " + id;
            db.delete(DB_TABLE, where, null);
        }
    }

    public ArrayList<String> getRows(String id)
    {
        ArrayList<String> studentRows = new ArrayList<String>();
        String[] columns = new String[] {"Name", "Location", "Date", "Time"};
        String where = "StudentId = ?";
        String[] arg = {id};
        Cursor cursor = db.query(DB_TABLE, columns, where, arg, null, null, null);
        String tableRows = "";
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            studentRows.add(cursor.getString(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3));
            cursor.moveToNext();
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return studentRows;
    }

    class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context context) {
            super(context, DB_NAME, null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i2) {
            Log.w("Products table", "Upgrading database i.e. dropping table and recreating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}
