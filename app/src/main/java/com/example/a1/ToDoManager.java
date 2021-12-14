package com.example.a1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
// for managing the todo database
public class ToDoManager {
    public static final String DB_NAME = "todos";
    public static final String DB_TABLE = "todo";

    public static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE +
            "(" +
            "Task TEXT, " +
            "Location TEXT, " +
            "Completed TEXT); ";

    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public ToDoManager(Context context)
    {
        this.context = context;
        helper = new SQLHelper(context);
        this.db = helper.getWritableDatabase();
    }

    public ToDoManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close()
    {
        helper.close();
    }

    public void addRow(String task, String location, String completed)
    {
        ContentValues newTodo = new ContentValues();
        newTodo.put("Task", task);
        newTodo.put("Location", location);
        newTodo.put("Completed", completed);

        try {
            db.insertOrThrow(DB_TABLE, null, newTodo);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
        }
    }

    public void deleteRow(String i)
    {
        db.execSQL("DELETE FROM " + DB_TABLE + " WHERE Task = " + i );
    }

    public void clearTodos()
    {
        db.delete(DB_TABLE, null, null);
    }

    public ArrayList<String> getRows()
    {
        ArrayList<String> studentRows = new ArrayList<String>();
        String[] columns = new String[] {"Task", "Location", "Completed"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        String tableRows = "";
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            studentRows.add(cursor.getString(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2));
            cursor.moveToNext();
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return studentRows;
    }

    public void updateRow(String task, String completed)
    {
        ContentValues values = new ContentValues();
        values.put("Completed", completed);
        db.update(DB_TABLE, values, "Task =" + "'"+task+"'", null);
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
            db.execSQL("DROP TABLE IF EXISTS " + DataBaseManager.DB_TABLE);
            onCreate(db);
        }
    }
}
