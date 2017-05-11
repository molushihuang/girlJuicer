package com.xqd.meizhi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pherson on 2017-5-11.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    Context context;
    public static final String CREATE_COLLECTION = "create table collection("
            + "_id integer primary key autoincrement,"
            + "myId text,"
            + "createdAt text,"
            + "type text,"
            + "who text,"
            + "images text,"
            + "title text,"
            + "url text)";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COLLECTION);
//        T.showShort(context, "create db successful");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch(oldVersion){
            case 1:
            db.execSQL("alter table collection add column myId text");
            db.execSQL("alter table collection add column createdAt text");
            db.execSQL("alter table collection add column type text");
            db.execSQL("alter table collection add column who text");
            db.execSQL("alter table collection add column images text");

        }
    }
}
