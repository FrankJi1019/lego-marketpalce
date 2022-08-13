package com.example.se306project1.adapters;


import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.models.IProduct;

        import java.util.List;

public class TopPickAdapter extends RecyclerView.Adapter<TopPickAdapter.TopPickViewHolder> {

    public class TopPickViewHolder extends RecyclerView.ViewHolder {
        private ImageView topPickImageView;
        public TopPickViewHolder(final View view) {
            super(view);
            this.topPickImageView = view.findViewById(R.id.top_pick_imageview);
        }
    }

    private List<IProduct> products;

    public TopPickAdapter(List<IProduct> products) {
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
//        String productImage = this.products.get(position).getImages().get(0);;
        IProduct product = this.products.get(position);
        holder.topPickImageView.setImageResource(product.getImages().get(0));
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }
}
