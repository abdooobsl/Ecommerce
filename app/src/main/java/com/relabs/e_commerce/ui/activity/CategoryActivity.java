package com.relabs.e_commerce.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.adapter.ProductAdapter;
import com.relabs.e_commerce.databinding.ActivityCategoryBinding;
import com.relabs.e_commerce.model.Product;
import com.relabs.e_commerce.util.Common;
import com.relabs.e_commerce.viewmodel.CategoryViewModel;

public class CategoryActivity extends AppCompatActivity {
    private ActivityCategoryBinding binding;
    private CategoryViewModel viewModel;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(CategoryActivity.this).get(CategoryViewModel.class);
        binding = DataBindingUtil.setContentView(CategoryActivity.this, R.layout.activity_category);
        binding.setLifecycleOwner(CategoryActivity.this);
        binding.setCategories(viewModel);

        binding.toolbar.setTitle(Common.CURRENT_CATEGORY.title);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        adapter = new ProductAdapter(CategoryActivity.this);
        binding.rvCategoryProducts.setLayoutManager(new GridLayoutManager(this, 2));
        viewModel.category_id.setValue(Common.CURRENT_CATEGORY.id);
        viewModel.getCategoryProducts();
        observeViewModel();

        adapter.setOnItemClickListener(new ProductAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Product item) {
                Common.CURRENT_PRODUCT=item;
                startActivity(new Intent(getApplicationContext(),ProductActivity.class));
            }
        });
    }

    private void observeViewModel() {
        viewModel.products.observe(CategoryActivity.this, products -> {
            if (products != null) {
                adapter.updateList(products);
                binding.rvCategoryProducts.setAdapter(adapter);
            }
        });
        viewModel.error.observe(CategoryActivity.this, error ->{
            if (error!=null){
                Toast.makeText(getApplicationContext(),viewModel.error.getValue().category_id+"",Toast.LENGTH_SHORT).show();
            }
        });
    }
}