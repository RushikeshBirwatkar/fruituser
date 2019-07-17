package com.example.fruituser.utils;

import android.annotation.TargetApi;
import android.os.Build;

import com.example.fruituser.Client.ApiUtils;
import com.example.fruituser.model.APIResponse;

import java.io.IOException;
import java.util.Optional;


import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    @TargetApi(Build.VERSION_CODES.N)
    public static Optional<APIResponse> parseError(Response<?> response) {



        Converter<ResponseBody, APIResponse> errorConverter = ApiUtils.getConverter();
        try {
            APIResponse error = errorConverter.convert(response.errorBody());

            if(error!=null){
                return Optional.of(error);
            }

        } catch (IOException e) {
            e.printStackTrace();

            return Optional.empty();
        }
        return Optional.empty();
    }
}