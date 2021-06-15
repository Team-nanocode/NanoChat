package com.example.nanochat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        int[] numbers = new int[]{1 , 2 , 3, 4 , 5 , 6 , 7 , 8 , 9 , 10,11,12,13,14,15};

        RecyclerView recyclerView = findViewById(R.id.chats);
        ChatListAdapter adapter = new ChatListAdapter(numbers);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}