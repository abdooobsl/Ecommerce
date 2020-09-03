package com.relabs.e_commerce.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.adapter.ProductAdapter;
import com.relabs.e_commerce.databinding.FragmentSearchBinding;
import com.relabs.e_commerce.viewmodel.HomeViewModel;
import com.relabs.e_commerce.viewmodel.SearchViewModel;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;
    private ProductAdapter adapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        binding.setSearch(viewModel);
        adapter = new ProductAdapter(getContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeViewModel();
        binding.rvSearch.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.keyword.setValue(s.toString());
                viewModel.search();
                observeViewModel();
            }
        });
    }

    private void observeViewModel() {
        viewModel.products.observe(getActivity(),products -> {
            if (products!=null){
                adapter.updateList(products);
                binding.rvSearch.setAdapter(adapter);
            }
        });
    }
}