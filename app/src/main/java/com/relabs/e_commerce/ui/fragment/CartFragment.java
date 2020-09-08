package com.relabs.e_commerce.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.adapter.CartListAdapter;
import com.relabs.e_commerce.databinding.FragmentCartBinding;
import com.relabs.e_commerce.model.ItemList;
import com.relabs.e_commerce.ui.activity.CheckoutActivity;
import com.relabs.e_commerce.util.Common;
import com.relabs.e_commerce.util.Refresh;
import com.relabs.e_commerce.viewmodel.CartViewModel;

import java.util.List;

public class CartFragment extends Fragment {

    private CartViewModel viewModel;
    private FragmentCartBinding binding;
    private CartListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        binding.setCart(viewModel);
        adapter= new CartListAdapter(getActivity(),CartFragment.this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeViewModel();
        if (Common.CURRENT_USER!=null){
            viewModel.user_id.setValue(Common.CURRENT_USER.id);
            viewModel.getCart();
            observeViewModel();
        }
        binding.swipeCart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getCart();
                binding.swipeCart.setRefreshing(false);
            }
        });
        binding.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CheckoutActivity.class);
                intent.putExtra("total_price",adapter.getTotal());
                startActivity(intent);
            }
        });
    }

    private void observeViewModel() {
        viewModel.cart.observe(getActivity(),cartList -> {
            if (cartList!=null){
                adapter.updateList(cartList.list);
                binding.rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.rvCart.setAdapter(adapter);
                binding.totalValue.setText("$ "+adapter.getTotal());
            }
        });
    }
    public void refresh(List<ItemList> ItemListList){
        adapter.updateList(ItemListList);
        binding.rvCart.setAdapter(adapter);
        binding.totalValue.setText("$ "+adapter.getTotal());

    }



}