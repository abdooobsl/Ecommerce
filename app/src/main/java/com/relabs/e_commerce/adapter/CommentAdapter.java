package com.relabs.e_commerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.databinding.ItemCommentLayoutBinding;
import com.relabs.e_commerce.databinding.ItemProductGridBinding;
import com.relabs.e_commerce.model.Comment;
import com.relabs.e_commerce.model.Image;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> newCommentList = new ArrayList<>();
    onItemClickListener listener;
    Context context;
    private ItemCommentLayoutBinding binding;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void updateList(List<Comment> CommentList) {
        newCommentList.clear();
        newCommentList.addAll(CommentList);
        notifyDataSetChanged();
    }

    public void addItem(Comment comment) {
        this.newCommentList.add(comment);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCommentLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentAdapter.CommentViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        if (newCommentList != null) {
            binding.userNameComment.setText(newCommentList.get(position).user.name + " :");
            binding.userCommentComment.setText(newCommentList.get(position).comment);
        }
    }

    @Override
    public int getItemCount() {
        return newCommentList.size();
    }

    public void setList(List<Comment> CommentList) {
        this.newCommentList = CommentList;
        notifyDataSetChanged();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(newCommentList.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Comment item);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
