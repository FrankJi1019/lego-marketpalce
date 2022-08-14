package com.example.se306project1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.activities.ProductActivity;
import com.example.se306project1.models.ICategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTitleTextview;
        private FloatingActionButton forwardButton;
        private ImageView imageView;
        private CardView cardView;
        public CategoryViewHolder(final View view) {
            super(view);
            this.categoryTitleTextview = view.findViewById(R.id.category_title_textview);
            this.forwardButton = view.findViewById(R.id.forward_arrow_button);
            this.imageView = view.findViewById(R.id.category_imageview);
            this.cardView = view.findViewById(R.id.category_card_view);
        }
    }

    private AppCompatActivity activity;
    private List<ICategory> categories;

    public CategoryAdapter(AppCompatActivity activity, List<ICategory> categories) {
        this.activity = activity;
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
        holder.forwardButton.setOnClickListener(view -> {
            ProductActivity.startWithTheme(this.activity, this.categories.get(position).getTitle());
        });
        holder.cardView.setOnClickListener(view -> {
            ProductActivity.startWithTheme(this.activity, this.categories.get(position).getTitle());
        });
        holder.imageView.setImageResource(this.categories.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }
}
