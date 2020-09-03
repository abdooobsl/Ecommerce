package com.relabs.e_commerce.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.relabs.e_commerce.model.Errors;
import com.relabs.e_commerce.model.Product;
import com.relabs.e_commerce.model.Server_Category_Products_Response;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.Common;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CategoryViewModel extends AndroidViewModel {
    public CategoryViewModel(@NonNull Application application) {
        super(application);
    }
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<Integer> category_id = new MutableLiveData<>();
    public MutableLiveData<List<Product>> products=new MutableLiveData<List<Product>>();
    public MutableLiveData<Integer> success = new MutableLiveData<>();
    public MutableLiveData<Errors> error=new MutableLiveData<Errors>();

    public void getCategoryProducts(){
        disposable.add(restFullApi.getCategoryProducts(category_id.getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<Server_Category_Products_Response>() {
                    @Override
                    public void subscribe(Observer<? super Server_Category_Products_Response> observer) {
                        Log.d("debug",observer+"");

                    }
                })
                .subscribe(new Consumer<Server_Category_Products_Response>() {
                    @Override
                    public void accept(Server_Category_Products_Response server_category_products_response) throws Exception {
                        products.setValue(server_category_products_response.products);
                        success.setValue(server_category_products_response.success);
                        error.setValue(server_category_products_response.errors);

                    }
                })

        );
    }
}
