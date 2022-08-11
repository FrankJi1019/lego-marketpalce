package com.example.se306project1.database;

import com.example.se306project1.models.IUser;
import com.example.se306project1.models.User;

import com.example.se306project1.utilities.PasswordEncripter;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserDatabase implements IUserDatabase{
    private static UserDatabase userDatabase = null;
    private ArrayList<String> inValidUsername = new ArrayList<String>();
    private static boolean isValid;
    private static String password;
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
        return inValidUsername.contains(username);
   }

    public void addUserToFireStore(String username, String password){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = new User(username,password);
        db.collection("Users").document(username).set(user);
    }

     public boolean isLoginValid(String username, String password){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    isValid = documentSnapshot.toObject(User.class).getPassword().equals(PasswordEncripter.hashPassword(password));
                }
            }
        });
        return isValid;
    }
}
