package com.relabs.e_commerce.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.relabs.e_commerce.model.Errors;
import com.relabs.e_commerce.model.Product;
import com.relabs.e_commerce.model.Search;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.Common;

import java.util.List;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends AndroidViewModel {
    public SearchViewModel(@NonNull Application application) {
        super(application);
    }
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> message = new MutableLiveData<>();
    public MutableLiveData<Integer> success = new MutableLiveData<>();
    public MutableLiveData<Errors> errors = new MutableLiveData<Errors>();
    public MutableLiveData<List<Product>> products=new MutableLiveData<List<Product>>();
    public MutableLiveData<String> keyword = new MutableLiveData<>();


    public void search(){
        disposable.add(restFullApi.searchKeyword(keyword.getValue().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<Search>() {
                    @Override
                    public void subscribe(Observer<? super Search> observer) {

                    }
                })
                .subscribe(new Consumer<Search>() {
                    @Override
                    public void accept(Search search) throws Exception {
                        message.setValue(search.message);
                        success.setValue(search.success);
                        products.setValue(search.products);
                        errors.setValue(search.errors);
                    }
                })
        );

    }
}