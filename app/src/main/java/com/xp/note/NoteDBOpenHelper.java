package com.xp.note;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by XP on 2015/2/15.
 */
public class NoteDBOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME ="note";
    public static final int VERSION = 1;
    public static final String TITLE="title";
    public static final String CONTENT="content";
    public static final String TIME="time";
    public static final String ID="_id";

    public NoteDBOpenHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ TABLE_NAME +" ("
                + ID + " integer primary key autoincrement  ,"
                + CONTENT + " TEXT NOT NULL,"
                + TITLE + " TEXT NOT NULL,"
                + TIME + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
