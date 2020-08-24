package com.relabs.e_commerce.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.databinding.ActivityLoginBinding;
import com.relabs.e_commerce.ui.viewmodel.LoginViewModel;
import com.relabs.e_commerce.util.Common;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(LoginActivity.this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(LoginActivity.this);
        binding.setLoginmodel(viewModel);
        preferences = getApplicationContext().getSharedPreferences("UserData", 0);
        editor=preferences.edit();
        binding.checkBoxRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Common.USER_PREFERENCE=isChecked;
            }
        });
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        binding.forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.editTextTextEmailAddress.getText())) {
                    binding.editTextTextEmailAddress.setError("Enter an E-Mail Address");
                    binding.editTextTextEmailAddress.requestFocus();
                }
                else if (TextUtils.isEmpty(binding.editTextTextPassword.getText())) {
                    binding.editTextTextPassword.setError("Enter a Password");
                    binding.editTextTextPassword.requestFocus();
                }
                else if (Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextEmailAddress.getText()).matches()) {
                    viewModel.email.setValue(binding.editTextTextEmailAddress.getText().toString());
                    viewModel.password.setValue(binding.editTextTextPassword.getText().toString());
                    viewModel.login();

                }
                else {
                    binding.editTextTextEmailAddress.setError("Enter a Valid E-mail Address");
                    binding.editTextTextEmailAddress.requestFocus();
                }
            }
        });
        observeViewModel();
    }

    private void observeViewModel() {

    viewModel.logged.observe(LoginActivity.this,isLogged->{
        if (isLogged != null && isLogged) {
            Toast.makeText(getApplicationContext(),viewModel.log_message.getValue(),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        else
            Toast.makeText(getApplicationContext(),"wrong username or password",Toast.LENGTH_SHORT).show();
    });
    }
}