package com.relabs.e_commerce.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.relabs.e_commerce.model.Banner;
import com.relabs.e_commerce.model.Category;
import com.relabs.e_commerce.model.Home;
import com.relabs.e_commerce.model.Product;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.Common;

import java.util.List;
import java.util.logging.LoggingPermission;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel {


    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<List<Banner>> banner=new MutableLiveData<List<Banner>>();
    public MutableLiveData<List<Category>> category=new MutableLiveData<List<Category>>();
    public MutableLiveData<List<Product>> product=new MutableLiveData<List<Product>>();


    public void getHome() {
        disposable.add(restFullApi.get_home()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<Home>() {
                    @Override
                    public void subscribe(Observer<? super Home> observer) {
                        Log.d("test",observer+"");
                    }
                })
                .subscribe(new Consumer<Home>() {
                    @Override
                    public void accept(Home home) throws Exception {
                        banner.setValue(home.banners);
                        category.setValue(home.categories);
                        product.setValue(home.products);
                    }
                })
        );

    }
}