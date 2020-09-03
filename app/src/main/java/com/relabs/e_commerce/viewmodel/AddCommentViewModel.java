package com.relabs.e_commerce.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.relabs.e_commerce.model.Errors;
import com.relabs.e_commerce.model.server_message;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.util.Common;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddCommentViewModel extends AndroidViewModel {
    public AddCommentViewModel(@NonNull Application application) {
        super(application);
    }
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> message = new MutableLiveData<>();
    public MutableLiveData<Integer> success = new MutableLiveData<>();
    public MutableLiveData<Errors> errors = new MutableLiveData<Errors>();
    public MutableLiveData<Integer> rate = new MutableLiveData<>();
    public MutableLiveData<String> comment = new MutableLiveData<>();
    public MutableLiveData<Integer> product_id = new MutableLiveData<>();
    public MutableLiveData<Integer> user_id = new MutableLiveData<>();

    public void addComment(){
        disposable.add(restFullApi.addComment(user_id.getValue(),product_id.getValue(),comment.getValue())
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
                        success.setValue(server_message.success);
                        message.setValue(server_message.message);
                        errors.setValue(server_message.errors);
                    }
                })
        );

    }
    public void addRate(){
        disposable.add(restFullApi.addRate(user_id.getValue(),product_id.getValue(),rate.getValue())
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
                        success.setValue(server_message.success);
                        message.setValue(server_message.message);
                        errors.setValue(server_message.errors);
                    }
                })
        );

    }
}
