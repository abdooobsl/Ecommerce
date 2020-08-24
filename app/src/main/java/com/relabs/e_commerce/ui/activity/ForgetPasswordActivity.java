package com.relabs.e_commerce.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.databinding.ActivityForgetPasswordBinding;
import com.relabs.e_commerce.ui.viewmodel.ForgetPasswordViewModel;
import com.relabs.e_commerce.ui.viewmodel.LoginViewModel;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ForgetPasswordViewModel viewModel;
    private ActivityForgetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(ForgetPasswordActivity.this).get(ForgetPasswordViewModel.class);
        binding = DataBindingUtil.setContentView(ForgetPasswordActivity.this, R.layout.activity_forget_password);
        binding.setLifecycleOwner(ForgetPasswordActivity.this);
        binding.setForgetPassword(viewModel);
        binding.buttonSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.email.setValue(binding.editTextTextEmailAddress.getText().toString());
                viewModel.forget();
            }
        });
        observeViewModel();
    }

    private void observeViewModel() {

        viewModel.log_message.observe(ForgetPasswordActivity.this,message->{
            if (message != null) {
                Toast.makeText(getApplicationContext(),viewModel.log_message.getValue(),Toast.LENGTH_SHORT).show();

            }
            else
                Toast.makeText(getApplicationContext(),"wrong username or password",Toast.LENGTH_SHORT).show();
        });
    }
}