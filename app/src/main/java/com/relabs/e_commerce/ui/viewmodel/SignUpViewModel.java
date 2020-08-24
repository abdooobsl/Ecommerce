package com.relabs.e_commerce.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.navigation.NavigationView;
import com.relabs.e_commerce.model.Errors;
import com.relabs.e_commerce.model.login_message;
import com.relabs.e_commerce.model.server_message;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.Common;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SignUpViewModel extends AndroidViewModel {
    public SignUpViewModel(@NonNull Application application) {
        super(application);
    }

    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> confirm_password = new MutableLiveData<>();
    public MutableLiveData<String> log_message = new MutableLiveData<>();
    public MutableLiveData<Integer> success = new MutableLiveData<>();
    public MutableLiveData<Errors> errors = new MutableLiveData<Errors>();


    public void sign_up(){
        disposable.add(restFullApi.sign_up(email.getValue(),name.getValue(),password.getValue(),confirm_password.getValue())
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
                        success.setValue(server_message.success);
                        errors.setValue(server_message.errors);
                        Log.d("test",log_message.getValue().toString());
                        Log.d("test",success.getValue().toString());
                        Log.d("test",errors.getValue()+"");
                    }
                })
                );
    }
}
