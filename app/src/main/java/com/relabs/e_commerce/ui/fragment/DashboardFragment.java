package com.relabs.e_commerce.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.relabs.e_commerce.R;
import com.relabs.e_commerce.databinding.FragmentDashboardBinding;
import com.relabs.e_commerce.ui.activity.Favorites;
import com.relabs.e_commerce.ui.activity.LoginActivity;
import com.relabs.e_commerce.ui.activity.MainActivity;
import com.relabs.e_commerce.util.Common;
import com.relabs.e_commerce.viewmodel.DashboardViewModel;

import java.util.Objects;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;
    private FragmentDashboardBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        binding.setProfile(viewModel);
        preferences = getContext().getSharedPreferences("UserData", 0);
        editor = preferences.edit();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonLogInOut.setText(R.string.login);
        binding.buttonLogInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });


        if (Common.CURRENT_USER != null) {
            binding.textUserName.setText(Common.CURRENT_USER.name);
            binding.textUserEmail.setText(Common.CURRENT_USER.email);
            Glide.with(getContext())
                    .load(Common.Avatar_URL+Common.CURRENT_USER.avatar)
                    .fitCenter()
                    .into(binding.circleImageView);
            binding.buttonLogInOut.setText(R.string.log_out);
            binding.buttonLogInOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.clear();
                    editor.apply();
                    Common.CURRENT_USER=null;
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            });
        }


        binding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Favorites.class));
            }
        });

    }
}