package com.example.androidapp.api;
import com.example.androidapp.Contact;
//import retrofit2.Call;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import java.util.List;

public interface WebServiceAPI {
    @GET("contacts")
    Call<List<Contact>> getContacts();

    @POST("contacts")
    Call<Void> createPost(@Body Contact contact);

//    @DELETE("posts/{id}")
//    Call<Void> deletePost(@Path("id") int id);
}
