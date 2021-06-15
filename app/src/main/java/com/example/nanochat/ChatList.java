package com.example.nanochat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ChatList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int[] numbers = new int[]{1 , 2 , 3, 4 , 5 , 6 , 7 , 8 , 9 , 10};

        RecyclerView recyclerView = findViewById(R.id.chatListReview);
        ChatListAdapter adapter = null;
        adapter = new ChatListAdapter(numbers);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}