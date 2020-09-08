package com.relabs.e_commerce.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.relabs.e_commerce.R;
import com.relabs.e_commerce.databinding.ItemCartLayoutBinding;
import com.relabs.e_commerce.model.CartList;
import com.relabs.e_commerce.model.ItemList;
import com.relabs.e_commerce.model.server_message;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.ui.activity.MainActivity;
import com.relabs.e_commerce.ui.fragment.CartFragment;
import com.relabs.e_commerce.util.Common;
import com.relabs.e_commerce.util.Refresh;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ItemListViewHolder> {
    private List<ItemList> newItemListList = new ArrayList<>();
    onItemClickListener listener;
    Context context;
    private ItemCartLayoutBinding binding;
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    private Fragment fragment;

    public CartListAdapter(Context context, CartFragment cartFragment) {
        this.context = context;
        this.fragment = cartFragment;
    }


    public void updateList(List<ItemList> ItemListList) {
        newItemListList.clear();
        newItemListList.addAll(ItemListList);
        notifyDataSetChanged();
    }

    public String getTotal() {
        String total;
        int value=0;
        Log.d("test_cartA",newItemListList.size()+"");
        for (int i=0;i<newItemListList.size();i++){
            Log.d("test_cartA",i+"");
            value=value+newItemListList.get(i).quantity*newItemListList.get(i).product.price;

        }
        total= String.valueOf(value);
            return total;

    }


    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCartLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartListAdapter.ItemListViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        binding.productNameCart.setText(newItemListList.get(position).product.title);
        binding.priceCart.setText( String.valueOf(newItemListList.get(position).product.price * newItemListList.get(position).quantity));
        binding.quantity.setText(String.valueOf(newItemListList.get(position).quantity));
        if (context != null) {
            Glide.with(context)
                    .load(Common.PRODUCT_URL + newItemListList.get(position).product.image)
                    .fitCenter()
                    .into(binding.imageView6);
        }
        binding.inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newItemListList.get(position).quantity == 10) {
                    Toast.makeText(context, "max quantity is reached", Toast.LENGTH_SHORT).show();
                } else {
                    int y = newItemListList.get(position).quantity;
                    y++;
                    update(Common.CURRENT_USER.id, newItemListList.get(position).product_id, y);

                }
            }
        });
        binding.dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newItemListList.get(position).quantity == 1) {
                    Toast.makeText(context, "min quantity is reached", Toast.LENGTH_SHORT).show();
                } else {
                    int y = newItemListList.get(position).quantity;
                    y--;
                    update(Common.CURRENT_USER.id, newItemListList.get(position).product_id, y);


                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return newItemListList.size();
    }

    public void setList(List<ItemList> ItemListList) {
        this.newItemListList = ItemListList;
        notifyDataSetChanged();
    }

    public class ItemListViewHolder extends RecyclerView.ViewHolder {

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(newItemListList.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {

        void onItemClick(ItemList item);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    private void update(int user_id, int product_id, int quantity) {
        disposable.add(restFullApi.updateCart(user_id, product_id, quantity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<CartList>() {
                    @Override
                    public void subscribe(Observer<? super CartList> observer) {

                    }
                })
                .subscribe(new Consumer<CartList>() {
                    @Override
                    public void accept(CartList cartList) throws Exception {
                        Log.d("test_cart", cartList.message);
                        updateList(cartList.list);
                        ((CartFragment) fragment).refresh(cartList.list);

                    }
                })
        );

    }


}

