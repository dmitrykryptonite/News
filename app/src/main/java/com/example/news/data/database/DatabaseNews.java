package com.example.news.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseNews extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;

    static final String TABLE_NEWS = "news";
    static final String NEWS_ID = "_id";
    static final String NEWS_TITLE = "title";
    static final String NEWS_APPBAR_TITLE = "appbar_title";
    static final String NEWS_APPBAR_SUBTITLE = "appbar_subtitle";
    static final String NEWS_DATE = "date";
    static final String NEWS_AUTHOR = "author";
    static final String NEWS_DESCRIPTION = "description";
    static final String NEWS_PATH_TO_IMAGE = "path_to_image";
    static final String NEWS_URL = "url";
    private static final String NEWS_TIME_ADDED = "time_added";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NEWS + " (" +
                    NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NEWS_TITLE + " TEXT, " +
                    NEWS_APPBAR_TITLE + " TEXT, " +
                    NEWS_APPBAR_SUBTITLE + " TEXT, " +
                    NEWS_DATE + " TEXT, " +
                    NEWS_AUTHOR + " TEXT, " +
                    NEWS_DESCRIPTION + " TEXT, " +
                    NEWS_PATH_TO_IMAGE + " TEXT, " +
                    NEWS_URL + " TEXT, " +
                    NEWS_TIME_ADDED + " TEXT default CURRENT_TIMESTAMP" + ")";

    DatabaseNews(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
