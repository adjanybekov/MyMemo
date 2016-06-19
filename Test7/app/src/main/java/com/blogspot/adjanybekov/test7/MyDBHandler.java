package com.blogspot.adjanybekov.test7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDBHandler {
    private SQLiteDatabase database;
    private MyDB openHelper;
    private static volatile MyDBHandler instance;

    private MyDBHandler(Context context) {
        this.openHelper = new MyDB(context,null,null,1);
    }

    public static synchronized MyDBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new MyDBHandler(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public void save(MyMemo memo) {
        ContentValues values = new ContentValues();
        memo.set_id(new Date());
        values.put("_id",memo.get_id().getTime());
        values.put(MyDB.COLUMN_TITLE, memo.getTitle());
        values.put(MyDB.COLUMN_TEXT, memo.getText());
        database.insert(MyDB.TABLE_MEMOS, null, values);
    }

    public void update(MyMemo memo) {
        ContentValues values = new ContentValues();
        memo.set_id(new Date());
        values.put(MyDB.COLUMN_ID,memo.get_id().getTime());
        values.put(MyDB.COLUMN_TITLE, memo.getTitle());
        values.put(MyDB.COLUMN_TEXT, memo.getText());
        database.update(MyDB.TABLE_MEMOS, values, null, null);
    }

    public void delete(MyMemo memo) {
        String id = memo.getTitle();
        database.delete(MyDB.TABLE_MEMOS, "title = ?", new String[]{id});

        //database.execSQL("DELETE FROM " + MyDB.TABLE_MEMOS + " WHERE " + MyDB.COLUMN_TITLE+ "=\"" + memo.getTitle()+ "\";");
    }

    public List getAllMyMemos() {
        List memos = new ArrayList<>();
        String query = "SELECT * FROM " + MyDB.TABLE_MEMOS + " WHERE 1";
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            System.out.println("getAllMyMemos");
            String title= cursor.getString(1);
            String text = cursor.getString(2);
            memos.add(new MyMemo(title, text));
            cursor.moveToNext();
        }
        cursor.close();
        return memos;
    }
}