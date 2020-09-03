package com.relabs.e_commerce.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.databinding.ActivityAddCommentBinding;
import com.relabs.e_commerce.model.Product;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.Common;
import com.relabs.e_commerce.viewmodel.AddCommentViewModel;
import com.relabs.e_commerce.viewmodel.ProductViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class AddCommentActivity extends AppCompatActivity {
    private AddCommentViewModel viewModel;
    private ActivityAddCommentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(AddCommentActivity.this).get(AddCommentViewModel.class);
        binding = DataBindingUtil.setContentView(AddCommentActivity.this, R.layout.activity_add_comment);
        binding.setLifecycleOwner(AddCommentActivity.this);
        binding.setRating(viewModel);
        binding.addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.ratingComment.getRating() == 0.00) {
                    binding.ratingComment.requestFocus();
                    Toast.makeText(getApplicationContext(), "you must add rate", Toast.LENGTH_LONG).show();
                } else if (binding.commentEditText.getText().length() == 0) {
                    binding.commentEditText.setError("you must type comment");
                    binding.commentEditText.requestFocus();
                } else {
                    viewModel.user_id.setValue(Common.CURRENT_USER.id);
                    viewModel.product_id.setValue(Common.CURRENT_PRODUCT.id);
                    viewModel.rate.setValue(Math.round(binding.ratingComment.getRating()));
                    viewModel.comment.setValue(binding.commentEditText.getText().toString());
                    viewModel.addComment();
                    viewModel.addRate();

                }

            }
        });
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.message.observe(this, message -> {
            if (message != null) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddCommentActivity.this, ProductActivity.class));
                finish();
            }
        });
    }

}