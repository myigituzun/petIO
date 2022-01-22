package com.example.petio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class HomePageFragment extends Fragment {
    private View view;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        CardView signOut = view.findViewById(R.id.signout);
        firebaseAuth = FirebaseAuth.getInstance();

        signOut.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
            alert.setMessage("Hesabınızdan çıkış yapmak istiyor musunuz?");
            alert.setPositiveButton("Evet", (dialog, which) -> {
                Toast.makeText(v.getContext(), "Hesaptan çıkış yapıldı.",Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();

                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            });
            alert.setNegativeButton("Hayır", (dialog, which) -> {
            });
            alert.show();
        });
        return view;
    }
}