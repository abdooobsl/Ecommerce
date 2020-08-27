package com.relabs.e_commerce.ui.fragment;

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
import com.relabs.e_commerce.adapter.CategoryAdapter;
import com.relabs.e_commerce.adapter.HomeProductAdaper;
import com.relabs.e_commerce.adapter.SliderAdapter;
import com.relabs.e_commerce.databinding.FragmentHomeBinding;
import com.relabs.e_commerce.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private SliderAdapter adapter;
    private CategoryAdapter categoryAdapter;
    private HomeProductAdaper homeProductAdaper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setHome(viewModel);
        adapter = new SliderAdapter(getContext());
        categoryAdapter=new CategoryAdapter(getContext());
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
                categoryAdapter.updateList(categories);
                binding.rvCategories.setAdapter(categoryAdapter);

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