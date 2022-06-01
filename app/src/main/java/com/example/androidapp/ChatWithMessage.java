package com.example.androidapp;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ChatWithMessage {
    @Embedded
    Chat chat;
    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    List<Message> message;
}
