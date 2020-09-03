package com.relabs.e_commerce.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.adapter.HomeCategoryAdapter;
import com.relabs.e_commerce.adapter.HomeProductAdaper;
import com.relabs.e_commerce.adapter.HomeSliderAdapter;
import com.relabs.e_commerce.databinding.FragmentHomeBinding;
import com.relabs.e_commerce.model.Category;
import com.relabs.e_commerce.model.Product;
import com.relabs.e_commerce.ui.activity.CategoryActivity;
import com.relabs.e_commerce.ui.activity.ProductActivity;
import com.relabs.e_commerce.util.Common;
import com.relabs.e_commerce.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private HomeSliderAdapter adapter;
    private HomeCategoryAdapter homeCategoryAdapter;
    private HomeProductAdaper homeProductAdaper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setHome(viewModel);
        adapter = new HomeSliderAdapter(getContext());
        homeCategoryAdapter =new HomeCategoryAdapter(getContext());
        homeProductAdaper=new HomeProductAdaper(getContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getHome();
        observeViewModel();
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rvProducts.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        homeCategoryAdapter.setOnItemClickListener(new HomeCategoryAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Category item) {
                Common.CURRENT_CATEGORY=item;
                startActivity(new Intent(getContext(), CategoryActivity.class));
            }
        });
        homeProductAdaper.setOnItemClickListener(new HomeProductAdaper.onItemClickListener() {
            @Override
            public void onItemClick(Product item) {
                Common.CURRENT_PRODUCT=item;
                startActivity(new Intent(getContext(), ProductActivity.class));
            }
        });
    }

    private void observeViewModel() {
        viewModel.banner.observe(getActivity(), banners -> {
            if (banners != null) {
                adapter.renewItems(banners);
                binding.imageSlider.setSliderAdapter(adapter);
            }
        });
        viewModel.category.observe(getActivity(), categories -> {
            if(categories!=null){
                homeCategoryAdapter.updateList(categories);
                binding.rvCategories.setAdapter(homeCategoryAdapter);

            }

        });
        viewModel.product.observe(getActivity(),products -> {
            if(products!=null){
                homeProductAdaper.updateList(products);
                binding.rvProducts.setAdapter(homeProductAdaper);

            }
        });
    }

}