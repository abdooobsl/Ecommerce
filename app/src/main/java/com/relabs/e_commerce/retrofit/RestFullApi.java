package com.relabs.e_commerce.retrofit;


import android.widget.ScrollView;

import com.relabs.e_commerce.model.CartList;
import com.relabs.e_commerce.model.Home;
import com.relabs.e_commerce.model.Product_response;
import com.relabs.e_commerce.model.Search;
import com.relabs.e_commerce.model.Server_Category_Products_Response;
import com.relabs.e_commerce.model.server_message;
import com.relabs.e_commerce.model.login_message;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestFullApi {

    @GET("gethome")
    Observable<Home> get_home();

    @FormUrlEncoded
    @POST("login")
    Observable<login_message> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("signup")
    Observable<server_message> sign_up(@Field("email") String email,
                                       @Field("name") String name,
                                       @Field("password") String password,
                                       @Field("password_confirmation") String password_confirmation);

    @FormUrlEncoded
    @POST("forget")
    Observable<server_message> forget(@Field("email") String email);

    @FormUrlEncoded
    @POST("addComment")
    Observable<server_message> addComment (@Field("user_id") int user_id,
                                          @Field("product_id") int product_id,
                                          @Field("comment") String comment);
    @FormUrlEncoded
    @POST("addRate")
    Observable<server_message> addRate (@Field("user_id") int user_id,
                                           @Field("product_id") int product_id,
                                           @Field("rating") int rating);

    @FormUrlEncoded
    @POST("favourite")
    Observable<server_message> favourite(@Field("user_id") int user_id,
                                         @Field("product_id") int product_id);

    @FormUrlEncoded
    @POST("getCategoryProducts")
    Observable<Server_Category_Products_Response> getCategoryProducts(@Field("category_id") int category_id);

    @FormUrlEncoded
    @POST("getProductByid")
    Observable<Product_response> getProductByid(@Field("product_id") int product_id);

    @FormUrlEncoded
    @POST("searchKeyword")
    Observable<Search> searchKeyword(@Field("keyword") String keyword);
    @FormUrlEncoded
    @POST("addTocart")
    Observable<CartList> addTocart (@Field("user_id") int user_id,
                                    @Field("product_id") int product_id,
                                    @Field("quantity") int quantity);
    @FormUrlEncoded
    @POST("updateCart")
    Observable<CartList> updateCart (@Field("user_id") int user_id,
                                    @Field("product_id") int product_id,
                                    @Field("quantity") int quantity);
    @FormUrlEncoded
    @POST("cartList")
    Observable<CartList> cartList (@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("placeOrder")
    Observable<server_message> placeOrder (@Field("user_id") int user_id,
                                     @Field("total") int total,
                                     @Field("notes") String notes);
}
