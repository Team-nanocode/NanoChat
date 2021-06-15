package com.example.nanochat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatMessages";
    List<Message> chat = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        fillChat();

        Log.d(TAG,"onCreate : "+chat.toString());

        recyclerView = findViewById(R.id.chatRecyclerView);

        recyclerView.setHasFixedSize(false);

        //use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //specify an adapter
        mAdapter = new MessageAdapter(chat,ChatActivity.this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.scrollToPosition(chat.size() - 1);
    }

    private void fillChat() {
        Message m0 = new Message(1,"Hi","06:30","Nuwan",true);
        Message m1 = new Message(2,"Good Morning","06:31","Nuwan",true);
        Message m2 = new Message(3,"Good Morning","06:32","Ravi",false);
        Message m3 = new Message(4,"How are you?","06:33","Ravi",false);
        Message m4 = new Message(5,"Feeling Good","06:34","Nuwan",true);
        Message m5 = new Message(6,"Thanks for asking","06:35","Nuwan",true);
        Message m6 = new Message(7,"Nice talking with u.Bye","06:36","Ravi",false);
        Message m7 = new Message(8,"Bye","06:37","Nuwan",true);
        Message m8 = new Message(9,"See u at lecture","06:38","Ravi",false);
        chat.addAll(Arrays.asList(m0, m1, m2, m3, m4, m5, m6, m7, m8));
    }
}