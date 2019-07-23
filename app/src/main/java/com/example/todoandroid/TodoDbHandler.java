package com.example.todoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TodoDbHandler extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "tododb";
    public static final String TABLE_NAME = "todo";
    public static final String KEY_ID = "_id";
    public static final String KEY_TODO = "todo";
    public static final String KEY_IMPORTANT = "important";

    public TodoDbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLE_NAME + " (" + KEY_ID +
                " integer primary key autoincrement, " + KEY_TODO
                + " text, " + KEY_IMPORTANT + " integer)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table if exists " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<TodoItem> getAllTodos() {
        ArrayList<TodoItem> todoItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select _id, todo, important from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                TodoItem todoItem = new TodoItem();
                todoItem.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                todoItem.setTodo(cursor.getString(cursor.getColumnIndex(KEY_TODO)));
                todoItem.setImportant(cursor.getInt(cursor.getColumnIndex(KEY_IMPORTANT)));
                todoItems.add(todoItem);
            } while (cursor.moveToNext());
        }
        db.close();
        return todoItems;
    }

    public TodoItem getTodo(int id) {
        TodoItem todoItem = new TodoItem();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select _id, todo, important from " + TABLE_NAME + " where _id =" + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            todoItem.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            todoItem.setTodo(cursor.getString(cursor.getColumnIndex(KEY_TODO)));
            todoItem.setImportant(cursor.getInt(cursor.getColumnIndex(KEY_IMPORTANT)));
        }
        db.close();
        return todoItem;
    }

    public long insertTodo(String todo, int important) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TODO, todo);
        contentValues.put(KEY_IMPORTANT, important);
        long newId = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return newId;
    }

    public void deleteTodo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void updateTodo(int id, String todo, int important) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TODO, todo);
        contentValues.put(KEY_IMPORTANT, String.valueOf(important));
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, contentValues, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
}
