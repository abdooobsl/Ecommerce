package com.relabs.e_commerce.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.databinding.ActivityCheckoutBinding;
import com.relabs.e_commerce.util.Common;
import com.relabs.e_commerce.viewmodel.CheckoutViewModel;
import com.relabs.e_commerce.viewmodel.LoginViewModel;

public class CheckoutActivity extends AppCompatActivity {
    private ActivityCheckoutBinding binding;
    private CheckoutViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(CheckoutActivity.this).get(CheckoutViewModel.class);
        binding = DataBindingUtil.setContentView(CheckoutActivity.this, R.layout.activity_checkout);
        binding.setLifecycleOwner(CheckoutActivity.this);
        binding.setOrder(viewModel);
        Intent intent = getIntent();
        binding.totalPrice.setText("$ " + intent.getStringExtra("total_price"));
        binding.checkToolbar.setTitle(getString(R.string.checkout));
        setSupportActionBar(binding.checkToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.checkToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.checkToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.user_id.setValue(Common.CURRENT_USER.id);
                viewModel.total.setValue(Integer.parseInt(intent.getStringExtra("total_price").toString()));
                if (binding.note.getText().length() == 0)
                    viewModel.notes.setValue("No special requests ");
                else
                    viewModel.notes.setValue(binding.note.getText().toString());
                viewModel.place_order();
            }
        });
        observeViewModel();

    }

    private void observeViewModel() {
        viewModel.success.observe(this,success->{
            if (success!=null){
                if (success==1)
                    startActivity(new Intent(getApplicationContext(),FinishedOrderActivity.class));
                finish();
            }
        });
        viewModel.message.observe(this, message -> {
            if (message != null) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

        });
        viewModel.errors.observe(this, errors -> {
            if (errors != null) {
                if (errors.notes != null) {
                    Toast.makeText(getApplicationContext(), errors.notes.get(0) + "", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}