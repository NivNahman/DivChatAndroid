package com.example.androidapp.api;
import android.net.Uri;

import com.example.androidapp.Addmsg;
import com.example.androidapp.Contact;
import com.example.androidapp.Message;
import com.example.androidapp.User;
//import retrofit2.Call;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface WebServiceAPI {
    static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

    @POST("Users/login/{username}/{password}")
    Call<User> login(@Path("username") String loginUsername,@Path("password") String loginPassword);

    @GET("contacts")
    Call<List<Contact>> getcontacts(@Query("connecteduser") String username);

    @GET("Users/messages")
    Call<List<Message>> getmessageofuser(@Query("connecteduser") String username);

    @GET("contacts/{contactname}/messages")
    Call<List<Message>> getmessages(@Path("contactname") String contactname,@Query("connecteduser") String username);

    @POST("contacts/{contactname}/messages")
    Call<Void> newmessage(@Path("contactname") String contactname2, @Body Addmsg addmsg);
//    @DELETE("posts/{id}")
//    Call<Void> deletePost(@Path("id") int id);
}
