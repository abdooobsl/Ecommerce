package com.relabs.e_commerce.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.relabs.e_commerce.model.Errors;
import com.relabs.e_commerce.model.Product;
import com.relabs.e_commerce.model.server_message;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.Common;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CheckoutViewModel extends AndroidViewModel {
    public CheckoutViewModel(@NonNull Application application) {
        super(application);
    }
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> message = new MutableLiveData<>();
    public MutableLiveData<Integer> success = new MutableLiveData<>();
    public MutableLiveData<Errors> errors = new MutableLiveData<Errors>();
    public MutableLiveData<Integer> user_id = new MutableLiveData<>();
    public MutableLiveData<String> notes = new MutableLiveData<>();
    public MutableLiveData<Integer> total = new MutableLiveData<>();

    public void place_order(){

        disposable.add(restFullApi.placeOrder(user_id.getValue(),total.getValue(),notes.getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<server_message>() {
                    @Override
                    public void subscribe(Observer<? super server_message> observer) {

                    }
                })
                .subscribe(new Consumer<server_message>() {
                    @Override
                    public void accept(server_message server_message) throws Exception {
                        message.setValue(server_message.message);
                        success.setValue(server_message.success);
                        errors.setValue(server_message.errors);
                    }
                })

        );
    }
}
