package com.example.se306project1.database;

import com.example.se306project1.models.IProduct;

public interface IPoductDatabase {

    public void addProductToDb(IProduct product);
    public void deleteProductFromDb(IProduct product);
    public <T> void updateProductInfo(String productName,String fieldName,T value);
    public <T> void updateIncrement(String productName,String fieldName,T step);
    public void getAllProducts(FireStoreCallback fireStoreCallback);
    public void getSpecificProduct(FireStoreCallback fireStoreCallback, String productName);
    public void getAllProductsByCategoryTitle(FireStoreCallback fireStoreCallback, String categorytitle);
}
