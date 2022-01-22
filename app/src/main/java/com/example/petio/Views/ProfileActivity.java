package com.example.petio.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.petio.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView bir = findViewById(R.id.bir);
        TextView iki = findViewById(R.id.iki);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


    }
}