package com.example.se306project1.activities;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.se306project1.R;
import com.google.android.material.navigation.NavigationView;

public class Drawer {

    class DrawerViewHolder {
        private Toolbar toolbar;
        private DrawerLayout drawerLayout;
        private NavigationView navigationView;
    }

    DrawerViewHolder drawerViewHolder;
    AppCompatActivity activity;

    public Drawer(AppCompatActivity activity) {
        this.activity = activity;
        this.drawerViewHolder = new DrawerViewHolder();
        this.drawerViewHolder.toolbar = (Toolbar) this.activity.findViewById(R.id.app_toolbar);
        this.drawerViewHolder.drawerLayout = (DrawerLayout) this.activity.findViewById(R.id.drawerlayout);
        this.drawerViewHolder.navigationView = (NavigationView) this.activity.findViewById(R.id.app_drawer_navigation);
    }

    public void initialise() {
        this.activity.setSupportActionBar(this.drawerViewHolder.toolbar);
        ActionBar tb = this.activity.getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.menu);
        tb.setTitle(R.string.app_title);
        tb.setDisplayHomeAsUpEnabled(true);
        // click event handler for the items in the navigation
        this.drawerViewHolder.navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        drawerViewHolder.drawerLayout.closeDrawers();
                        drawerViewHolder.drawerLayout.setSelected(true);
                        return true;
                    }
                });
        for (Class c: this.activity.getClass().getInterfaces()) {
            if (c.equals(NavigationView.OnNavigationItemSelectedListener.class)) {
                this.drawerViewHolder.navigationView.setNavigationItemSelectedListener(
                        (NavigationView.OnNavigationItemSelectedListener) this.activity
                );
                break;
            }
        }

    }

    public boolean setUp(MenuItem item, boolean toBeReturned) {
        if (item.getItemId() == android.R.id.home) {
            this.drawerViewHolder.drawerLayout.openDrawer(GravityCompat.START);
        }
        return toBeReturned;
    }

    public boolean onNavigationItemSelected(MenuItem item, boolean toBeReturned) {
        if (item.getItemId() ==  R.id.nav_homepage && !this.activity.getClass().equals(CategoryActivity.class)) {
            CategoryActivity.start(this.activity);
        } else if (item.getItemId() ==  R.id.nav_likes && !this.activity.getClass().equals(ProductActivity.class)) {
            ProductActivity.start(this.activity);
        } else if (item.getItemId() ==  R.id.nav_cart && !this.activity.getClass().equals(CategoryActivity.class)) {
            CartActivity.start(this.activity);
        } else if (item.getItemId() ==  R.id.nav_technic && !this.activity.getClass().equals(ProductActivity.class)) {
            ProductActivity.start(this.activity);
        } else if (item.getItemId() ==  R.id.nav_starwar && !this.activity.getClass().equals(ProductActivity.class)) {
            ProductActivity.start(this.activity);
        } else if (item.getItemId() ==  R.id.nav_friends && !this.activity.getClass().equals(ProductActivity.class)) {
            ProductActivity.start(this.activity);
        } else if (item.getItemId() ==  R.id.nav_logout) {
            Toast.makeText(this.activity, "log out", Toast.LENGTH_SHORT).show();
        }
        this.drawerViewHolder.drawerLayout.closeDrawer(GravityCompat.START);
        return toBeReturned;
    }

}
