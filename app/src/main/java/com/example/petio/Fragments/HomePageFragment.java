package com.example.petio.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.petio.R;
import com.example.petio.Views.AddPostActivity;
import com.example.petio.Views.LoginActivity;
import com.example.petio.Views.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomePageFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        CardView signOut = view.findViewById(R.id.signout);
        CardView addPost = view.findViewById(R.id.addPostButton);
        CardView profile = view.findViewById(R.id.profileButton);

        firebaseAuth = FirebaseAuth.getInstance();

        signOut.setOnClickListener(this);
        addPost.setOnClickListener(this);
        profile.setOnClickListener(this);
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signout:
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setMessage("Hesabınızdan çıkış yapmak istiyor musunuz?");
                alert.setPositiveButton("Evet", (dialog, which) -> {
                    Toast.makeText(view.getContext(), "Hesaptan çıkış yapıldı.", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();

                    intent = new Intent(view.getContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                });
                alert.setNegativeButton("Hayır", (dialog, which) -> {
                });
                alert.show();
                break;
            case R.id.profileButton:
                intent = new Intent(view.getContext(), ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.addPostButton:
                intent = new Intent(view.getContext(), AddPostActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}