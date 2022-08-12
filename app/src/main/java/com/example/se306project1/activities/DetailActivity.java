package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.se306project1.R;
import com.example.se306project1.adapters.DetailAdapter;
import com.example.se306project1.database.ProductDatabase;
import com.example.se306project1.dataproviders.DataProvider;
import com.example.se306project1.dataproviders.ProductData;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    class ViewHolder{
        private final ViewPager viewPager = findViewById(R.id.viewPager);
        private final TextView  name = findViewById(R.id.detail_name_textView);
        private final TextView  stock = findViewById(R.id.stockNumber);
        private final TextView price = findViewById(R.id.price);
        private final TextView decription = findViewById(R.id.description);
    }
    
    List<Integer> imageList;

    ViewHolder viewHolder;
    Drawer drawer;
    ProductSearcher productSearcher;
    DetailAdapter detailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.viewHolder = new ViewHolder();
        this.imageList = new ArrayList<>();
        this.drawer = new Drawer(this);
        this.productSearcher = new ProductSearcher(this);

//        List<IProduct> res=ProductData.getAllProducts();
//        ProductDatabase db=ProductDatabase.getInstance();
//        for(int i=0;i<res.size();i++){
//            db.addProductToDb(res.get(i));
//        }


//        this.fillImage();
        fetchData();

        this.drawer.initialise();
        this.productSearcher.initialise();
    }

    public void fillImage(){
        imageList = DataProvider.getImageList(3);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return this.drawer.setUp(item, super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return this.productSearcher.onCreateOptionsMenu(menu, super.onCreateOptionsMenu(menu));
    }

    public void fetchData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products").document("HoverCraft").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Product product = documentSnapshot.toObject(Product.class);
                    List<Integer> imageList = product.getImages();
                    detailAdapter = new DetailAdapter(imageList);
                    viewHolder.viewPager.setAdapter(detailAdapter);
                    viewHolder.name.setText(product.getName());
                    viewHolder.stock.setText(product.getStock()+"");
                    viewHolder.price.setText("$"+product.getPrice());
                    viewHolder.decription.setText(product.getDescription());
                }
            }
        });
    }
}