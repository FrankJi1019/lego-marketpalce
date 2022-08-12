package com.example.se306project1.models;

import java.util.List;

public interface IProduct {

    public String getCategoryTitle();
    public void setCategoryTitle(String title);
    public String getName();
    public void setName(String name);
    public String getDescription();
    public void setDescription(String description);
    public double getPrice();
    public void setPrice(double price);
    public List<Integer> getImages();
    public void setImages(List<Integer> images);
    public int getStock();
    public void setStock(int stock);
    public void setLikesNumber(int likesNumber);
    public int getLikesNumber();

}
