package com.rootsoftit.pensiontracker.data;

import com.rootsoftit.pensiontracker.crud.AllPensionResponse;
import com.rootsoftit.pensiontracker.ui.login.LoginResponse;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PensionClient {

    @POST("/api/auth/register")
    Call<ResponsePacket<String>> register(@Body User user);

    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body User user);

    @POST("/api/auth/edit-mydetails")
    Call<User> editMyDetails(@Body FormBody user);

    @GET("/api/auth/ic_logout")
    Call<User> logout();

    @POST("/api/auth/add-pension")
    Call<Pension> addPension(@Body Pension pension);

    @DELETE("/api/auth/single-pension-delete/{id}")
    Call<Pension> deletePension(@Path("id") int id);

    @POST("/api/auth/single-pension-update/{id}")
    Call<Pension> updatePension(@Path("id") int id, @Body Pension pension);

    @GET("/api/auth/single-pension/{id}")
    Call<Pension> getPensionDetails(@Path("id") int id);

    @GET("/api/auth/all-pension")
    Call<AllPensionResponse> getAllPension();

    @POST("/api/auth/contact-us")
    Call<ResponsePacket<String>> contact(@Body RequestBody body);

    @Multipart
    @POST("/api/auth/image-upload")
    Call<ResponsePacket<String>> uploadImage(@Part MultipartBody.Part body);


    @GET("/api/auth/edit-mydetails")
    Call<UserResponse> getUserDetails();

}
