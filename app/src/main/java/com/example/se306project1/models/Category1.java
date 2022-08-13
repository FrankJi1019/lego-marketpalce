package com.example.se306project1.models;

public class Category1 extends Category{
    private String layout;

    public Category1(){

    }

    public Category1(String layout) {
        this.layout = layout;
    }

    public Category1(String title, int image, String layout) {
        super(title, image);
        this.layout = layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }
    public String getLayout() { return layout;}
}
