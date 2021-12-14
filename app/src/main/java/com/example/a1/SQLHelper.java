package com.example.a1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
// sql helper class included in database manager
class SQLHelper extends SQLiteOpenHelper {
    public SQLHelper(Context context) {
        super(context, DataBaseManager.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.w("Products table", "Upgrading database i.e. dropping table and recreating it");
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseManager.DB_TABLE);
        onCreate(db);
    }
}
