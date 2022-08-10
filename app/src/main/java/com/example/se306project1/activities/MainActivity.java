package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.se306project1.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

}