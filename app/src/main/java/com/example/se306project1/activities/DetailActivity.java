package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.se306project1.R;
import com.example.se306project1.adapters.DetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    ViewHolder viewHolder;
    List<Integer> imageList;

    class ViewHolder{
        private final ViewPager viewPager = findViewById(R.id.viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewHolder = new ViewHolder();
        imageList = new ArrayList<>();
        this.fillImage();
        DetailAdapter detailAdapter = new DetailAdapter(imageList);
        viewHolder.viewPager.setAdapter(detailAdapter);
    }

    public void fillImage(){
        String str = "image_placeholder";
        imageList.add(R.drawable.image_placeholder);
        imageList.add(R.drawable.image_placeholder);
        imageList.add(R.drawable.image_placeholder);
    }
}