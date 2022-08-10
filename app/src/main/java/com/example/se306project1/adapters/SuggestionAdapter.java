package com.example.se306project1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.dataproviders.DataProvider;
import com.example.se306project1.models.IProduct;

import java.util.ArrayList;
import java.util.Locale;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder>
        implements Filterable {

    private ArrayList<IProduct> allProducts = new ArrayList<>();
    private ArrayList<IProduct> filteredProducts = new ArrayList<>();
    private ArrayList<IProduct> emptyProductList = new ArrayList<>();

    class SuggestionViewHolder extends RecyclerView.ViewHolder {
        private TextView suggestedNameTextView;

        public SuggestionViewHolder(View view) {
            super(view);
            this.suggestedNameTextView = view.findViewById(R.id.suggested_product_name_textview);
        }
    }

    public SuggestionAdapter(ArrayList<IProduct> products) {
        this.allProducts = products;
    }

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggestion_item, parent, false);
        return new SuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
        holder.suggestedNameTextView.setText(this.filteredProducts.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.filteredProducts.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchText = charSequence.toString();
                if (searchText.isEmpty()) {
                    filteredProducts.clear();
                } else {
                    for (IProduct product : allProducts) {
                        if (product.getName().toLowerCase().contains(searchText.toLowerCase())) {
                            filteredProducts.add(product);
                        }
                    }
                }
                if (!searchText.isEmpty() && filteredProducts.isEmpty()) {
                    IProduct product = DataProvider.getIProduct();
                    product.setName("No Result");
                    filteredProducts.add(product);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredProducts;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredProducts = (ArrayList<IProduct>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
