package com.example.fruituser.Client;

import com.example.fruituser.model.FileDto;
import com.example.fruituser.model.LoginRequest;
import com.example.fruituser.model.LoginResponse;
import com.example.fruituser.model.SignUpRequest;
import com.example.fruituser.model.SignupResponse;
import com.example.fruituser.model.SuccessMessage;
import com.example.fruituser.model.UserForgotEmailVerifyResponse;
import com.example.fruituser.model.UserForgotPasswordRequest;
import com.example.fruituser.model.VerifyOtpRequest;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @POST("login")
    public Call<LoginResponse> userLogin(@Header("Accept-Language") String acceptLanguage,
                                         @Header("Content-Type") String contentType,
                                         @Body LoginRequest loginRequest);

    @POST("signup")
    public Call<SignupResponse> signUp(@Header("Accept-Language") String acceptLanguage,
                                       @Header("Content-Type") String contentType,
                                       @Body SignUpRequest signUpRequest);

    @Multipart
    @POST("fileupload")
    public Call<FileDto> profileUpload(@Header("Accept-Language") String acceptLanguage,
                                       @Header("Authorization") String authHeader,
                                       @Part MultipartBody.Part image);

    @GET("user")
    public Call<UserForgotEmailVerifyResponse> verifyEmail(@Header("Accept-Language") String acceptLanguage,
                                                           @Header("Content-Type") String contentType,
                                                           @Query("email") String email);

    @PUT("user/verifyotp/{userId}")
    public Call<SuccessMessage> verifyOtp(@Header("Accept-Language") String acceptLanguage,
                                          @Header("Content-Type") String contentType,
                                          @Body VerifyOtpRequest verifyOtpRequest,
                                          @Path("userId") String userId);

    @PUT("user/forgotpassword/{userId}")
    public Call<SuccessMessage> forgotPassword(@Header("Accept-Language") String acceptLanguage,
                                               @Header("Content-Type") String contentType,
                                               @Body UserForgotPasswordRequest userForgotPasswordRequest);
    @POST("logout")
    public Call<SuccessMessage> logout(@Header("Accept-Language") String acceptLanguage,
                                       @Header("Content-Type") String contentType,
                                       @Header("Authorization") String authHeader
    );
}
