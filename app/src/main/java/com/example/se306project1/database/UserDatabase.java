package com.example.se306project1.database;

public class UserDatabase implements IUserDatabase{

    public UserDatabase(){}


    public boolean isLoginValid(String username, String password){
        return true;
    }

    public void createUser(String username, String password){

    }
}
