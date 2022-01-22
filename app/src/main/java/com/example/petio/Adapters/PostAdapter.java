package com.example.petio.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petio.Models.Post;
import com.example.petio.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private ArrayList<Post> postList;
    private OnNoteListener mOnNoteListener;

    public PostAdapter(ArrayList<Post> postList,OnNoteListener onNoteListener) {
        this.postList = postList;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.post_item, parent, false);

        return new PostHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder postHolder, int position) {
        String imageurl = postList.get(position).url;
        String imagedesc = postList.get(position).description;
        String useremail = postList.get(position).useremail;
        String postcity = postList.get(position).postcity;

        postHolder.postDesc.setText(imagedesc);
        postHolder.postCity.setText(postcity);
        postHolder.userName.setText(useremail);


        Picasso.get().load(imageurl).into(postHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView, menuButton;
        private TextView postDesc, postCity, userName;
        private OnNoteListener onNoteListener;
        private FirebaseFirestore firebaseFirestore;
        private FirebaseStorage firebaseStorage;

        public PostHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.postitemimage);
            postCity = itemView.findViewById(R.id.postitemcity);
            postDesc = itemView.findViewById(R.id.postitemdesc);
            userName = itemView.findViewById(R.id.postitemusername);
            menuButton = itemView.findViewById(R.id.menubutton);
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseStorage = FirebaseStorage.getInstance();
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
