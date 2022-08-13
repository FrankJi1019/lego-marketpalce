package com.example.se306project1.models;

public abstract class Category implements ICategory{
    private String title;
    private int image;

    public Category(){

    }

    public Category(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public void setTitle(String tile) {this.title = tile;}
    public String getTitle() {return title;}
    public int getImage() {return image;}
    public void setImage(int image) {this.image = image;}
    public abstract void setLayout(String layout);
}
