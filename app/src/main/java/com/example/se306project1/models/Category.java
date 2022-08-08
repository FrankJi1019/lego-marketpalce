package com.example.se306project1.models;

public abstract class Category implements ICategory{
    private int id;
    public int getId(){return id;}
    public abstract void setLayout(String layout);
}
