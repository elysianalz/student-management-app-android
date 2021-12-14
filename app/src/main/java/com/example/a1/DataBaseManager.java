package com.example.a1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

// manager for managing student database
public class DataBaseManager {
    public static final String DB_NAME = "students";
    public static final String DB_TABLE = "studentInfo";

    public static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE +
            "(" +
            "StudentID INTEGER PRIMARY KEY, " +
            "FirstName TEXT, " +
            "LastName TEXT, " +
            "Age INTEGER," +
            "Gender TEXT," +
            "Address TEXT," +
            "Course TEXT," +
            "Image TEXT);";

    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DataBaseManager(Context context)
    {
        this.context = context;
        helper = new SQLHelper(context);
        this.db = helper.getWritableDatabase();
    }

    public DataBaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close()
    {
        helper.close();
    }

    public void addRow(Integer studentId, String firstName, String lastName, Integer age, String gender, String address, String course, String image)
    {
        ContentValues newStudent = new ContentValues();
        newStudent.put("StudentID", studentId);
        newStudent.put("FirstName", firstName);
        newStudent.put("LastName", lastName);
        newStudent.put("Age", age);
        newStudent.put("Gender", gender);
        newStudent.put("Address", address);
        newStudent.put("Course", course);
        newStudent.put("Image", image);
        try {
            db.insertOrThrow(DB_TABLE, null, newStudent);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
        }
    }

    public void deleteRow(String i)
    {
        db.execSQL("DELETE FROM " + DB_TABLE + " WHERE StudentId = " + i );
    }

    public void updateRow(Integer id, String firstName, String lastName, String age, String gender, String course, String address, String image)
    {
        ContentValues values = new ContentValues();
        values.put("FirstName", firstName);
        values.put("LastName", lastName);
        values.put("Age", age);
        values.put("Gender", gender);
        values.put("Course", course);
        values.put("Address", address);
        values.put("Image", image);
        db.update(DB_TABLE, values, " StudentID = " + id.toString(), null);
    }

    public ArrayList<String> getRows()
    {
        ArrayList<String> studentRows = new ArrayList<String>();
        String[] columns = new String[] {"StudentID", "FirstName", "LastName", "Age", "Gender", "Address", "Course"};
        Cursor  cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            studentRows.add(cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2));
            cursor.moveToNext();
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return studentRows;
    }

    public ArrayList<String> getRows(String[] columns, String i)
    {
        ArrayList<String> studentRows = new ArrayList<String>();
        Cursor  cursor = db.query(DB_TABLE, columns, "StudentID = "+i, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            String row = "";
            for(int j = 0; j < columns.length; j++)
            {
                row += cursor.getString(j) + ", ";
            }
            studentRows.add(row);
            cursor.moveToNext();
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return studentRows;
    }
}


