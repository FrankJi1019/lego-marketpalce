package com.example.se306project1.models;

import com.example.se306project1.utilities.PasswordEncripter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements IUser{
    private String username;
    private String password;

    @Override
    public String getUsername() {return username;}

    @Override
    public String getPassword() {return password;}

    public void setUsername(String username) {this.username = username;}

    public void setPassword(String password) {this.password = password;}
}
