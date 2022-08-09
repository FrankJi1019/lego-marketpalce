package com.example.se306project1.models;

public abstract class Category implements ICategory{
    private int id;
    private String title;
    private String image;
    public int getId(){return id;}
    public void setId(int id) {this.id = id;}
    public void setTitle(String tile) {this.title = tile;}
    public String getTitle() {return title;}
    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}
    public abstract void setLayout(String layout);
}
