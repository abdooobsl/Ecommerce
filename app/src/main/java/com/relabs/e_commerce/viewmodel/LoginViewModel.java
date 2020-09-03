package com.relabs.e_commerce.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.relabs.e_commerce.model.User;
import com.relabs.e_commerce.model.login_message;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.Common;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        preferences = context.getSharedPreferences("UserData", 0);
        editor = preferences.edit();
    }

    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> log_message = new MutableLiveData<>();
    public MutableLiveData<Integer> success = new MutableLiveData<>();
    public MutableLiveData<Boolean> logged = new MutableLiveData<>();
    public MutableLiveData<User> user = new MutableLiveData<User>();


    public void login() {
        disposable.add(restFullApi.login(email.getValue(), password.getValue())
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
                        log_message.setValue(login_message.message);
                        success.setValue(login_message.success);
                        user.setValue(login_message.user);
                    }
                })
        );
    }
}
