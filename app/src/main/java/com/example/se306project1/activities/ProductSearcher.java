package com.example.se306project1.activities;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.adapters.SuggestionAdapter;
import com.example.se306project1.models.IProduct;

import java.util.List;

public class ProductSearcher {

    class ViewHolder {
        private RecyclerView suggestionListRecycler;
    }

    private List<IProduct> allProducts;
    private AppCompatActivity activity;
    private ViewHolder viewHolder;
    private SuggestionAdapter suggestionAdapter;

    public ProductSearcher(AppCompatActivity activity, List<IProduct> searchPool) {
        this.activity = activity;
        this.viewHolder = new ViewHolder();
        this.viewHolder.suggestionListRecycler = (RecyclerView) this.activity.findViewById(R.id.suggestion_recyclerview);
        this.allProducts = searchPool;
    }

    public void initialise() {
        this.suggestionAdapter = new SuggestionAdapter(allProducts);
        this.viewHolder.suggestionListRecycler.setAdapter(this.suggestionAdapter);
        this.viewHolder.suggestionListRecycler.setLayoutManager(
                new LinearLayoutManager(
                        this.activity.getApplicationContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                )
        );
        this.viewHolder.suggestionListRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    public boolean onCreateOptionsMenu(Menu menu, boolean superValue) {
        this.activity.getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search with keyword...");
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
        return superValue;
    }
}
