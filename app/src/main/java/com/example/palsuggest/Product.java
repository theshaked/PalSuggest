package com.example.palsuggest;


import android.graphics.Bitmap;

public class Product {
    private String name;
    private String review;
    private String tag;
    private String link;
    private int price;
    private Bitmap image;
    private int suggesterID;
    private int []likesIDs;

    public Product(String name, String review, String tag, String link, int price, Bitmap image, int suggesterID, int[] likesIDs) {
        this.name = name;
        this.review = review;
        this.tag = tag;
        this.link = link;
        this.price = price;
        this.image = image;
        this.suggesterID = suggesterID;
        this.likesIDs = likesIDs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getSuggesterID() {
        return suggesterID;
    }

    public void setSuggesterID(int suggesterID) {
        this.suggesterID = suggesterID;
    }

    public int[] getLikesIDs() {
        return likesIDs;
    }

    public void setLikesIDs(int[] likesIDs) {
        this.likesIDs = likesIDs;
    }


}
