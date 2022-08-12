package com.example.se306project1.database;

public interface IUserDatabase {
    boolean isUserExist(String username);
    boolean isLoginValid(String username, String password);
    void addUserToFireStore(String username, String password);
//    password needed to be hashed
}
