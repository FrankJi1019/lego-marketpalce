package com.example.se306project1.utilities;

import com.example.se306project1.models.User;

public class UserState {
    private static UserState userState = null;
    private User currentUser = null;

    private UserState(){}

    public User getCurrentUser(){
        return  this.currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static UserState getInstance(){
        if(userState == null) {
            userState = new UserState();
        }
        return userState;
    }
}
