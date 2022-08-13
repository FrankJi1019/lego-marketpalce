package com.example.se306project1.database;

import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ProductDatabase {

     FirebaseFirestore db = FirebaseFirestore.getInstance();
     private static ProductDatabase productDatabase;

     public static ProductDatabase getInstance(){
          if(productDatabase==null){
               productDatabase = new ProductDatabase();
          }
          return productDatabase;
     }

     //use to add the product into firestore database at the first time
     public void addProductToDb(IProduct product){
          db.collection("Products").document(product.getName()).set(product);
     }

     //use to delete the product from the Products collections
     public void deleteProductFromDb(IProduct product){
          db.collection("Products").document(product.getName()).delete();
     }

     //update the product's specific field's value to new value
     //As the product name is key value,therefore,we just need the product name
     public <T> void updateProductInfo(String productName,String fieldName,T value){
          DocumentReference prod = db.collection("Products").document(productName);
          prod.update(fieldName,value);
     }

     //use to update the database data for the number which can increment
     //such as we can add the product's price 1 .
     //for this method the type T must be int,double or long
     public <T> void updateIncrement(String productName,String fieldName,T step){
          DocumentReference products = db.collection("Products").document(productName);
          products.update(fieldName, FieldValue.increment((double)step));
     }

     //use to update the database data for the number which can decrement
     //such as we can substract the product's price 1 .
     //for this method the type T must be int,double or long
     public <T> void updateDecrement(String productName,String fieldName,T step){
          DocumentReference products = db.collection("Products").document(productName);
          products.update(fieldName, FieldValue.increment((double)step*(-1)));
     }

     //get all products in database using the call back
     public void getAllProducts(FireStoreCallback fireStoreCallback){
          List<IProduct> products = new ArrayList<>();
          db.collection("Products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
               @Override
               public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot ds:documents){
                         if(ds.exists()){
                              IProduct product = ds.toObject(Product.class);
                              products.add(product);
                         }
                    }
                    fireStoreCallback.Callback(products);
               }
          });
     }

     //get the specific product in db according to its name using call back
     public void getSpecificProduct(FireStoreCallback fireStoreCallback,String productName){
          IProduct product1 = new Product();
          db.collection("Products").document(productName).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
               @Override
               public void onSuccess(DocumentSnapshot documentSnapshot) {
                    IProduct product = documentSnapshot.toObject(Product.class);
                    fireStoreCallback.Callback(product);
               }
          });
     }

     //get the all products of specific category according to its title directly from database
     public void getAllProductsByCategoryTitle(FireStoreCallback fireStoreCallback,String categorytitle){
          List<IProduct> products = new ArrayList<>();
          db.collection("Products")
                  .whereEqualTo("categoryTitle",categorytitle)
                  .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                       @Override
                       public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot doc:documents){
                                 if(doc.exists()){
                                      IProduct product = doc.toObject(Product.class);
                                      products.add(product);
                                 }
                            }
                            fireStoreCallback.Callback(products);
                       }
                  });
     }

     //already get the all products,just select the products which categorytitle which equal to the parameter
     public List<IProduct> getCatagoryProducts(List<IProduct> allProducts,String categoryTitle){
           List<IProduct> res = new ArrayList<>();
           for(int i=0;i<allProducts.size();i++){
                if(allProducts.get(i).getCategoryTitle()==categoryTitle){
                     res.add(allProducts.get(i));
                }
           }
           return res;
     }



     //sort product descend according to price
     public void sortDescendByPrice(List<IProduct> products){
          Collections.sort(products, new Comparator<IProduct>() {
               @Override
               public int compare(IProduct p1, IProduct p2) {
                    return (int)(p2.getPrice()-p1.getPrice());
               }
          });
     }

     //sort product ascend according to price
     public void sortAscendByPrice(List<IProduct> products){
          Collections.sort(products, new Comparator<IProduct>() {
               @Override
               public int compare(IProduct p1, IProduct p2) {
                    return (int)(p1.getPrice()-p2.getPrice());
               }
          });
     }

     //sort product descend according to likes number
     public void sortDescendByLikes(List<IProduct> products){
          Collections.sort(products, new Comparator<IProduct>() {
               @Override
               public int compare(IProduct p1, IProduct p2) {
                    return (p2.getLikesNumber()-p1.getLikesNumber());
               }
          });
     }

     //sort product ascend according to likes number
     public void sortAscendByLikes(List<IProduct> products){
          Collections.sort(products, new Comparator<IProduct>() {
               @Override
               public int compare(IProduct p1, IProduct p2) {
                    return (p1.getLikesNumber()-p2.getLikesNumber());
               }
          });
     }

     //already get all products from the database,and then select the product name
     //which contains the keyword
     public List<IProduct> getProductsBySearch(List<IProduct> allProducts,String keyword){
          List<IProduct> res = new ArrayList<>();
          for(int i=0;i<allProducts.size();i++){
               if(allProducts.get(i).getName().toLowerCase().indexOf(keyword.toLowerCase())!=-1){
                    res.add(allProducts.get(i));
               }
          }
          return res;
     }
}
