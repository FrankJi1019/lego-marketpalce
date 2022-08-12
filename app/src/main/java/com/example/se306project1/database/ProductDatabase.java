package com.example.se306project1.database;

import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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

     //get all products in database
     public List<IProduct> getAllProducts(){
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
               }
          });
          return products;
     }

     //get the specific product in db according to its name
     public IProduct getSpecificProduct(String productName){
          final IProduct[] product1 = new IProduct[1];
          db.collection("Products").document(productName).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
               @Override
               public void onSuccess(DocumentSnapshot documentSnapshot) {
                    IProduct product = documentSnapshot.toObject(Product.class);
                    product1[0] = product;
               }
          });
          return product1[0];
     }

}
