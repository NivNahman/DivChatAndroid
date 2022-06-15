package com.example.androidapp;//package com.example.divchatandroidapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
@Entity(tableName = "User_table")
public class User {
    @NonNull
    @PrimaryKey
    //private int id;

    private String username;


    private String nickname;


    private String password;

    private String srcImg;

    public User() { }

    public User(int id, String username, String nickname, String password, String srcImg) {
        username = username;
        nickname = nickname;
        password = password;
        srcImg = srcImg;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }

    public String getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(String srcImg) {
        srcImg = srcImg;
    }
}


