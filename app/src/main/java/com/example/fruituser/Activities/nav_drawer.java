package com.example.fruituser.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.fruituser.Client.APIService;
import com.example.fruituser.R;
import com.example.fruituser.model.APIResponse;
import com.example.fruituser.model.LogoutRequest;
import com.example.fruituser.model.SuccessMessage;
import com.example.fruituser.utils.AppConstant;
import com.example.fruituser.utils.ErrorUtils;
import com.example.fruituser.utils.SessionManagement;

import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class nav_drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG="nav_drawer ";

    private APIService apiService;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        //ImageAdapter adapterView = new ImageAdapter(this);
        //mViewPager.setAdapter(adapterView);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_logout) {
            LogoutRequest logoutRequest=new LogoutRequest();
            sendRequestForLogout(logoutRequest);

        }
        return true;
    }
    private void sendRequestForLogout(LogoutRequest logoutRequest){

        apiService.logout(AppConstant.acceptLangauge,AppConstant.contentType,session.getSessionKey()).enqueue(new Callback<SuccessMessage>() {
            @Override
            public void onResponse(Call<SuccessMessage> call, Response<SuccessMessage> response) {
                if(response.isSuccessful()) {

                    Log.d("Post Result Success " ,response.body().toString());
                    Intent intent=new Intent(nav_drawer.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Optional<APIResponse> apiResponse= ErrorUtils.parseError(response);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if(apiResponse.isPresent()){

                            Log.d(TAG, "onResponse: ======> "+apiResponse.get().getMessage());
                            Toast.makeText(nav_drawer.this,apiResponse.get().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<SuccessMessage> call, Throwable t) {
                Log.d("Post Result Failure", "Unable to get Result."+call.request().body().toString());

                Log.d("Generated Url", call.request().url().toString());

            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
