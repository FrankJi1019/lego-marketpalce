package com.example.se306project1.database;

import com.example.se306project1.models.User;

import com.example.se306project1.utilities.PasswordEncripter;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

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