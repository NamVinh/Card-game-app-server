package com.example.ps08877_dangnamvinh_asm.API;

import com.example.ps08877_dangnamvinh_asm.Model.CardList;
import com.example.ps08877_dangnamvinh_asm.Model.Login;
import com.example.ps08877_dangnamvinh_asm.Model.Register;
import com.example.ps08877_dangnamvinh_asm.Model.UserList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    @GET("posts")
    Call<CardList> getCards(@Header("Authorization") String Authorization);
    @GET("user")
    Call<Register> getUserIsLogged(@Header("Authorization") String Authorization); //Get Info User Is Logged
    @GET("posts/{id}")
    Call<CardList> getCardsByCat(@Header("Authorization") String Authorization, @Path("id") String id);

    @DELETE("/posts/{id}")
    Call<CardList> deleteCards(@Header("Authorization") String Authorization, @Path("id") String id);

    @GET("/users/{id}")
    Call<UserList> getUserbyID(@Header("Authorization") String Authorization, @Path("id") String id);

    @FormUrlEncoded
    @POST("user/add")
    Call<Register> Sinup(
            @Field("fullname") String fullname,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<Login> loginUser(@Field("email") String email, @Field("password") String password);
}
