package com.example.se306project1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.se306project1.R;
import com.example.se306project1.adapters.DetailAdapter;
import com.example.se306project1.database.FireStoreCallback;
import com.example.se306project1.database.LikesDatabase;
import com.example.se306project1.database.ProductDatabase;
import com.example.se306project1.dataproviders.DataProvider;
import com.example.se306project1.statemanagement.CartState;
import com.example.se306project1.utilities.UserState;
import com.google.android.material.navigation.NavigationView;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    class ViewHolder {
        private final ViewPager viewPager = findViewById(R.id.viewPager);
        private final Button likeButton = findViewById(R.id.like_button);
        private final Button unlikeButton = findViewById(R.id.unlike_button);
        private final TextView name = findViewById(R.id.detail_name_textView);
        private final TextView stock = findViewById(R.id.stockNumber);
        private final TextView price = findViewById(R.id.price);
        private final TextView decription = findViewById(R.id.description);
    }

    List<Integer> imageList;
    String productName;

    ViewHolder viewHolder;
    Drawer drawer;
    ProductSearcher productSearcher;
    DetailAdapter detailAdapter;

    public static void start(AppCompatActivity activity) {
        Intent intent = new Intent(activity.getBaseContext(), DetailActivity.class);
        activity.startActivity(intent);
    }

    public static void startWithName(AppCompatActivity activity, String name) {
        Intent intent = new Intent(activity.getBaseContext(), DetailActivity.class);
        intent.putExtra("name", name);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.viewHolder = new ViewHolder();
        this.imageList = new ArrayList<>();
        this.drawer = new Drawer(this);
        this.productSearcher = new ProductSearcher(this);

//        List<IProduct> res = ProductData.getAllProducts();
//        ProductDatabase db = ProductDatabase.getInstance();
//        for (int i = 0; i < res.size(); i++) {
//            db.addProductToDb(res.get(i));
//        }

//        this.fillImage();

//        ldb.getUsersAllLikes(new FireStoreCallback() {
//            @Override
//            public <T> void Callback(T value) {
//                List<IProduct> products = (List<IProduct>) value;
//                for(int i=0;i<products.size();i++){
//                    System.out.println(products.get(i).getName());
//                }
//                System.out.println("hello");
//            }
//        },"qingyang");


//        ProductDatabase db = ProductDatabase.getInstance();
//        db.getSpecificProduct(new FireStoreCallback() {
//            @Override
//            public <T> void Callback(T value) {
//                IProduct product = (IProduct) value;
//                fetchDataAndSetAdapter(product);
//            }
//        },"Ferrari");

        this.drawer.initialise();
        this.productSearcher.initialise();
        this.productName = getIntent().getStringExtra("name");

        if (UserState.getInstance().hasLiked(productName)) {
            this.viewHolder.unlikeButton.setVisibility(View.VISIBLE);
            this.viewHolder.likeButton.setVisibility(View.INVISIBLE);
        } else {
            this.viewHolder.likeButton.setVisibility(View.VISIBLE);
            this.viewHolder.unlikeButton.setVisibility(View.INVISIBLE);
        }

        ProductDatabase db = ProductDatabase.getInstance();
        db.getSpecificProduct(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                IProduct product = (IProduct) value;
                fetchDataAndSetAdapter(product);
            }
        },productName);
    }

    public void fillImage() {
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return this.drawer.onNavigationItemSelected(item, true);
    }

    public void onGoBack(View view) {
        finish();
    }

    public void onAddToCart(View view) {
        ProductDatabase productDatabase = ProductDatabase.getInstance();
        productDatabase.getSpecificProduct(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                IProduct product = (Product) value;
                CartState.getCartState().addToCart(product.toCartProduct());
            }
        }, productName);
    }

    public void onToggleLike(View view) {
        view.setVisibility(View.INVISIBLE);
        if (view.getId() == R.id.unlike_button) {
            this.viewHolder.likeButton.setVisibility(View.VISIBLE);
            LikesDatabase db = LikesDatabase.getInstance();
            db.removeProductFromLikesList(UserState.getInstance().getCurrentUser().getUsername(),productName);
            ProductDatabase productDatabase = ProductDatabase.getInstance();
            productDatabase.updateIncrement(productName, "likesNumber", -1);
            IProduct product = new Product();
            product.setName(productName);
            UserState.getInstance().unlike(product);
        } else {
            this.viewHolder.unlikeButton.setVisibility(View.VISIBLE);
            LikesDatabase db = LikesDatabase.getInstance();
            db.addProductToLikesList(UserState.getInstance().getCurrentUser().getUsername(),productName);
            ProductDatabase productDatabase = ProductDatabase.getInstance();
            productDatabase.updateIncrement(productName, "likesNumber", 1);
            IProduct product = new Product();
            product.setName(productName);
            UserState.getInstance().like(product);
        }
    }
    

    public void fetchDataAndSetAdapter(IProduct product) {

        List<Integer> imageList = product.getImages();
        detailAdapter = new DetailAdapter(imageList);
        viewHolder.viewPager.setAdapter(detailAdapter);
        viewHolder.name.setText(product.getName());
        viewHolder.stock.setText(product.getStock() + "");
        viewHolder.price.setText("$" + product.getPrice());
        viewHolder.decription.setText(product.getDescription());

    }
}