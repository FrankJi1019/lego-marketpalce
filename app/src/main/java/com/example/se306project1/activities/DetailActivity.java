package com.example.se306project1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.se306project1.R;
import com.example.se306project1.adapters.DetailAdapter;
import com.example.se306project1.dataproviders.DataProvider;
import com.example.se306project1.models.CartProduct;
import com.example.se306project1.statemanagement.CartState;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    class ViewHolder{
        private final ViewPager viewPager = findViewById(R.id.viewPager);
    }
    
    List<Integer> imageList;

    ViewHolder viewHolder;
    Drawer drawer;
    ProductSearcher productSearcher;

    public static void start(AppCompatActivity activity) {
        Intent intent = new Intent(activity.getBaseContext(), DetailActivity.class);
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

        this.fillImage();
        DetailAdapter detailAdapter = new DetailAdapter(imageList);
        viewHolder.viewPager.setAdapter(detailAdapter);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return this.drawer.onNavigationItemSelected(item, true);
    }

    public void onGoBack(View view) {
        finish();
    }

    public void onAddToCart(View view) {
        CartProduct product = DataProvider.getIProduct().toCartProduct();
        CartState.getCartState().addToCart(product);
    }
}