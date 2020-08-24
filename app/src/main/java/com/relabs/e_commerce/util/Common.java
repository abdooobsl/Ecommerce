package com.relabs.e_commerce.util;


import com.relabs.e_commerce.model.User;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.retrofit.RetrofitClint;
public class Common {

    public static String BASE_URL = "http://192.168.1.7/storeprojectlaravel/public/api/";

    public static User CURRENT_USER = null;
    public static Boolean USER_PREFERENCE=false;


    public static RestFullApi getApi() {
        return RetrofitClint.getClient(BASE_URL).create(RestFullApi.class);
    }
}
