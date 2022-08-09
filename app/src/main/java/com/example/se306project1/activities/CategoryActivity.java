package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.se306project1.R;
import com.example.se306project1.adapters.CategoryAdapter;
import com.example.se306project1.adapters.TopPickAdapter;
import com.example.se306project1.models.Category;
import com.example.se306project1.models.Category1;
import com.example.se306project1.models.Category2;
import com.example.se306project1.models.Category3;
import com.example.se306project1.models.ICategory;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private List<ICategory> categories;
    private ArrayList<IProduct> products;

    ViewHolder viewHolder;

    class ViewHolder {
        private final RecyclerView categoryRecyclerView = findViewById(R.id.category_recycler_view);
        private final RecyclerView topPickRecyclerView = findViewById(R.id.top_pick_product_recycler_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        viewHolder = new ViewHolder();
        this.categories = new ArrayList<>();
        this.products = new ArrayList<>();
        this.fillTopPicks();
        this.fillCategories();
        this.setAdapter();
    }

    public void setAdapter() {
        TopPickAdapter topPickAdapter = new TopPickAdapter(this.products);
        RecyclerView.LayoutManager topPickLayoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        this.viewHolder.topPickRecyclerView.setLayoutManager(topPickLayoutManager);
        this.viewHolder.topPickRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.viewHolder.topPickRecyclerView.setAdapter(topPickAdapter);

        CategoryAdapter categoryAdapter = new CategoryAdapter(this.categories);
        RecyclerView.LayoutManager categoryLayoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        this.viewHolder.categoryRecyclerView.setLayoutManager(categoryLayoutManager);
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

    public void fillTopPicks() {
        Product product = new Product();
        product.setId(1);
        product.setCategoryId(1);
        product.setName("Colosseum");
        product.setDescription("Build and discover the Taj Mahal! The huge ivory-white marble mausoleum, renowned as one of the world’s architectural wonders, was commissioned in 1631 by the Emperor Shah Jahan in memory of his wife, the Empress Mumtaz Mahal. This relaunched 2008 LEGO® Creator Expert interpretation features the structure's 4 facades with sweeping arches, balconies and arched windows. The central dome, subsidiary domed chambers and surrounding minarets are topped with decorative finials, and the raised platform is lined with recessed arches.");
        product.setPrice(199.90);
        product.setStock(0);
        List<String> images = new ArrayList<>();
        images.add("image_placeholder.png");
        product.setImages(images);
        for (int i = 0; i < 5; i++) {
            this.products.add(product);
        }
    }

}
