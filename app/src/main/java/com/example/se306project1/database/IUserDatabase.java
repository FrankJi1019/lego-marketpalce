package com.example.se306project1.database;

public interface IUserDatabase {
    boolean isLoginValid(String username, String password);
    void createUser(String username, String password);
//    password needed to be hashed
}
