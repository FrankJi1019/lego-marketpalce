package com.example.se306project1.adapters;


import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.activities.DetailActivity;
import com.example.se306project1.models.IProduct;

        import java.util.List;

public class TopPickAdapter extends RecyclerView.Adapter<TopPickAdapter.TopPickViewHolder> {

    public class TopPickViewHolder extends RecyclerView.ViewHolder {
        private ImageView topPickImageView;
        private TextView topPickNameTextView;
        private CardView container;
        public TopPickViewHolder(final View view) {
            super(view);
            this.topPickImageView = view.findViewById(R.id.top_pick_imageview);
            this.topPickNameTextView = view.findViewById(R.id.top_pick_text_view);
            this.container = view.findViewById(R.id.top_pick_container);
        }
    }

    private List<IProduct> products;

    private AppCompatActivity activity;

    public TopPickAdapter(AppCompatActivity activity, List<IProduct> products) {
        this.activity = activity;
        this.products = products;
    }

    @NonNull
    @Override
    public TopPickAdapter.TopPickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View topPickLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_pick_layout_view, parent, false);
        return new TopPickViewHolder(topPickLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPickViewHolder holder, int position) {
        IProduct product = this.products.get(position);
        holder.topPickImageView.setImageResource(product.getImages().get(0));
        holder.topPickNameTextView.setText(product.getName());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.startWithName(activity, product.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }
}
