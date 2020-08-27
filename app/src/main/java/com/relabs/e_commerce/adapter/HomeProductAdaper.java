package com.relabs.e_commerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.relabs.e_commerce.R;
import com.relabs.e_commerce.model.Product;
import com.relabs.e_commerce.util.Common;

public class HomeProductAdaper extends RecyclerView.Adapter<HomeProductAdaper.ProductViewHolder> {
    private List<Product> newProductList = new ArrayList<>();
    onItemClickListener listener;
    Context context;

    public HomeProductAdaper(Context context) {
        this.context = context;
    }

    public void updateList(List<Product> ProductList) {
        newProductList.clear();
        newProductList.addAll(ProductList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_home_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        ImageView imageView =holder.itemView.findViewById(R.id.image_product_home);
        TextView textView=holder.itemView.findViewById(R.id.text_product_home_name);
        if(context!= null){
            Glide.with(context)
                    .load(Common.PRODUCT_URL+newProductList.get(position).image)
                    .fitCenter()
                    .into(imageView);
        }
        textView.setText(newProductList.get(position).title);
    }

    @Override
    public int getItemCount() {
        return newProductList.size();
    }

    public void setList(List<Product> ProductList) {
        this.newProductList = ProductList;
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(newProductList.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Product item);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
