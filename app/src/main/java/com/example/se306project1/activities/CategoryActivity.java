package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.se306project1.R;
import com.example.se306project1.adapters.CategoryAdapter;
import com.example.se306project1.models.Category;
import com.example.se306project1.models.Category1;
import com.example.se306project1.models.Category2;
import com.example.se306project1.models.Category3;
import com.example.se306project1.models.ICategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private List<ICategory> categories;

    ViewHolder viewHolder;

    class ViewHolder {
        private final RecyclerView categoryRecyclerView = findViewById(R.id.category_recycler_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        viewHolder = new ViewHolder();
        this.categories = new ArrayList<>();
        this.fillCategories();
        this.setAdapter();
    }

    public void setAdapter() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(this.categories);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        this.viewHolder.categoryRecyclerView.setLayoutManager(layoutManager);
        this.viewHolder.categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.viewHolder.categoryRecyclerView.setAdapter(categoryAdapter);
    }


    public void fillCategories() {
        Category category1 = new Category1();
        Category category2 = new Category2();
        Category category3 = new Category3();

        category1.setId(1);
        category2.setId(2);
        category3.setId(3);

        category1.setTitle("Colosseum1");
        category2.setTitle("Colosseum2");
        category3.setTitle("Colosseum3");

        category1.setImage("image_placeholder.png");
        category1.setImage("image_placeholder.png");
        category1.setImage("image_placeholder.png");
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);

    }

}
