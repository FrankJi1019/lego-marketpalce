package com.example.se306project1.models;

public class Category3 extends Category{
    private String layout;

    public Category3(){

    }

    public Category3(String layout) {
        this.layout = layout;
    }

    public Category3(String title, String image, String layout) {
        super(title, image);
        this.layout = layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }
    public String getLayout(){return layout;}
}
