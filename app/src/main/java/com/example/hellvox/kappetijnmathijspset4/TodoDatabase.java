package com.example.hellvox.kappetijnmathijspset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDatabase extends SQLiteOpenHelper {

    private static TodoDatabase instance;


    private TodoDatabase(Context context) {
        super(context, "todo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
          "create table if not exists todos" +
                  "( _id integer primary key autoincrement, title text, complete integer )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS todos");
        onCreate(db);

    }

    public void insertToDo (String title, int complete) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("complete", complete);
        db.insert("todos", null, contentValues);
    }

    public void update(long id, int complete) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("complete", complete);
        db.update("todos",contentValues, "_id = " + id, null);

    }
    public void delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("todos","_id = " + id, null);
    }

    public Cursor selectAll() {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("select * from todos order by complete", null);
    }

    public static TodoDatabase getInstance(Context context) {
        if (instance != null)
            return instance;
        else {
            instance = new TodoDatabase(context);
            return instance;
        }
    }
}
