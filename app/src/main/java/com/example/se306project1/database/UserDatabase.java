package com.example.se306project1.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.se306project1.models.CartProduct;
import com.example.se306project1.models.IUser;
import com.example.se306project1.models.User;

import com.example.se306project1.utilities.PasswordEncripter;
import com.google.android.gms.tasks.OnFailureListener;
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
    private ArrayList<String> inValidUsername = new ArrayList<String>();
    private static boolean isValid, dataAvailable;
    private UserDatabase(){}

    public static UserDatabase getInstance(){
        if (userDatabase == null){
            userDatabase = new UserDatabase();
        }
        return userDatabase;
    }

    public boolean isUserExist(String username){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    inValidUsername.add(documentSnapshot.toObject(User.class).getUsername());
                }
            }
        });
        docRef.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("ERROR", String.valueOf( inValidUsername.contains(username)));
            }
        });
        return inValidUsername.contains(username);
   }

    public void addUserToFireStore(String username, String password){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = new User(username,password);
        db.collection("Users").document(username).set(user);

        Map<String, List<String>> map = new HashMap<>();
        map.put("likeList",new ArrayList<>());
        db.collection("likes").document(username).set(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("cartproducts",new ArrayList<CartProduct>());
        db.collection("cart").document(username).set(map1);
    }

//    public void isLoginValid(String username, String password){
//        checkLoginValid(username, password);
//        while(dataAvailable){
//            return isValid;
//        }
//    }

    public boolean isLoginValid(String username, String password){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    isValid = documentSnapshot.toObject(User.class).getPassword().equals(PasswordEncripter.hashPassword(password));
                    dataAvailable = true;
                }
            }
        });
        docRef.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("ERROR", String.valueOf(isValid));
            }
        });
        return true;
    }

}
