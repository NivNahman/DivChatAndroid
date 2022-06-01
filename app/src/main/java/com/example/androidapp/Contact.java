package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Contact_table")
public class Contact {
    @NonNull
    @PrimaryKey
    private String name ;

    private String server;

    private String last;

    private String lastdate;

    public Contact(String name, String server, String last, String lastdate) {
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
    }

    public Contact() {
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }
}
