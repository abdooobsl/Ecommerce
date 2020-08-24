package com.relabs.e_commerce.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.relabs.e_commerce.model.server_message;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.Common;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ForgetPasswordViewModel extends AndroidViewModel {
    public ForgetPasswordViewModel(@NonNull Application application) {
        super(application);
    }
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> log_message = new MutableLiveData<>();



    public void forget(){
        disposable.add(restFullApi.forget(email.getValue())
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
                        log_message.setValue(server_message.message);
                    }
                }));

    }
}
