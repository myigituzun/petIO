package com.example.petio.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petio.R;

public class ChatActivity extends AppCompatActivity {
    private String userEmail,userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("useremail");
        userName = intent.getStringExtra("username");
    }
}