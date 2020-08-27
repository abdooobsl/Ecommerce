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
import com.relabs.e_commerce.model.Category;
import com.relabs.e_commerce.util.Common;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> newCategoryList = new ArrayList<>();
    onItemClickListener listener;
    Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void updateList(List<Category> CategoryList) {
        newCategoryList.clear();
        newCategoryList.addAll(CategoryList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        ImageView imageView =holder.itemView.findViewById(R.id.image_category);
        TextView textView=holder.itemView.findViewById(R.id.text_category_name);
        if(context!= null){
            Glide.with(context)
                    .load(Common.CATEGORY_URL+newCategoryList.get(position).image)
                    .fitCenter()
                    .into(imageView);
        }
        textView.setText(newCategoryList.get(position).title);

    }

    @Override
    public int getItemCount() {
        return newCategoryList.size();
    }

    public void setList(List<Category> CategoryList) {
        this.newCategoryList = CategoryList;
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(newCategoryList.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Category item);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
