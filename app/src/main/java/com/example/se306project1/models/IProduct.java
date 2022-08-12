package com.example.se306project1.models;

import java.util.List;

public interface IProduct {

    public int getId();
    public void setId(int id);
    public int getCategoryId();
    public void setCategoryId(int categoryId);
    public String getName();
    public void setName(String name);
    public String getDescription();
    public void setDescription(String description);
    public double getPrice();
    public void setPrice(double price);
    public List<String> getImages();
    public void setImages(List<String> images);
    public int getStock();
    public void setStock(int stock);
    public CartProduct toCartProduct();

}
