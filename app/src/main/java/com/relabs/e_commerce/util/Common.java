package com.relabs.e_commerce.util;


import com.relabs.e_commerce.model.Category;
import com.relabs.e_commerce.model.Product;
import com.relabs.e_commerce.model.User;
import com.relabs.e_commerce.retrofit.RestFullApi;
import com.relabs.e_commerce.retrofit.RetrofitClint;
public class Common {

    public static String BASE_URL = "http://192.168.1.7/storeprojectlaravel/public/api/";
    public static String BANNER_URL=  "http://192.168.1.7/storeprojectlaravel/public/uploads/banner/";
    public static String CATEGORY_URL=  "http://192.168.1.7/storeprojectlaravel/public/uploads/category/";
    public static String Avatar_URL=  "http://192.168.1.7/storeprojectlaravel/public/uploads/avatar/";
    public static String PRODUCT_URL=  "http://192.168.1.7/storeprojectlaravel/public/uploads/product/";

    public static User CURRENT_USER = null;
    public static Category CURRENT_CATEGORY=null;
    public static Boolean USER_PREFERENCE=false;
    public static Product CURRENT_PRODUCT = null;


    public static RestFullApi getApi() {
        return RetrofitClint.getClient(BASE_URL).create(RestFullApi.class);
    }
}
