package com.example.petio.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petio.Adapters.PostAdapter;
import com.example.petio.Models.Post;
import com.example.petio.R;
import com.example.petio.Views.AddPostActivity;
import com.example.petio.Views.LoginActivity;
import com.example.petio.Views.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class HomePageFragment extends Fragment implements View.OnClickListener,PostAdapter.OnNoteListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Intent intent;
    private String email;
    private ArrayList<Post> postList;
    private PostAdapter postAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        CardView signOut = view.findViewById(R.id.signout);
        CardView addPost = view.findViewById(R.id.addPostButton);
        CardView profile = view.findViewById(R.id.profileButton);
        RecyclerView recyclerView = view.findViewById(R.id.homePageRecyclerView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        email = firebaseAuth.getCurrentUser().getEmail();

        postList = new ArrayList<>();

        getPostData();

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        postAdapter = new PostAdapter(postList,this);
        recyclerView.setAdapter(postAdapter);

        signOut.setOnClickListener(this);
        addPost.setOnClickListener(this);
        profile.setOnClickListener(this);
        return view;
    }

    public void getPostData(){
        firebaseFirestore.collection("Kullanicilar").document(email).addSnapshotListener((value, error) -> {
            if (value != null){
                String city = value.getString("Sehir");

                firebaseFirestore.collection("Posts").whereEqualTo("postcity",city).orderBy("date", Query.Direction.DESCENDING).addSnapshotListener((value1, error1) -> {
                    if (value1 !=null){

                        for (DocumentSnapshot documentSnapshot: value1.getDocuments()){
                            String imagedesc = documentSnapshot.getString("imagedesc");
                            String imageurl = documentSnapshot.getString("imageurl");
                            String useremail = documentSnapshot.getString("useremail");
                            String postcity = documentSnapshot.getString("postcity");
                            String postid = documentSnapshot.getId();

                            Post post = new Post(imagedesc,imageurl,useremail,postid,postcity);
                            postList.add(post);

                            postAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
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



    @Override
    public void onNoteClick(int position) {

    }
}