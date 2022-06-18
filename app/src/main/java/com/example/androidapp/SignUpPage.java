package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.api.PostAPI;
import com.example.androidapp.api.WebServiceAPI;
import com.example.androidapp.databinding.ActivitySignUpBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPage extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.signupBtn.setOnClickListener(v -> {
            if(binding.Username.getText().toString().isEmpty() || binding.Password.getText().toString().isEmpty() ||
            binding.Nickname.getText().toString().isEmpty() || binding.Password2.getText().toString().isEmpty()){
                Toast.makeText(SignUpPage.this, "There is an empty field", Toast.LENGTH_SHORT).show();
            }
            else if(binding.Password.getText().toString().compareTo(binding.Password2.getText().toString()) != 0){
                Toast.makeText(SignUpPage.this, "The Passwords Do Not Match", Toast.LENGTH_SHORT).show();
            }
            else if(!binding.Password.getText().toString().matches(".*[0-9].*")){
                Toast.makeText(SignUpPage.this, "Password must contain a number", Toast.LENGTH_SHORT).show();
            }
            else if(!binding.Password.getText().toString().matches(".*[A-Z].*")){
                Toast.makeText(SignUpPage.this, "Password must contain a uppercase letter", Toast.LENGTH_SHORT).show();
            }
            else{
                PostAPI postAPI = new PostAPI();
                WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
                Call<Void> call = webServiceAPI.signup(binding.Username.getText().toString(), binding.Nickname.getText().toString(), binding.Password.getText().toString());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent(SignUpPage.this, ContactList.class);
                        intent.putExtra("username",binding.Username.getText().toString());
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        binding.toLogin.setOnClickListener(v -> {
                    Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                    intent.putExtra("username",binding.Username.getText().toString());
                    startActivity(intent);
        });
        Log.i("SignUpPage", "onCreate");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("SignUpPage", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("SignUpPage", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("SignUpPage", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("SignUpPage", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("SignUpPage", "onRestart");
    }
}
