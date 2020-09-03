package com.relabs.e_commerce.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.relabs.e_commerce.databinding.ItemProductGridBinding;
import com.relabs.e_commerce.model.Product;
import com.relabs.e_commerce.model.server_message;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.Common;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> newProductList = new ArrayList<>();
    onItemClickListener listener;
    Context context;
    private ItemProductGridBinding binding;
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public ProductAdapter(Context context) {
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
        binding = ItemProductGridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        binding.productName.setText(newProductList.get(position).title);
        binding.productPrice.setText(newProductList.get(position).price+" $");
        if (context!=null){
            Glide.with(context)
                    .load(Common.PRODUCT_URL+newProductList.get(position).image)
                    .fitCenter()
                    .into(binding.productImage);
        }
        binding.love.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                disposable.add(restFullApi.favourite(Common.CURRENT_USER.id,newProductList.get(position).id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .onErrorResumeNext(new ObservableSource<server_message>() {
                            @Override
                            public void subscribe(Observer<? super server_message> observer) {

                            }
                        })
                        .subscribe(new Consumer<server_message>() {
                            @Override
                            public void accept(server_message server_message) throws Exception {
                                Toast.makeText(context,server_message.message,Toast.LENGTH_SHORT).show();
                            }
                        })

                );
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Log.d("test",newProductList.get(position).id+"");
                disposable.add(restFullApi.favourite(Common.CURRENT_USER.id,newProductList.get(position).id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .onErrorResumeNext(new ObservableSource<server_message>() {
                            @Override
                            public void subscribe(Observer<? super server_message> observer) {

                            }
                        })
                        .subscribe(new Consumer<server_message>() {
                            @Override
                            public void accept(server_message server_message) throws Exception {
                                Toast.makeText(context,server_message.message,Toast.LENGTH_SHORT).show();
                            }
                        })

                );
            }
        });

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

    private void add_to_fav(){
       // disposable.add(restFullApi.favourite(newProductList.get(p)))
    }
}
