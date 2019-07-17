package com.example.fruituser.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fruituser.Activities.MainActivity;
import com.example.fruituser.Client.APIService;
import com.example.fruituser.Client.ApiUtils;
import com.example.fruituser.R;
import com.example.fruituser.model.APIResponse;
import com.example.fruituser.model.DeviceDetails;
import com.example.fruituser.model.FileDto;
import com.example.fruituser.model.LoginResponse;
import com.example.fruituser.model.LoginUserProfile;
import com.example.fruituser.model.SignUpRequest;
import com.example.fruituser.model.SignupResponse;
import com.example.fruituser.model.UserProfile;
import com.example.fruituser.utils.AppConstant;
import com.example.fruituser.utils.ErrorUtils;
import com.example.fruituser.utils.SessionManagement;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_signup extends Fragment {
    public static final String TAG="SignUp ";

    private APIService apiService;
    SessionManagement session;

    SignUpRequest userSignUp;
    LoginUserProfile loginUserProfile;
    FileDto fileDto;


    private AppCompatEditText username;
    private AppCompatEditText firstName;
    private AppCompatEditText lastName;
    private AppCompatEditText email;
    private AppCompatEditText password;
    private AppCompatEditText mobileNo;
    private AppCompatEditText landlineNo;
    private AppCompatEditText dateOfBirth;

    private Button btnSignUp;
    Uri picUri;
    Bitmap myBitmap;
    private TextView txtImage;
    private CircleImageView iv;

    TextInputLayout usernameWrapper,firstNameWrapper, lastNameWrapper, emailwrapper, passwordWrapper, mobileNoWrapper,landlineNoWrapper,birthdateWrapper,profileImageWrapper ;

    String Username,firstname, lastname, Email, Password, mobileno,landlineno,dateofbirth,profileimage;
    String DeviceUId,DeviceName,DeviceType,DeviceToken;

    public fragment_signup() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_signup, container, false);
        apiService= ApiUtils.getAPIService();
        session = new SessionManagement(getContext().getApplicationContext());


        usernameWrapper = (TextInputLayout) view.findViewById(R.id.usernameWrapper);
        firstNameWrapper = (TextInputLayout) view.findViewById(R.id.firstNameWrapper);
        lastNameWrapper = (TextInputLayout) view.findViewById(R.id.lastNameWrapper);
        emailwrapper = (TextInputLayout) view.findViewById(R.id.emailWrapper);
        passwordWrapper = (TextInputLayout) view.findViewById(R.id.passwordWrapper);
        mobileNoWrapper = (TextInputLayout) view.findViewById(R.id.mobileNoWrapper);
        landlineNoWrapper = (TextInputLayout) view.findViewById(R.id.landlineNoWrapper);
        birthdateWrapper = (TextInputLayout) view.findViewById(R.id.birthdateWrapper);


        txtImage=(TextView)view.findViewById(R.id.txtImage);
        iv=(CircleImageView)view.findViewById(R.id.iv);

        username = (AppCompatEditText) usernameWrapper.getEditText();
        firstName = (AppCompatEditText) firstNameWrapper.getEditText();
        lastName = (AppCompatEditText) lastNameWrapper.getEditText();
        email = (AppCompatEditText) emailwrapper.getEditText();
        password = (AppCompatEditText) passwordWrapper.getEditText();
        mobileNo = (AppCompatEditText) mobileNoWrapper.getEditText();
        landlineNo = (AppCompatEditText) landlineNoWrapper.getEditText();
        dateOfBirth = (AppCompatEditText) birthdateWrapper.getEditText();


        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);

        //Get the instance of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        //  DeviceUId = tm.getSubscriberId();//Device UID
        DeviceUId=Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        DeviceName = tm.getNetworkOperatorName();//Device Name
        DeviceToken = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        //Get the phone type
        DeviceType="";

        int phoneType=tm.getPhoneType();

        switch (phoneType)
        {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                DeviceType="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                DeviceType="GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                DeviceType="NONE";
                break;
        }

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(getContext(),onDateSetListener,calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();


            }
        });

        txtImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });

        return view;

    }

    private boolean validate() {
        Username=username.getText().toString();
        firstname=firstName.getText().toString();
        lastname=lastName.getText().toString();
        Email=email.getText().toString();
        Password=password.getText().toString();
        mobileno=mobileNo.getText().toString();
        landlineno=landlineNo.getText().toString();
        dateofbirth=dateOfBirth.getText().toString();

        // Reset errors.
        usernameWrapper.setError(null);
        firstNameWrapper.setError(null);
        lastNameWrapper.setError(null);
        emailwrapper.setError(null);
        passwordWrapper.setError(null);
        mobileNoWrapper.setError(null);
        landlineNoWrapper.setError(null);
        birthdateWrapper.setError(null);

        if(TextUtils.isEmpty(Username)){
            usernameWrapper.setError("Please Enter Usernme");
            return false;
        }

        if (TextUtils.isEmpty(firstname ) ) {
            firstNameWrapper.setError("Please Enter First Name ");
            return false;
        }else if(!firstname.matches("[a-zA-Z ]+")){
            firstNameWrapper.setError("Please Enter valid First Name ");
            return false;
        }

        if (TextUtils.isEmpty(lastname ) ) {
            lastNameWrapper.setError("Please Enter Last Name ");
            return false;
        }else if(!lastname.matches("[a-zA-Z ]+")){
            lastNameWrapper.setError("Please Enter valid Last Name ");
            return false;

        }


        if (TextUtils.isEmpty(Email)) {
            emailwrapper.setError("Email Id is required");
            return false;
        } else if (!Email.matches("^[A-Za-z][A-Za-z0-9]*([._-]?[A-Za-z0-9]+)@[A-Za-z].[A-Za-z]{0,3}?.[A-Za-z]{0,3}$")) {
            emailwrapper.setError("Please Enter valid Email Id");
            return false;
        }

        if (TextUtils.isEmpty(Password)) {
            passwordWrapper.setError("Password is required");
            return false;
        } else if (!isPasswordValid(Password)) {
            passwordWrapper.setError("Password must contain at least 6 characters");
            return false;
        }
        if (TextUtils.isEmpty(mobileno)) {
            mobileNoWrapper.setError("Mobile Number is required");
            return false;
        } else if (!mobileno.matches("^[7-9][0-9]{9}$")) {
            mobileNoWrapper.setError("Please enter valid Mobile Number");
            return false;
        }

        if(TextUtils.isEmpty(landlineno)){
            landlineNoWrapper.setError("Please Enter Landline Number");
            return false;

        }

        if(TextUtils.isEmpty(dateofbirth)){
            birthdateWrapper.setError("Please Enter Birthdate");
            return false;

        }

        fileDto=new FileDto();
        userSignUp=new SignUpRequest();
        loginUserProfile=new LoginUserProfile();

        userSignUp.setUsername(Username);
        userSignUp.setPassword(Password);
        userSignUp.setEmail(Email);
        userSignUp.setFirstName(firstname);
        userSignUp.setLastName(lastname);
        userSignUp.setPhoneNumber(mobileno);
        userSignUp.setLandlineNumber(landlineno);
        userSignUp.setDateOfBirth(dateofbirth);
        userSignUp.setProfileImage(session.getFileId());


        DeviceDetails deviceDetails=new DeviceDetails();
        deviceDetails.setDeviceUid(DeviceUId);
        deviceDetails.setDeviceName(DeviceName);
        deviceDetails.setDeviceType(DeviceType);
        deviceDetails.setDeviceToken(DeviceToken);

        userSignUp.setDeviceInfo(deviceDetails);

        sendRequestForSignUp(userSignUp);
        return true;

    }


    //Check password with minimum requirement here(it should be minimum 6 characters)
    public static boolean isPasswordValid(String Password){
        return Password.length() >= 6;
    }
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String dobIs =dayOfMonth + "/" + (monthOfYear+1) + "/"+year;
            dateOfBirth.setText(dobIs);
        }
    };

    private void sendRequestForSignUp(final SignUpRequest userSignUp){

        apiService.signUp(AppConstant.acceptLangauge,AppConstant.contentType,userSignUp).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                if(response.isSuccessful()) {
                    //session.setUserId(response.body().getProfile().getUserId().toString());

                    loginUserProfile.setUserId(response.body().getProfile().getUserId().toString());

                    Log.d("Post Result Success " ,response.body().toString());
                    Intent intent=new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(),"Sign Up Successfully",Toast.LENGTH_SHORT).show();

                }else{
                    Optional<APIResponse> apiResponse= ErrorUtils.parseError(response);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if(apiResponse.isPresent()){

                            Log.d(TAG, "onResponse: ======> "+apiResponse.get().getMessage());
                            Toast.makeText(getContext(),apiResponse.get().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                //firstName.setText("");
                Log.d("Post Result Failure", "Unable to get Result."+call.request().body().toString());
                Log.d("Generated Url", call.request().url().toString());
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);

            }
        });

    }

    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList();
        PackageManager packageManager = getContext().getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }


    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getContext().getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {

            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), picUri);
                    // myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500);

                    MultipartBody.Part body = getFileFromBitmap(picUri,myBitmap);

                    apiService.profileUpload(AppConstant.acceptLangauge,AppConstant.contentType,body).enqueue(new Callback<FileDto>() {
                        @Override
                        public void onResponse(Call<FileDto> call, Response<FileDto> response) {


                            Log.d("Post Result Success " ,call.request().url().toString());
                            Log.d("Post Result Success " ,response.body().toString());
                            session.setFileId(response.body().getFileId().toString());
                            //Loading Image from URL
                            Picasso.with(getContext())
                                    //.load("http://fruitapp-env-1.iapmt5t7yb.us-east-1.elasticbeanstalk.com/fruitapp/api/downloadfile/thumbnail_"+session.getFileId())
                                    .load("http://fruitappenv-env.rtk3mtcj7e.us-east-1.elasticbeanstalk.com/fruitapp/api/downloadfile/thumbnail_"+session.getFileId())
                                    .resize(400,400)
                                    .into(iv);

                        }

                        @Override
                        public void onFailure(Call<FileDto> call, Throwable t) {

                            Log.d("Post Result Failure", "Unable to get Result."+call.request().body().toString());
                            Log.d("Generated Url", call.request().url().toString());
                        }
                    });
//                    iv.setImageBitmap(myBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {


                bitmap = (Bitmap) data.getExtras().get("data");

                myBitmap = bitmap;

            }

        }

    }

    public  MultipartBody.Part getFileFromBitmap(Uri uri,Bitmap bitmap){

        MultipartBody.Part body =null;
        try{

            //create a file to write bitmap data
            String filename=getFileName(picUri);

            Log.d("File name", "File Name  : "+filename);

            File file = new File(getContext().getApplicationContext().getCacheDir(), filename);
            file.createNewFile();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, byteArrayOutputStream);
            byte[] bitmapdata = byteArrayOutputStream.toByteArray();

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bitmapdata);

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

            fileOutputStream.flush();
            fileOutputStream.close();

            return body;

        }catch (IOException exception){
            exception.printStackTrace();
            return body;
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            picUri = savedInstanceState.getParcelable("pic_uri");;
        }
    }
}
