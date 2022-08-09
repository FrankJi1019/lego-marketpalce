package com.example.se306project1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.models.ICategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTitleTextview;
        public CategoryViewHolder(final View view) {
            super(view);
            this.categoryTitleTextview = view.findViewById(R.id.category_title_textview);
        }
    }

    private List<ICategory> categories;

    public CategoryAdapter(List<ICategory> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout_view, parent, false);
        return new CategoryViewHolder(categoryLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String CategoryTitle = this.categories.get(position).getTitle();
        holder.categoryTitleTextview.setText(CategoryTitle);
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }
}
