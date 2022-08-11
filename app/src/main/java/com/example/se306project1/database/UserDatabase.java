package com.example.se306project1.database;

public class UserDatabase implements IUserDatabase{

    public UserDatabase(){}

//    TODO
   public boolean isUserExist(String username){
        return true;
   }

    public boolean isLoginValid(String username, String password){
        return true;
    }

    public void addUserToFireStore(String username, String password){

    }
}
