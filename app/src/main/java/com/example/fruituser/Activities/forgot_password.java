package com.example.fruituser.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fruituser.Client.APIService;
import com.example.fruituser.Client.ApiUtils;
import com.example.fruituser.R;
import com.example.fruituser.model.APIResponse;
import com.example.fruituser.model.LoginUserProfile;
import com.example.fruituser.model.SuccessMessage;
import com.example.fruituser.model.UserForgotEmailVerifyResponse;
import com.example.fruituser.model.UserForgotPasswordRequest;
import com.example.fruituser.model.UserProfile;
import com.example.fruituser.model.VerifyOtpRequest;
import com.example.fruituser.utils.AppConstant;
import com.example.fruituser.utils.ErrorUtils;
import com.example.fruituser.utils.SessionManagement;
import com.google.gson.Gson;

import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgot_password extends AppCompatActivity {

   private static final String TAG = forgot_password.class.getSimpleName();
    //private static final String TAG = "forgot_password";

    SessionManagement session;
    VerifyOtpRequest verifyOtpRequest;
    UserForgotEmailVerifyResponse userForgotEmailVerifyResponse;
    UserProfile userProfile;

    private LoginUserProfile loginUserProfile;
    private RelativeLayout relativeLayoutForForgotPassword;

    private ProgressDialog progressDialog;

    private APIService apiService;

    private LinearLayout linearLayoutForEmail,linearLayoutForForgotPasswordVerifyOtp,linearLayoutForResetPassword;
    private EditText edtForgotPasswordEmail,edtForgotPasswordVerifyOtp,edtForgotPasswordNewPassword,edtForgotPasswordConfirmPassword;
    private Button btnReset,btnForgotPasswordVerifyOtp,btnForgotPasswordSubmit;


    private String Email,Otp,NewPassword,ConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        apiService= ApiUtils.getAPIService();
        //session = new SessionManagement(getApplicationContext());

        relativeLayoutForForgotPassword = (RelativeLayout)findViewById(R.id.relativeLayoutForForgotPassword);

        linearLayoutForEmail = (LinearLayout)findViewById(R.id.linearLayoutForEmail);
        linearLayoutForForgotPasswordVerifyOtp = (LinearLayout)findViewById(R.id.linearLayoutForForgotPasswordVerifyOtp);
        linearLayoutForResetPassword = (LinearLayout)findViewById(R.id.linearLayoutForResetPassword);

        edtForgotPasswordEmail=(EditText)findViewById(R.id.edtForgotPasswordEmail);
        edtForgotPasswordVerifyOtp = (EditText)findViewById(R.id.edtForgotPasswordVerifyOtp);
        edtForgotPasswordNewPassword = (EditText)findViewById(R.id.edtForgotPasswordNewPassword);
        edtForgotPasswordConfirmPassword = (EditText)findViewById(R.id.edtForgotPasswordConfirmPassword);

        btnReset=(Button)findViewById(R.id.btnReset);
        btnForgotPasswordVerifyOtp=(Button)findViewById(R.id.btnForgotPasswordVerifyOtp);
        btnForgotPasswordSubmit=(Button)findViewById(R.id.btnForgotPasswordSubmit);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        btnForgotPasswordVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Otp=edtForgotPasswordVerifyOtp.getText().toString();
                verifyOtpRequest = new VerifyOtpRequest();
                verifyOtpRequest.setUserId(userForgotEmailVerifyResponse.getUserId());
                verifyOtpRequest.setOtp(Otp);

                if(!TextUtils.isEmpty(Otp)){
                    if(Otp.length() >= 6 ){
                        //progressDialog.show();
                        sendRequestForVerifyOtp(verifyOtpRequest);
                    }else{
                        Toast.makeText(forgot_password.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    edtForgotPasswordVerifyOtp.setError("Enter OTP");
                }

            }
        });

        btnForgotPasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatepassword();
            }
        });
    }

    private boolean validatepassword() {
        NewPassword=edtForgotPasswordNewPassword.getText().toString();
        ConfirmPassword=edtForgotPasswordConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(NewPassword)&& TextUtils.isEmpty(ConfirmPassword)) {
            Toast.makeText(forgot_password.this, "Enter Both Fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!NewPassword.matches(ConfirmPassword)) {
            Toast.makeText(forgot_password.this, "Password do not match", Toast.LENGTH_SHORT).show();

            return false;
        }

        UserForgotPasswordRequest userForgotPasswordRequest=new UserForgotPasswordRequest();
        userForgotPasswordRequest.setUserId(userForgotEmailVerifyResponse.getUserId());
        userForgotPasswordRequest.setOtp(Otp);
        userForgotPasswordRequest.setNewPassword(ConfirmPassword);

        sendRequestForForgotPassword(userForgotPasswordRequest);

        Intent intent=new Intent(forgot_password.this,MainActivity.class);
        startActivity(intent);
        return true;
    }

    private boolean validate() {
        Email=edtForgotPasswordEmail.getText().toString();
        // Reset errors.
        edtForgotPasswordEmail.setError(null);

        if (TextUtils.isEmpty(Email)) {
            edtForgotPasswordEmail.setError("Email is required");
            return false;
        } else if (!Email.matches("^[A-Za-z][A-Za-z0-9]*([._-]?[A-Za-z0-9]+)@[A-Za-z].[A-Za-z]{0,3}?.[A-Za-z]{0,3}$")) {
            edtForgotPasswordEmail.setError("Enter a valid email");
            return false;
        }
        sendRequestForVerifyEmail();
        return true;

    }

    private void sendRequestForVerifyEmail(){

        apiService.verifyEmail(AppConstant.acceptLangauge,AppConstant.contentType,Email).enqueue(new Callback<UserForgotEmailVerifyResponse>() {
            @Override
            public void onResponse(Call<UserForgotEmailVerifyResponse> call, Response<UserForgotEmailVerifyResponse> response) {
                if(response.isSuccessful()) {

                    Log.d("Post Result Success " ,response.body().toString());

                    userForgotEmailVerifyResponse=response.body();
                    linearLayoutForEmail.setVisibility(View.GONE);
                    linearLayoutForForgotPasswordVerifyOtp.setVisibility(View.VISIBLE);
                    linearLayoutForResetPassword.setVisibility(View.GONE);
                }else{
                    Optional<APIResponse> apiResponse= ErrorUtils.parseError(response);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if(apiResponse.isPresent()){

                            Log.d(TAG, "onResponse: ======> "+apiResponse.get().getMessage());
                            Toast.makeText(forgot_password.this,apiResponse.get().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<UserForgotEmailVerifyResponse> call, Throwable t) {
                Log.d("Post Result Failure", "Unable to get Result."+call.request().url().toString());

                Log.d("Generated Url", call.request().url().toString());
                Intent intent=new Intent(forgot_password.this, forgot_password.class);
                startActivity(intent);

            }
        });

    }

    private void sendRequestForVerifyOtp(VerifyOtpRequest verifyOtpRequest){

        Gson gson = new Gson();
        String otpJson = gson.toJson(verifyOtpRequest);

        Log.d(TAG, "sendRequestForVerifyOtp: "+otpJson);


        apiService.verifyOtp(AppConstant.acceptLangauge,AppConstant.contentType,verifyOtpRequest,userForgotEmailVerifyResponse.getUserId()).enqueue(new Callback<SuccessMessage>() {
            @Override
            public void onResponse(Call<SuccessMessage> call, Response<SuccessMessage> response) {
                if(response.isSuccessful()) {

                    Log.d("Post Result Success " ,call.request().url().toString());
                    linearLayoutForEmail.setVisibility(View.GONE);
                    linearLayoutForForgotPasswordVerifyOtp.setVisibility(View.GONE);
                    linearLayoutForResetPassword.setVisibility(View.VISIBLE);
                    btnForgotPasswordVerifyOtp.setVisibility(View.GONE);

                }else{
                    Optional<APIResponse> apiResponse= ErrorUtils.parseError(response);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if(apiResponse.isPresent()){

                            Log.d(TAG, "onResponse: ======> "+apiResponse.get().getMessage());
                            Toast.makeText(forgot_password.this,apiResponse.get().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }



            @Override
            public void onFailure(Call<SuccessMessage> call, Throwable t) {
                Log.d("Post Result Failure", "Unable to get Result."+call.request().body().toString());
//                Intent intent=new Intent(getContext(), MainActivity.class);
//                startActivity(intent);
                Log.d("Generated Url", call.request().url().toString());

            }
        });

    }


    private void sendRequestForForgotPassword(UserForgotPasswordRequest userForgotPasswordRequest){

        apiService.forgotPassword(AppConstant.acceptLangauge,AppConstant.contentType,userForgotPasswordRequest).enqueue(new Callback<SuccessMessage>() {
            @Override
            public void onResponse(Call<SuccessMessage> call, Response<SuccessMessage> response) {
                if(response.isSuccessful()) {

                    Log.d("Post Result Success " ,response.body().toString());

                }else{
                    Optional<APIResponse> apiResponse= ErrorUtils.parseError(response);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if(apiResponse.isPresent()){

                            Log.d(TAG, "onResponse: ======> "+apiResponse.get().getMessage());
                            Toast.makeText(forgot_password.this,apiResponse.get().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }



            @Override
            public void onFailure(Call<SuccessMessage> call, Throwable t) {
                Log.d("Post Result Failure", "Unable to get Result."+call.request().body().toString());
//                Intent intent=new Intent(getContext(), MainActivity.class);
//                startActivity(intent);
                Log.d("Generated Url", call.request().url().toString());

            }
        });

    }
}
