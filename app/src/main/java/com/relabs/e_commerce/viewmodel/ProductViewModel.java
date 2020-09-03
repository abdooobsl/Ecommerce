package com.relabs.e_commerce.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.relabs.e_commerce.model.Comment;
import com.relabs.e_commerce.model.Errors;
import com.relabs.e_commerce.model.Image;
import com.relabs.e_commerce.model.Product;
import com.relabs.e_commerce.model.Product_response;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.Common;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProductViewModel extends AndroidViewModel {
    public ProductViewModel(@NonNull Application application) {
        super(application);
    }

    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> message = new MutableLiveData<>();
    public MutableLiveData<Integer> success = new MutableLiveData<>();
    public MutableLiveData<Errors> errors = new MutableLiveData<Errors>();
    public MutableLiveData<Product> product = new MutableLiveData<Product>();
    public MutableLiveData<Integer> product_id = new MutableLiveData<>();
    public MutableLiveData<Integer> rate = new MutableLiveData<>();
    public MutableLiveData<List<Comment>> comments = new MutableLiveData<List<Comment>>();
    public MutableLiveData<List<Image>> images = new MutableLiveData<List<Image>>();

    public void getProduct() {
        disposable.add(restFullApi.getProductByid(product_id.getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<Product_response>() {
                    @Override
                    public void subscribe(Observer<? super Product_response> observer) {
                        Log.d("error",observer+"");
                    }
                })
                .subscribe(new Consumer<Product_response>() {
                    @Override
                    public void accept(Product_response product_response) throws Exception {
                        success.setValue(product_response.success);
                        message.setValue(product_response.message);
                        product.setValue(product_response.product);
                        rate.setValue(product_response.product.rating);
                        comments.setValue(product_response.product.comments);
                        images.setValue(product_response.product.images);
                        Log.d("test",comments.getValue().size()+"");
                    }
                })

        );

    }
}
