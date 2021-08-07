package com.pdn.eng.cipher.nanochat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class ChatFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    LinearLayoutManager linearLayoutManager;
    private FirebaseAuth firebaseAuth;
    ImageView mImageViewOfUser;
    FirestoreRecyclerAdapter<FirebaseModel,NoteViewHolder> chatAdapter;

    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chat_fragment,container,false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mRecyclerView = v.findViewById(R.id.recyclerview);

        Query query = firebaseFirestore.collection("Users");

        FirestoreRecyclerOptions<FirebaseModel> allUserNames = new FirestoreRecyclerOptions.Builder<FirebaseModel>().setQuery(query,FirebaseModel.class).build();

        chatAdapter = new FirestoreRecyclerAdapter<FirebaseModel, NoteViewHolder>(allUserNames) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull FirebaseModel firebaseModel) {
                noteViewHolder.particularUserName.setText(firebaseModel.getName());
                String uri = firebaseModel.getImage();

                Picasso.get().load(uri).into(mImageViewOfUser);
                if(firebaseModel.getStatus().equals("Online")){
                    noteViewHolder.statusOfUser.setText(firebaseModel.getStatus());
                    noteViewHolder.statusOfUser.setTextColor(Color.GREEN);
                }
                else {
                    noteViewHolder.statusOfUser.setText(firebaseModel.getStatus());
                }

                noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Item Is Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view_layout,parent,false);
                return new NoteViewHolder(view);
            }
        };

        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(chatAdapter);
        return v;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView particularUserName;
        private TextView statusOfUser;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            particularUserName = itemView.findViewById(R.id.nameofuser);
            statusOfUser = itemView.findViewById(R.id.statusofuser);
            mImageViewOfUser = itemView.findViewById(R.id.imageviewofuser);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        chatAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(chatAdapter != null){
            chatAdapter.stopListening();
        }
    }
}
