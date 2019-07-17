package com.example.fruituser.Client;


import com.example.fruituser.model.APIResponse;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ApiUtils {


    public static final String BASE_URL ="http://fruitappenv-env.rtk3mtcj7e.us-east-1.elasticbeanstalk.com/fruitapp/api/";
//    public static final String BASE_URL = "http://192.168.0.102:8099/fruitapp/api/";
//    public static final String BASE_URL = "http://matrimonialenv-env.jnr3iswkn4.us-east-2.elasticbeanstalk.com/api/"+ AppConstant.tenantId + "/";

//    public static final String BASE_URL = "http://fruittestdev-env.eaahux2f9i.us-east-2.elasticbeanstalk.com/api/";
    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static Converter<ResponseBody, APIResponse> getConverter(){
        Retrofit retrofit=RetrofitClient.getClient(BASE_URL);
        Converter<ResponseBody, APIResponse> converter=retrofit.responseBodyConverter(APIResponse.class,new Annotation[0]);
        return converter;
    }

}