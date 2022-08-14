package com.example.se306project1.models;

import java.util.List;

public class CartProduct extends Product {

    private int amount;

    public CartProduct(){}

    public CartProduct(int amount) {
        this.amount = amount;
    }

    public CartProduct(String categoryTitle, String name, String description, double price, List<Integer> images, int stock, int likesNum, int amount) {
        super(categoryTitle, name, description, price, images, stock, likesNum);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
