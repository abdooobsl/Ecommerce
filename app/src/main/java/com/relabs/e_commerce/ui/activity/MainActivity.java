package com.relabs.e_commerce.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.relabs.e_commerce.R;
import com.relabs.e_commerce.util.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_more,
                R.id.navigation_cart,R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        preferences =getApplicationContext().getSharedPreferences("UserData", 0);
        editor = preferences.edit();
        //setUserData();
    }

    private void setUserData(){

        if (!Objects.equals(preferences.getString("user_email", ""), "")) {
            Common.CURRENT_USER.id=preferences.getInt("user_id",0);
            Common.CURRENT_USER.name=preferences.getString("user_name","test");
            Common.CURRENT_USER.email=preferences.getString("user_email","test");
            Common.CURRENT_USER.avatar=preferences.getString("user_avatar","test");
            Common.CURRENT_USER.email_verified_at=preferences.getString("user_email_verified_at","test");
            Common.CURRENT_USER.created_at=preferences.getString("user_created_at","test");
            Common.CURRENT_USER.updated_at=preferences.getString("user_updated_at","test");
        }
    }

}