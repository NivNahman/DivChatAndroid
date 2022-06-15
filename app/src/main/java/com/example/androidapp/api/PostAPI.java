package com.example.androidapp.api;

import android.widget.Toast;

import com.example.androidapp.Contact;
import com.example.androidapp.LoginPage;
import com.example.androidapp.MyApplication;
import com.example.androidapp.R;
import com.example.androidapp.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAPI {

    Retrofit retrofit;
    private WebServiceAPI webServiceAPI;

    public WebServiceAPI getWebServiceAPI() {
        return webServiceAPI;
    }

    public void setWebServiceAPI(WebServiceAPI webServiceAPI) {
        this.webServiceAPI = webServiceAPI;
    }

    public PostAPI() {

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

//    public void get() {
//        Call<List<User>> call = webServiceAPI.getUsers();
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                //7261
//                List<User> users = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(LoginPage.this,"FAILED !!!!!!!!!!!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
