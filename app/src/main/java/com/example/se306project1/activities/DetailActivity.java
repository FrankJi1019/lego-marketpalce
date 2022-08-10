package com.example.se306project1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.se306project1.R;
import com.example.se306project1.adapters.DetailAdapter;
import com.example.se306project1.adapters.SuggestionAdapter;
import com.example.se306project1.dataproviders.DataProvider;
import com.example.se306project1.models.IProduct;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    ViewHolder viewHolder;
    AppBarViewHolder appBarViewHolder;
    List<Integer> imageList;

    private ArrayList<IProduct> allProducts;

    SuggestionAdapter suggestionAdapter;

    class ViewHolder{
        private final ViewPager viewPager = findViewById(R.id.viewPager);
    }

    class AppBarViewHolder {
        private final Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        private final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        private final NavigationView navigationView = (NavigationView) findViewById(R.id.app_drawer_navigation);
        private final RecyclerView suggestionListRecycler = (RecyclerView) findViewById(R.id.suggestion_recyclerview);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        viewHolder = new ViewHolder();
        appBarViewHolder = new AppBarViewHolder();
        imageList = new ArrayList<>();
        this.allProducts = new ArrayList<>();

        this.fillImage();
        this.fillProductSearchList();
        DetailAdapter detailAdapter = new DetailAdapter(imageList);
        viewHolder.viewPager.setAdapter(detailAdapter);

        this.setAppBar();
        this.setSuggestionAdapter();
    }

    public void fillImage(){
        imageList.add(R.drawable.image_placeholder);
        imageList.add(R.drawable.image_placeholder);
        imageList.add(R.drawable.image_placeholder);
    }

    // click event handler for the menu icon in the app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            appBarViewHolder.drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAppBar() {
        setSupportActionBar(appBarViewHolder.toolbar);
        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.menu);
        tb.setTitle("MyMusic");
        tb.setDisplayHomeAsUpEnabled(true);

        // click event handler for the items in the navigation
        appBarViewHolder.navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        appBarViewHolder.drawerLayout.closeDrawers();
                        appBarViewHolder.drawerLayout.setSelected(true);
                        return true;
                    }
                });

        getSupportActionBar().setTitle(R.string.app_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search with keyword...");
        Context that = this;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                suggestionAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void setSuggestionAdapter() {
        this.suggestionAdapter = new SuggestionAdapter(allProducts);
        this.appBarViewHolder.suggestionListRecycler.setAdapter(suggestionAdapter);
        this.appBarViewHolder.suggestionListRecycler.setLayoutManager(
                new LinearLayoutManager(
                        getApplicationContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                )
        );
        this.appBarViewHolder.suggestionListRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    public void fillProductSearchList() {
        this.allProducts = DataProvider.getIProductList(10);
    }
}