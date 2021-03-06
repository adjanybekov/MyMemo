package com.blogspot.adjanybekov.ZhurnalZametok;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDBHandler {
    private SQLiteDatabase database;
    private MyDB myDB;
    private static volatile MyDBHandler myDBHandler;

    public MyDBHandler(Context context) {
        this.myDB = new MyDB(context,null,null,1);
    }

    public static synchronized MyDBHandler getInstance(Context context) {
        if (myDBHandler == null) {
            myDBHandler = new MyDBHandler(context);
        }
        return myDBHandler;
    }

    public void open() {
        this.database = myDB.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public void save(MyMemo memo) {
        ContentValues values = new ContentValues();
        values.put(MyDB.COLUMN_ID, memo.getTime());
        values.put(MyDB.COLUMN_TITLE, memo.getTitle());
        values.put(MyDB.COLUMN_TEXT, memo.getText());
        database.insert(MyDB.TABLE_MEMOS, null, values);
    }

    public void update(MyMemo memo) {
        ContentValues values = new ContentValues();
        //memo.set_id(new Date().getTime());
        String id = Long.toString(memo.getTime());
        values.put(MyDB.COLUMN_ID, new Date().getTime());//we need to reset existing date
        values.put(MyDB.COLUMN_TITLE, memo.getTitle());
        values.put(MyDB.COLUMN_TEXT, memo.getText());
        database.update(MyDB.TABLE_MEMOS, values,MyDB.COLUMN_ID+"= ?", new String[]{id});
    }

    public void delete(MyMemo memo) {
        String id = Long.toString(memo.getTime());
        database.delete(MyDB.TABLE_MEMOS, MyDB.COLUMN_ID+"= ?", new String[]{id});
        //database.execSQL("DELETE FROM " + MyDB.TABLE_MEMOS + " WHERE " + MyDB.COLUMN_TITLE+ "=\"" + memo.getTitle()+ "\";");
    }

    public List getAllMyMemos() {
        List memos = new ArrayList<>();
        String query = "SELECT * FROM " + MyDB.TABLE_MEMOS + " WHERE 1";
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(0);
            String title= cursor.getString(1);
            String text = cursor.getString(2);
            memos.add(new MyMemo(title, text, id ));
            cursor.moveToNext();
        }
        cursor.close();
        return memos;
    }
}