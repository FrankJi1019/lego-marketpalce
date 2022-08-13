package com.example.se306project1.database;

import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikesDatabase extends ProductDatabase{

     FirebaseFirestore db = FirebaseFirestore.getInstance();
     private static LikesDatabase likesDatabase;

     public static LikesDatabase getInstance(){
         if(likesDatabase == null){
             likesDatabase = new LikesDatabase();
         }
         return likesDatabase;
     }

     public void addProductToLikesList(String userName,String productName){
         DocumentReference likes = db.collection("likes").document(userName);
         likes.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
             @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {
                  if(documentSnapshot.exists()){
                      likes.update("likeList", FieldValue.arrayUnion(productName));
                  }else{
                      Map<String, List<String>> map = new HashMap<>();
                      List<String> likelist = new ArrayList<>();
                      likelist.add(productName);
                      map.put("likeList",likelist);
                      likes.set(map);
                  }
             }
         });
     }

     public void removeProductFromLikesList(String userName,String productName){
         DocumentReference likes = db.collection("likes").document(userName);
         likes.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
             @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {
                 if(!documentSnapshot.exists()){
                     return;
                 }else{
                     likes.update("likeList",FieldValue.arrayRemove(productName));
                 }
             }
         });
     }

     public void getUsersAllLikes(FireStoreCallback fireStoreCallback,String username,List<IProduct> products){
         List<IProduct> tt = new ArrayList<>();
           db.collection("likes").document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
               @Override
               public void onSuccess(DocumentSnapshot documentSnapshot) {
                   List<String> likeList = (List<String>)documentSnapshot.get("likeList");
                   if(likeList == null){
                       tt.add(new Product());
                   }else{
                       for(int i=0;i<products.size();i++){
                           if(likeList.contains(products.get(i).getName())){
                               tt.add(products.get(i));
                           }
                       }
                   }

                   fireStoreCallback.Callback(tt);
               }
           });
     }

}
