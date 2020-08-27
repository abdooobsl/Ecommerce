package com.relabs.e_commerce.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.databinding.ActivitySignUpBinding;
import com.relabs.e_commerce.viewmodel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    SignUpViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(SignUpActivity.this).get(SignUpViewModel.class);
        binding = DataBindingUtil.setContentView(SignUpActivity.this, R.layout.activity_sign_up);
        binding.setLifecycleOwner(SignUpActivity.this);
        binding.setSigunup(viewModel);
        //
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.editTextTextEmailAddressSignUp.getText())) {
                    binding.editTextTextEmailAddressSignUp.setError("Enter an E-Mail Address");
                    binding.editTextTextEmailAddressSignUp.requestFocus();
                } else if (TextUtils.isEmpty(binding.editTextTextPersonNameSignUp.getText())) {
                    binding.editTextTextPersonNameSignUp.setError("Enter a Name");
                    binding.editTextTextPersonNameSignUp.requestFocus();
                } else if (TextUtils.isEmpty(binding.editTextTextPasswordSignUp.getText())) {
                    binding.editTextTextPasswordSignUp.setError("Enter a Password");
                    binding.editTextTextPasswordSignUp.requestFocus();
                } else if (TextUtils.isEmpty(binding.editTextTextPasswordConfirmationSignUp.getText())) {
                    binding.editTextTextPasswordConfirmationSignUp.setError("Confirm Password");
                    binding.editTextTextPasswordConfirmationSignUp.requestFocus();
                } else if (binding.editTextTextPasswordSignUp.getText().length() < 8) {
                    binding.editTextTextPasswordSignUp.setError("must be 8 character at least");
                    binding.editTextTextPasswordSignUp.requestFocus();
                } else if (!binding.editTextTextPasswordConfirmationSignUp.getText().toString().equals(binding.editTextTextPasswordSignUp.getText().toString())) {
                    binding.editTextTextPasswordConfirmationSignUp.setError("Password doesn't match");
                    binding.editTextTextPasswordConfirmationSignUp.requestFocus();
                } else if (Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextEmailAddressSignUp.getText()).matches()) {
                    viewModel.email.setValue(binding.editTextTextEmailAddressSignUp.getText().toString());
                    viewModel.name.setValue(binding.editTextTextPersonNameSignUp.getText().toString());
                    viewModel.confirm_password.setValue(binding.editTextTextPasswordConfirmationSignUp.getText().toString());
                    viewModel.password.setValue(binding.editTextTextPasswordSignUp.getText().toString());
                    viewModel.sign_up();
                } else {
                    binding.editTextTextEmailAddressSignUp.setError("Enter a Valid E-mail Address");
                    binding.editTextTextEmailAddressSignUp.requestFocus();
                }

            }
        });
        observeViewModel();
    }


    private void observeViewModel() {
        viewModel.success.observe(SignUpActivity.this, value -> {
            if (value != null) {
                if (value == 1) {
                    Toast.makeText(getApplicationContext(), viewModel.log_message.getValue(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }
                else
                    /*if (viewModel.errors.getValue().email != null){
                        Toast.makeText(getApplicationContext(), viewModel.errors.getValue().email.get(0), Toast.LENGTH_SHORT).show();
                    }

                     */
                    Toast.makeText(getApplicationContext(), viewModel.log_message.getValue(), Toast.LENGTH_SHORT).show();


            }


        });
    }
}