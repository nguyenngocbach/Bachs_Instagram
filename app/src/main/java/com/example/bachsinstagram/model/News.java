package com.example.bachsinstagram.model;

public class News {
    private String status;
    private String title;
    private int image;
    private int like;
    private boolean isLike= false;

    public News() {
    }

    public News(String status, String title, int image, int like) {
        this.status = status;
        this.title = title;
        this.image = image;
        this.like = like;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
