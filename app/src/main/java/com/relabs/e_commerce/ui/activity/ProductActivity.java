package com.relabs.e_commerce.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.relabs.e_commerce.R;
import com.relabs.e_commerce.adapter.CommentAdapter;
import com.relabs.e_commerce.adapter.ImageSliderAdapter;
import com.relabs.e_commerce.databinding.ActivityProductBinding;
import com.relabs.e_commerce.model.Comment;
import com.relabs.e_commerce.model.Image;
import com.relabs.e_commerce.model.User;
import com.relabs.e_commerce.util.Common;
import com.relabs.e_commerce.viewmodel.ProductViewModel;

public class ProductActivity extends AppCompatActivity {
    private ActivityProductBinding binding;
    private ProductViewModel viewModel;
    private CommentAdapter commentAdapter;
    private ImageSliderAdapter imageSliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(ProductActivity.this).get(ProductViewModel.class);
        binding = DataBindingUtil.setContentView(ProductActivity.this, R.layout.activity_product);
        binding.setLifecycleOwner(ProductActivity.this);
        binding.setProduct(viewModel);
        commentAdapter = new CommentAdapter(getApplicationContext());
        imageSliderAdapter = new ImageSliderAdapter(getApplicationContext());
        viewModel.product_id.setValue(Common.CURRENT_PRODUCT.id);
        viewModel.getProduct();
        observeViewModel();
        binding.addToCartProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.quantity.setValue(1);
                viewModel.user_id.setValue(Common.CURRENT_USER.id);
                viewModel.product_id.setValue(Common.CURRENT_PRODUCT.id);
                viewModel.addToCart();
            }
        });

        binding.addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductActivity.this, AddCommentActivity.class));
                finish();
            }
        });


    }

    private void observeViewModel() {
        viewModel.product.observe(this, product -> {
            if (product != null) {
                binding.productActivityToolbar.setTitle(product.title);
                setSupportActionBar(binding.productActivityToolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                binding.productActivityToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
                binding.productActivityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });


                binding.productActivityName.setText(product.title);
                binding.productActivityPrice.setText(product.price + " $");
                binding.productActivityDescribtion.setText(product.details);
                binding.productActivityDescribtion.addShowLessText("less");
                binding.productActivityDescribtion.addShowMoreText("more");
                binding.productActivityDescribtion.setShowingLine(3);
            }
        });
        viewModel.comments.observe(this, comments -> {
            if (comments != null) {
                if (comments.size() >= 1) {
                    binding.rvComments.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    commentAdapter.updateList(viewModel.comments.getValue());
                } else {
                    Comment comment = new Comment();
                    User user = new User();
                    user.name = " ";
                    comment.user = user;
                    comment.comment = "no comments yet add yours ";
                    commentAdapter.addItem(comment);
                }
                binding.rvComments.setAdapter(commentAdapter);

            }
        });
        viewModel.rate.observe(this, rate -> {
            if (rate != null) {
                binding.simpleRatingBar.setRating(rate);

            }
        });
        viewModel.images.observe(this, images -> {
            if (images != null) {
                if (images.size() >= 1) {
                    imageSliderAdapter.renewItems(images);
                    binding.imageSlider.setSliderAdapter(imageSliderAdapter);
                    binding.imageSlider.setInfiniteAdapterEnabled(false);
                } else {
                    Image image = new Image();
                    image.image = viewModel.product.getValue().image;
                    imageSliderAdapter.addItem(image);
                    binding.imageSlider.setSliderAdapter(imageSliderAdapter);
                    binding.imageSlider.setAutoCycle(false);
                    binding.imageSlider.setInfiniteAdapterEnabled(false);

                }

            }
        });
        viewModel.message_cart.observe(this, message -> {
            if (message != null) {

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }




}