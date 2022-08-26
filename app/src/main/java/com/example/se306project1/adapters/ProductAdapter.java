package com.example.se306project1.adapters;

import android.annotation.SuppressLint;
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
import com.example.se306project1.activities.DetailActivity;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;
import com.example.se306project1.utilities.ActivityState;
import com.example.se306project1.utilities.AnimationFactory;
import com.example.se306project1.utilities.UserState;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView productNameTextview,price_textview, likeCountTextview;
        private final TextView inStockTextview, lowStockTextview, noStockTextview;
        private final MaterialButton likeButton, unlikeButton;
        private final ImageView product_image;
        private final CardView container;
        public ProductViewHolder(final View view) {
            super(view);
            this.productNameTextview = view.findViewById(R.id.product_name_textview);
            this.likeButton = view.findViewById(R.id.like_button);
            this.unlikeButton = view.findViewById(R.id.unlike_button);
            this.product_image = view.findViewById(R.id.product_imageview);
            this.price_textview = view.findViewById(R.id.price_textview);
            this.container = view.findViewById(R.id.product_item_container);
            this.inStockTextview = view.findViewById(R.id.in_stock_textview);
            this.lowStockTextview = view.findViewById(R.id.low_stock_textview);
            this.noStockTextview = view.findViewById(R.id.no_stock_textview);
            this.likeCountTextview = view.findViewById(R.id.like_count_textview);
        }
    }

    private final List<IProduct> products;

    public ProductAdapter(List<IProduct> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productListItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ProductViewHolder(productListItem);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        IProduct product = this.products.get(position);
        holder.productNameTextview.setText(product.getName());
        this.setLikeButtons(holder.unlikeButton, holder.likeButton, position);
        this.setLikeAction(
                holder.likeButton,
                holder.unlikeButton,
                holder.likeCountTextview,
                position
        );
        holder.product_image.setImageResource(product.getImages().get(0));
        holder.price_textview.setText("$" + String.format("%.2f", product.getPrice()));
        holder.container.setOnClickListener(view -> {
            DetailActivity.startWithName(
                    ActivityState.getInstance().getCurrentActivity(),
                    this.products.get(position).getName()
            );
        });
        holder.likeCountTextview.setText(product.getLikesNumber() + "");
        this.setStockState(holder.noStockTextview, holder.lowStockTextview, holder.inStockTextview, position);
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    private void setLikeButtons(MaterialButton unlikeButton, MaterialButton likeButton, int position) {
        IProduct product = this.products.get(position);
        setUnlikeButtonIcon(unlikeButton, position);
        if (UserState.getInstance().hasLiked(product.getName())) {
            likeButton.setVisibility(View.INVISIBLE);
            unlikeButton.setVisibility(View.VISIBLE);
        } else {
            unlikeButton.setVisibility(View.INVISIBLE);
            likeButton.setVisibility(View.VISIBLE);
        }
    }

    private void setUnlikeButtonIcon(MaterialButton unlikeButton, int position) {
        IProduct product = this.products.get(position);
        if (product.getCategoryTitle().equals("technic")) {
            unlikeButton.setIconResource(R.drawable.technic_icon);
            unlikeButton.setIconTintResource(R.color.technic);
        } else if (product.getCategoryTitle().equals("star war")) {
            unlikeButton.setIconResource(R.drawable.starwar_icon);
            unlikeButton.setIconTintResource(R.color.star_war);
        } else {
            unlikeButton.setIconResource(R.drawable.city_icon);
            unlikeButton.setIconTintResource(R.color.city);
        }
    }

    private void setLikeAction(
            MaterialButton likeButton,
            MaterialButton unlikeButton,
            TextView likeCountTextView,
            int position
    ) {
        likeButton.setOnClickListener(view -> {
            view.setVisibility(View.INVISIBLE);
            unlikeButton.setVisibility(View.VISIBLE);
            UserState.getInstance().like(this.products.get(position).getName());
            int initialLike = Integer.parseInt(
                    likeCountTextView.getText().toString().replace(" people liked", "")
            );
            likeCountTextView.setText(initialLike + 1 + "");
            products.get(position).setLikesNumber(initialLike + 1);
        });
        unlikeButton.setOnClickListener(view -> {
            view.setVisibility(View.INVISIBLE);
            likeButton.setVisibility(View.VISIBLE);
            UserState.getInstance().unlike(this.products.get(position).getName());
            int initialLike = Integer.parseInt(
                    likeCountTextView.getText().toString()
            );
            likeCountTextView.setText(initialLike - 1 + "");
            products.get(position).setLikesNumber(initialLike - 1);
        });
    }

    private void setStockState(
            TextView noStockTextView,
            TextView lowStockTextView,
            TextView inStockTextView,
            int position
    ) {
        if (this.products.get(position).getStock() == 0) {
            lowStockTextView.setVisibility(View.INVISIBLE);
            inStockTextView.setVisibility(View.INVISIBLE);
            noStockTextView.setVisibility(View.VISIBLE);
        } else if (this.products.get(position).getStock() <= Product.LOW_STOCK_BOUNDARY) {
            noStockTextView.setVisibility(View.INVISIBLE);
            inStockTextView.setVisibility(View.INVISIBLE);
            lowStockTextView.setVisibility(View.VISIBLE);
        } else {
            noStockTextView.setVisibility(View.INVISIBLE);
            lowStockTextView.setVisibility(View.INVISIBLE);
            inStockTextView.setVisibility(View.VISIBLE);
        }
    }

}
