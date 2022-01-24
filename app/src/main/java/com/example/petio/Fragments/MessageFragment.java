package com.example.petio.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petio.Adapters.ChatListAdapter;
import com.example.petio.Models.ChatList;
import com.example.petio.R;
import com.example.petio.Views.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MessageFragment extends Fragment implements ChatListAdapter.OnNoteListener {
    private View view;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String meEmail;
    private ArrayList<ChatList> chatLists;
    private ArrayList<String> emailForChatActivity;
    private ChatListAdapter chatListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        meEmail = firebaseAuth.getCurrentUser().getEmail();

        chatLists = new ArrayList<>();
        emailForChatActivity = new ArrayList<>();

        getDataFromFirestore();

        RecyclerView recyclerView = view.findViewById(R.id.chatListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        chatListAdapter = new ChatListAdapter(chatLists,this);
        recyclerView.setAdapter(chatListAdapter);

        return view;
    }

    private void getDataFromFirestore() {

        firebaseFirestore.collection("ChatList" + meEmail).orderBy("date", Query.Direction.DESCENDING).addSnapshotListener((value, error) -> {
            if (value != null) {
                chatLists.clear();
                emailForChatActivity.clear();

                for (DocumentSnapshot documentSnapshot : value.getDocuments()) {

                    String lastmessage = documentSnapshot.getString("lastmessage");
                    String usermame = documentSnapshot.getString("username");
                    String useremail = documentSnapshot.getId();

                    ChatList chatListData = new ChatList(usermame, lastmessage);

                    chatLists.add(chatListData);
                    emailForChatActivity.add(useremail);

                    chatListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent1 = new Intent(getContext(), ChatActivity.class);
        intent1.putExtra("useremail",emailForChatActivity.get(position));
        intent1.putExtra("username",chatLists.get(position).userName);
        startActivity(intent1);
    }
}