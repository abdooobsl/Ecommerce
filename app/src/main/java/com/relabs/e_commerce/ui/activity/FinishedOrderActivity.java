package com.relabs.e_commerce.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.databinding.ActivityFinishedOrderBinding;
import com.relabs.e_commerce.viewmodel.CheckoutViewModel;
import com.relabs.e_commerce.viewmodel.FinishedOrderViewModel;

public class FinishedOrderActivity extends AppCompatActivity {
private FinishedOrderViewModel viewModel;
private ActivityFinishedOrderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(FinishedOrderActivity.this).get(FinishedOrderViewModel.class);
        binding = DataBindingUtil.setContentView(FinishedOrderActivity.this, R.layout.activity_finished_order);
        binding.setLifecycleOwner(FinishedOrderActivity.this);
        binding.setFinishOrder(viewModel);
        binding.gotoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
               finish();


            }
        });
       /* binding.gotoOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        */
    }
}