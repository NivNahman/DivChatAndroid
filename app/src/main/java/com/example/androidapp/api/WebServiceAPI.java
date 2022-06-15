package com.example.androidapp.api;
import com.example.androidapp.Contact;
import com.example.androidapp.User;
//import retrofit2.Call;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import java.util.List;

public interface WebServiceAPI {
    @POST("Users/login/{username}/{password}")
    Call<User> login(@Path("username") String loginUsername,@Path("password") String loginPassword);

    @GET("contacts?connecteduser={username}")
    Call<List<Contact>> getcontacts(@Path("username") String username);

//    @DELETE("posts/{id}")
//    Call<Void> deletePost(@Path("id") int id);
}
