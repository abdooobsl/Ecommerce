package com.relabs.e_commerce.retrofit;


import android.widget.ScrollView;

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
    /*    @GET("get_services")
        Observable<Clinic> get_services();

        @FormUrlEncoded
        @POST("get_appointments")
        Observable<AppointmentData> get_appointments(@Field("b_id") String b_id,
                                                     @Field("from") String from,
                                                     @Field("to") String to);

        @FormUrlEncoded
        @POST("update_appointment")
        Observable<Message> updateStatus(@Field("id") String id, @Field("status") String status);

        @FormUrlEncoded
        @POST("get_time_slot")
        Observable<TimeSlot> get_time_slot(@Field("b_id") String b_id, @Field("date") String date);

        @FormUrlEncoded
        @POST("cancel_appointment")
        Observable<CancelAppointment> cancel_appointment(@Field("user_id") String user_id, @Field("app_id") String app_id);
        @FormUrlEncoded
        @POST("add_appointment")
        Observable<Confirmation> add_appointment(@Field("user_id") String user_id,
                                                 @Field("user_fullname") String user_fullname,
                                                 @Field("user_email") String user_email,
                                                 @Field("user_phone") String user_phone,
                                                 @Field("appointment_date") String appointment_date,
                                                 @Field("start_time") String start_time,
                                                 @Field("time_token") String time_token,
                                                 @Field("b_id") String b_id);

     */
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
}
