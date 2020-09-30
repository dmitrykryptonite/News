package com.example.news.entities;

public class FavoriteArticle {
    private int id;
    private String title;
    private String appbarTitle;
    private String appbarSubtitle;
    private String date;
    private String author;
    private String description;
    private String pathToImage;
    private String url;

    public FavoriteArticle(int id, String title, String appbarTitle,
                           String appbarSubtitle, String date, String author, String description,
                           String pathToImage, String url) {
        this.id = id;
        this.title = title;
        this.appbarTitle = appbarTitle;
        this.appbarSubtitle = appbarSubtitle;
        this.date = date;
        this.author = author;
        this.description = description;
        this.pathToImage = pathToImage;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppbarTitle() {
        return appbarTitle;
    }

    public void setAppbarTitle(String appbarTitle) {
        this.appbarTitle = appbarTitle;
    }

    public String getAppbarSubtitle() {
        return appbarSubtitle;
    }

    public void setAppbarSubtitle(String appbarSubtitle) {
        this.appbarSubtitle = appbarSubtitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
