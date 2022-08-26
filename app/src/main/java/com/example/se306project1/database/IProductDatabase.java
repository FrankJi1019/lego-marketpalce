package com.example.se306project1.database;

public interface IProductDatabase {

    <T> void updateProductInfo(String productName,String fieldName,T value);
    <T> void updateIncrement(String productName,String fieldName,T step);
    void getAllProducts(FireStoreCallback fireStoreCallback);
    void getSpecificProduct(FireStoreCallback fireStoreCallback, String productName);
    void getAllProductsByCategoryTitle(FireStoreCallback fireStoreCallback, String categoryTitle);
}
