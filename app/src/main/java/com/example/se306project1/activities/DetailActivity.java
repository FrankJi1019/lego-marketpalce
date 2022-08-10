package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.se306project1.R;
import com.example.se306project1.adapters.DetailAdapter;
import com.example.se306project1.dataproviders.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    class ViewHolder{
        private final ViewPager viewPager = findViewById(R.id.viewPager);
    }
    
    List<Integer> imageList;

    ViewHolder viewHolder;
    Drawer drawer;
    ProductSearcher productSearcher;

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
}