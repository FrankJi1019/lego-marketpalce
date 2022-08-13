package com.example.se306project1.database;

public interface IUserDatabase {
    void isUserExist(FireStoreCallback fireStoreCallback,String username);
    void isLoginValid(FireStoreCallback fireStoreCallback,String username, String password);
    void addUserToFireStore(String username, String password);
}
