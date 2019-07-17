package com.example.fruituser.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.fruituser.Activities.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SessionManagement {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "FruitAppUser";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";


    public static final String KEY_SESSIONKEY = "sessionKey";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERID = "userId";

    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setSessionKey(String sessionKey){

        editor.putBoolean(IS_LOGIN, true);

        editor.putString("sessionKey", sessionKey);

        editor.commit();
    }

    public String getSessionKey(){
        String sessionKey=pref.getString("sessionKey",null);
        return sessionKey;
    }

    //File ID
    public void setFileId(String fileId){

        editor.putBoolean(IS_LOGIN, true);

        editor.putString("fileId",fileId);

        editor.commit();
    }
    public String getFileId(){
        String fileId=pref.getString("fileId",null);
        return fileId;
    }

    //Email
    public void setEmail(String email){

        editor.putBoolean(IS_LOGIN, true);


        editor.putString("email",email);


        editor.commit();
    }

    public String getEmail(){
        String Email=pref.getString("email",null);
        return Email;
    }
//User ID
    public void setUserId(String userId){

        editor.putBoolean(IS_LOGIN, true);


        editor.putString("userId",userId);


        editor.commit();
    }
    public String getUserId(){
        String UserId=pref.getString("userId",null);
        return UserId;
    }

//First name
   public void setFirstName(String firstName){

        editor.putBoolean(IS_LOGIN, true);


        editor.putString("firstName",firstName);


        editor.commit();
    }
    public String getFirstName(){
        String firstName=pref.getString("firstName",null);
        return firstName;
    }

    //Profile Image
    public void setProfileImage(String profileImage){

        editor.putBoolean(IS_LOGIN, true);


        editor.putString("profileImage",profileImage);


        editor.commit();
    }
    public String getProfileImage(){
        String profileImage=pref.getString("profileImage",null);
        return profileImage;
    }

    //Last name
    public void setLastName(String lastName){

        editor.putBoolean(IS_LOGIN, true);


        editor.putString("lastName",lastName);


        editor.commit();
    }
    public String getLastName(){
        String lastName=pref.getString("lastName",null);
        return lastName;
    }

    public void setPhoneNumber(String phoneNumber){

        editor.putBoolean(IS_LOGIN, true);


        editor.putString("phoneNumber",phoneNumber);


        editor.commit();
    }
    public String getPhoneNumber(){
        String phoneNumber=pref.getString("phoneNumber",null);
        return phoneNumber;
    }


    //User Profile
    //Username
    public void setUsername(String username){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString("username",username);

        // commit changes
        editor.commit();
    }
    public String getUsername(){
        String username=pref.getString("username",null);
        return username;
    }

    //FirstName
    public void setCurrentUserFirstName(String firstname){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString("firstname",firstname);

        // commit changes
        editor.commit();
    }
    public String getCurrentUserFirstName(){
        String firstname=pref.getString("firstname",null);
        return firstname;
    }

    //LastName
    public void setCurrentUserLastName(String lastname){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString("lastname",lastname);

        // commit changes
        editor.commit();
    }
    public String getCurrentUserLastName(){
        String lastname=pref.getString("lastname",null);
        return lastname;
    }

    //Email
    public void setCurrentUserEmail(String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString("email",email);

        // commit changes
        editor.commit();
    }
    public String getCurrentUserEmail(){
        String email=pref.getString("email",null);
        return email;
    }


    //Date of Birth
    public void setCurrentUserDOB(String dateOfBirth){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString("dateOfBirth",dateOfBirth);

        // commit changes
        editor.commit();
    }
    public String getCurrentUserDOB(){
        String dateOfBirth=pref.getString("dateOfBirth",null);
        return dateOfBirth;
    }

    //PhoneNo
    public void setCurrentUserPhoneNo(String PhoneNo){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString("PhoneNo",PhoneNo);

        // commit changes
        editor.commit();
    }
    public String getCurrentUserPhoneNo(){
        String PhoneNo=pref.getString("PhoneNo",null);
        return PhoneNo;
    }
    //LandlineNo
    public void setCurrentUserLandlineNo(String LandlineNo){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString("LandlineNo",LandlineNo);

        // commit changes
        editor.commit();
    }
    public String getCurrentUserLandlineNo(){
        String LandlineNo=pref.getString("LandlineNo",null);
        return LandlineNo;
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    //Refine by
    //Refine By
    public void setRefineBy(String refineBy){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString("refineBy",refineBy);

        // commit changes
        editor.commit();
    }
    public String getRefineBy(){
        String refineBy=pref.getString("refineBy",null);
        return refineBy;
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
