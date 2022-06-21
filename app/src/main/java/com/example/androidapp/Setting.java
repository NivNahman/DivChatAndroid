package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidapp.databinding.ActivityLoginBinding;
import com.example.androidapp.databinding.ActivitySettingBinding;

public class Setting extends AppCompatActivity {
    private EditText text_input;
    private Button submitbtn;
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        text_input = binding.newServer;
        submitbtn = binding.submitBtn;
        Button button = binding.submitBtn;

        submitbtn.setOnClickListener(v->{
            text_input.setText("");
            Toast.makeText(Setting.this, "Don't Change the server forever!", Toast.LENGTH_SHORT).show();
        });
        binding.toLogin.setOnClickListener(v->{
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        });
    }
}