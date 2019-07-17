package com.example.fruituser.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fruituser.Activities.forgot_password;
import com.example.fruituser.Client.APIService;
import com.example.fruituser.Client.ApiUtils;
import com.example.fruituser.R;
import com.example.fruituser.model.APIResponse;
import com.example.fruituser.model.DeviceDetails;
import com.example.fruituser.model.LoginRequest;
import com.example.fruituser.model.LoginResponse;
import com.example.fruituser.Activities.nav_drawer;
import com.example.fruituser.utils.AppConstant;
import com.example.fruituser.utils.ErrorUtils;
import com.example.fruituser.utils.SessionManagement;
import com.example.fruituser.utils.StringUtils;
import com.google.gson.Gson;

import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_login extends Fragment {

    public static final String TAG="Login ";

    AppCompatEditText email;
    AppCompatEditText  password;
    ProgressBar pb;

    ImageView img;

    Button btnLogin;

    //DeviceDetails deviceDetails =null;

    private String deviceUid,deviceToken,deviceName,deviceType;

    private APIService apiService;
    SessionManagement session;

    String Username,Password;

    TextInputLayout usernameWrapper;
    TextInputLayout passwordWrapper;

    public TextView txtForgotPassword;

    public fragment_login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container, false);

        // Session Manager
        session = new SessionManagement(getContext().getApplicationContext());

        usernameWrapper = (TextInputLayout)view.findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout)view.findViewById(R.id.loginPasswordWrapper);
        email=(AppCompatEditText )usernameWrapper.getEditText();
        password=(AppCompatEditText)passwordWrapper.getEditText();
        img=(ImageView)view.findViewById(R.id.imageLogo);
        btnLogin=(Button)view.findViewById(R.id.btnLogin);


        apiService= ApiUtils.getAPIService();

        //Get the instance of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        //  DeviceUId = tm.getSubscriberId();//Device UID
        deviceUid= Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        deviceName = tm.getNetworkOperatorName();//Device Name
        deviceToken = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        //Get the phone type
        deviceType="";

        int phoneType=tm.getPhoneType();

        switch (phoneType)
        {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                deviceType="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                deviceType="GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                deviceType="NONE";
                break;
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This is login method", Toast.LENGTH_SHORT).show();
                Username=email.getText().toString();
                Password=password.getText().toString();

                usernameWrapper.setError(null);
                passwordWrapper.setError(null);

                if(!TextUtils.isEmpty(Username) && !TextUtils.isEmpty(Password)) {

                    boolean status = false;

                    final LoginRequest loginRequest = new LoginRequest();

                    status = StringUtils.validateEmailAddress(Username);


                    if (status) {
                        loginRequest.setEmail(Username);
                    }

                    status = StringUtils.validateMobileNumber(Username);
                    if (status) {
                        loginRequest.setPhoneNumber(Username);
                    }

                    loginRequest.setPassword(Password);

                    DeviceDetails deviceDetails = new DeviceDetails();

                    deviceDetails.setDeviceUid(deviceUid);
                    deviceDetails.setDeviceName(deviceName);
                    deviceDetails.setDeviceToken(deviceToken);
                    deviceDetails.setDeviceType(deviceType);

                    Gson gson = new Gson();
                    String deviceDetailsString = gson.toJson(deviceDetails);

                    Log.d(TAG, "User Device details =====> "+deviceDetailsString);

                    loginRequest.setDeviceInfo(deviceDetails);

                    sendRequestForLogin(loginRequest);
                }


            }
        });


        txtForgotPassword = (TextView) view.findViewById(R.id.txtForgotPassword);
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), forgot_password.class);
                startActivity(intent);
            }
        });

        return view;


    }

    private void sendRequestForLogin(LoginRequest loginRequest){

        Gson gson = new Gson();
        String json=gson.toJson(loginRequest);
        Log.d(TAG, "Json for Login ===> : "+json);

        apiService.userLogin(AppConstant.acceptLangauge, AppConstant.contentType,loginRequest).enqueue(new Callback<LoginResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {

                    Log.d("Post Result Success " ,response.body().toString());
                    Intent intent=new Intent(getActivity(), nav_drawer.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Welcome "+Username, Toast.LENGTH_SHORT).show();
                    clearLoginScreen();
                }else{
                    Optional<APIResponse> apiResponse= ErrorUtils.parseError(response);

                    if(apiResponse.isPresent()){

                            Log.d(TAG, "onResponse: ======> "+apiResponse.get().getMessage());
                            Toast.makeText(getContext(),apiResponse.get().getMessage(),Toast.LENGTH_LONG).show();
                        }
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Post Result Failure", "Unable to get Result."+call.request().body().toString());
                Log.d("Generated Url", call.request().url().toString());

            }
        });

    }

    public void clearLoginScreen(){
        email.setText("");
        password.setText("");
    }


}
