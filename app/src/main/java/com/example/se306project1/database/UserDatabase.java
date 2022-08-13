package com.example.se306project1.database;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.se306project1.models.CartProduct;
import com.example.se306project1.models.IUser;
import com.example.se306project1.models.User;

import com.example.se306project1.utilities.PasswordEncripter;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDatabase implements IUserDatabase{
    private static UserDatabase userDatabase = null;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private UserDatabase(){}

    public static UserDatabase getInstance(){
        if (userDatabase == null){
            userDatabase = new UserDatabase();
        }
        return userDatabase;
    }

    public void isUserExist(FireStoreCallback fireStoreCallback,String username){
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                fireStoreCallback.Callback((Boolean)documentSnapshot.exists());
            }
        });
   }

    public void addUserToFireStore(String username, String password){
        User user = new User(username,password);
        db.collection("Users").document(username).set(user);

        Map<String, List<String>> map = new HashMap<>();
        map.put("likeList",new ArrayList<>());
        db.collection("likes").document(username).set(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("cartproducts",new ArrayList<CartProduct>());
        db.collection("cart").document(username).set(map1);
    }

    public void isLoginValid(FireStoreCallback fireStoreCallback,String username, String password){
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                   boolean isValid = documentSnapshot.toObject(User.class).getPassword().equals(PasswordEncripter.hashPassword(password));
                    fireStoreCallback.Callback((Boolean)isValid);
               }
            }
       });
    }
}