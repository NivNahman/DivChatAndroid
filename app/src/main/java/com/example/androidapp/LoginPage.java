package com.example.androidapp;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.androidapp.api.PostAPI;
import com.example.androidapp.api.WebServiceAPI;
import com.example.androidapp.databinding.ActivityLoginBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    private EditText login_username, login_password;
    private Button login_submitbtn;
    private TextView alreadyHaveAccountBtn;
    private boolean isSigningUp = false;
    private ActivityLoginBinding binding;
    private AppDB db;
    ChatDao postDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        login_username = binding.loginUsername;
        login_password = binding.loginPassword;
        login_submitbtn = binding.loginSubmitBtn;
        alreadyHaveAccountBtn = binding.alreadyHaveAccountBtn;
//        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "DivDB")
//                .allowMainThreadQueries()
//                .build();

//        postDao = db.chatDao();
        postDao = AppDB.getDb(getBaseContext()).chatDao();

        setContentView(binding.getRoot());
        //Button loginBtn = findViewById(R.id.loginbtn);
        binding.loginSubmitBtn.setOnClickListener(v -> {
            if(binding.loginUsername.getText().toString().isEmpty() || binding.loginPassword.getText().toString().isEmpty()){
                Toast.makeText(LoginPage.this, "There is an empty field", Toast.LENGTH_SHORT).show();
            }
            else{
                login(binding.loginUsername.getText().toString(),binding.loginPassword.getText().toString());
            }

        });
        alreadyHaveAccountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpPage.class);
            startActivity(intent);
        });

        Log.i("LoginPage", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LoginPage", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LoginPage", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LoginPage", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LoginPage", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("LoginPage", "onRestart");
    }
    public void login(String username,String password) {
        PostAPI postAPI = new PostAPI();
        WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
        Call<User> call = webServiceAPI.login(username,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //7261
                //Toast.makeText(LoginPage.this, "SUCCESS !!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                User user = response.body();
                Intent intent = new Intent(LoginPage.this, ContactList.class);
                intent.putExtra("username",user.getUsername());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginPage.this, "FAILED !!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}