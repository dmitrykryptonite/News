package com.example.news.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.news.app.App;
import com.example.news.entities.FavoriteArticle;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public class DatabaseNewsManager {
    private static DatabaseNewsManager instance;

    public static DatabaseNewsManager getInstance() {
        if (instance == null)
            instance = new DatabaseNewsManager();
        return instance;
    }

    public Observable<List<FavoriteArticle>> favoriteArticlesUpdateListener;
    private ObservableEmitter<List<FavoriteArticle>> emitter;
    private DatabaseNews databaseNews;

    private DatabaseNewsManager() {
        Observable<List<FavoriteArticle>> observable = Observable.create(emitter ->
                DatabaseNewsManager.this.emitter = emitter);
        favoriteArticlesUpdateListener = observable.share();
        databaseNews = new DatabaseNews(App.getApp());
    }

    private int connectionsCount = 0;

    private void closeDB() {
        connectionsCount--;
        if (connectionsCount == 0) {
            databaseNews.close();
        }
    }

    public void getListNews() {
        emitter.onNext(getAllNews());
    }

    public Completable saveNewsToFavorites(String title, String appbarTitle,
                                           String appbarSubtitle, String date, String author,
                                           String description, String pathToImage, String url) {
        return Completable.create(emitter -> {
            ContentValues values = new ContentValues();
            SQLiteDatabase sqLiteDatabase = databaseNews.getWritableDatabase();
            connectionsCount++;
            values.put(DatabaseNews.NEWS_TITLE, title);
            values.put(DatabaseNews.NEWS_APPBAR_TITLE, appbarTitle);
            values.put(DatabaseNews.NEWS_APPBAR_SUBTITLE, appbarSubtitle);
            values.put(DatabaseNews.NEWS_DATE, date);
            values.put(DatabaseNews.NEWS_AUTHOR, author);
            values.put(DatabaseNews.NEWS_DESCRIPTION, description);
            values.put(DatabaseNews.NEWS_PATH_TO_IMAGE, pathToImage);
            values.put(DatabaseNews.NEWS_URL, url);
            sqLiteDatabase.insert(DatabaseNews.TABLE_NEWS, null, values);
            closeDB();
            emitter.onComplete();
            if (DatabaseNewsManager.this.emitter != null) {
                DatabaseNewsManager.this.emitter.onNext(getAllNews());
            }
        });
    }

    private List<FavoriteArticle> getAllNews() {
        List<FavoriteArticle> articlesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseNews.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseNews.TABLE_NEWS +
                " ORDER BY " + DatabaseNews.NEWS_ID + " DESC", null);
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_ID);
            int titleColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_TITLE);
            int appbarTitleColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_APPBAR_TITLE);
            int appbarSubtitleColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_APPBAR_SUBTITLE);
            int dateColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_DATE);
            int authorColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_AUTHOR);
            int descriptionColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_DESCRIPTION);
            int pathToImageColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_PATH_TO_IMAGE);
            int urlColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_URL);
            do {
                int id = cursor.getInt(idColIndex);
                String title = cursor.getString(titleColIndex);
                String appbarTitle = cursor.getString(appbarTitleColIndex);
                String appbarSubtitle = cursor.getString(appbarSubtitleColIndex);
                String date = cursor.getString(dateColIndex);
                String author = cursor.getString(authorColIndex);
                String description = cursor.getString(descriptionColIndex);
                String pathToImage = cursor.getString(pathToImageColIndex);
                String url = cursor.getString(urlColIndex);
                FavoriteArticle favoriteArticle = new FavoriteArticle(id, title,
                        appbarTitle, appbarSubtitle, date, author, description, pathToImage, url);
                articlesList.add(favoriteArticle);
            } while (cursor.moveToNext());
        }
        cursor.close();
        databaseNews.close();
        return articlesList;
    }

    public FavoriteArticle getNewsByTitleApiArticle(String strTitle) {
        SQLiteDatabase sqLiteDatabase = databaseNews.getWritableDatabase();
        FavoriteArticle favoriteArticle = null;
        Cursor cursor = sqLiteDatabase.query(DatabaseNews.TABLE_NEWS, null,
                "title=?", new String[]{strTitle}, null,
                null, null);
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_ID);
            int titleColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_TITLE);
            int appbarTitleColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_APPBAR_TITLE);
            int appbarSubtitleColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_APPBAR_SUBTITLE);
            int dateColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_DATE);
            int authorColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_AUTHOR);
            int descriptionColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_DESCRIPTION);
            int pathToImageColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_PATH_TO_IMAGE);
            int urlColIndex = cursor.getColumnIndex(DatabaseNews.NEWS_URL);
            do {
                int id = cursor.getInt(idColIndex);
                String title = cursor.getString(titleColIndex);
                String appbarTitle = cursor.getString(appbarTitleColIndex);
                String appbarSubtitle = cursor.getString(appbarSubtitleColIndex);
                String date = cursor.getString(dateColIndex);
                String author = cursor.getString(authorColIndex);
                String description = cursor.getString(descriptionColIndex);
                String pathToImage = cursor.getString(pathToImageColIndex);
                String url = cursor.getString(urlColIndex);
                favoriteArticle = new FavoriteArticle(id, title, appbarTitle,
                        appbarSubtitle, date, author, description, pathToImage, url);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return favoriteArticle;
    }

    public Completable deleteNewsFromFavorites(String title) {
        return Completable.create(emitter -> {
            SQLiteDatabase sqLiteDatabase = databaseNews.getWritableDatabase();
            connectionsCount++;
            sqLiteDatabase.execSQL("DELETE FROM " + DatabaseNews.TABLE_NEWS + " WHERE " +
                    DatabaseNews.NEWS_TITLE + " = '" + title + "'");
            closeDB();
            emitter.onComplete();
            if (DatabaseNewsManager.this.emitter != null)
                DatabaseNewsManager.this.emitter.onNext(getAllNews());
        });
    }
}
