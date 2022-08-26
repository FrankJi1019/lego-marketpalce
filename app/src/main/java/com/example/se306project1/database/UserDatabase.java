package com.example.se306project1.database;


import com.example.se306project1.models.User;

import com.example.se306project1.utilities.PasswordEncripter;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserDatabase implements IUserDatabase{
    private static UserDatabase userDatabase = null;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private UserDatabase(){}

    public static UserDatabase getInstance(){
        if (userDatabase == null){
            userDatabase = new UserDatabase();
        }
        return userDatabase;
    }

    public void isUserExist(FireStoreCallback fireStoreCallback,String username){
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnSuccessListener(documentSnapshot -> fireStoreCallback.Callback(documentSnapshot.exists()));
   }

    public void addUserToFireStore(String username, String password){
        User user = new User(username,password);
        db.collection("Users").document(username).set(user);

        Map<String, List<String>> map = new HashMap<>();
        map.put("likeList",new ArrayList<>());
        db.collection("likes").document(username).set(map);
        Map<String, List<String>> map1 = new HashMap<>();
        map1.put("cartproducts",new ArrayList<>());
        db.collection("cart").document(username).set(map1);
    }

    public void isLoginValid(FireStoreCallback fireStoreCallback,String username, String password){
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                boolean isValid = Objects.requireNonNull(
                        documentSnapshot.toObject(User.class))
                        .getPassword()
                        .equals(PasswordEncripter.hashPassword(password)
                );
                fireStoreCallback.Callback(isValid);
            }
        });
    }
}