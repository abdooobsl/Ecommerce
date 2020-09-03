package com.relabs.e_commerce.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.relabs.e_commerce.R;
import com.relabs.e_commerce.model.login_message;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.BottomNavigationBehavior;
import com.relabs.e_commerce.util.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_more,
                R.id.navigation_cart, R.id.navigation_search
                , R.id.activity_login)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        preferences = getApplicationContext().getSharedPreferences("UserData", 0);
        if (Common.CURRENT_USER == null) {
            setUserData();
        }

    }

    private void setUserData() {
        if (preferences.getBoolean("user_pref", true)) {
            login();

        }
    }

    public void login() {
        disposable.add(restFullApi.login(preferences.getString("user_email", "")
                , preferences.getString("user_pass", ""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<login_message>() {
                    @Override
                    public void subscribe(Observer<? super login_message> observer) {
                        Log.d("error", observer.toString());
                    }
                })
                .subscribe(new Consumer<login_message>() {
                    @Override
                    public void accept(login_message login_message) throws Exception {
                        Log.d("test", login_message.success + "");
                        Common.CURRENT_USER = login_message.user;

                    }
                })
        );
    }

}