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

import com.example.androidapp.databinding.ActivityLoginBinding;


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
            Intent intent = new Intent(this, ContactList.class);
            startActivity(intent);
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
}