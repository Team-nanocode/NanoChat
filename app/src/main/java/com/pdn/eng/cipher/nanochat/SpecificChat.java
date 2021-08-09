package com.pdn.eng.cipher.nanochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.internal.cache.DiskLruCache;

public class SpecificChat extends AppCompatActivity {

    EditText mGetMessage;
    ImageButton mSendMessageButton;

    CardView mSendMessage;
    androidx.appcompat.widget.Toolbar mToolBarOfSpecificChat;
    ImageView mImageViewOfSpecificUser;
    TextView mNameOfSpecificUser;

    private String enteredMessage;
    Intent intent;
    String mReceiverName, senderName, mReceiverUID, mSenderUID;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String senderRoom, receiverRoom;

    ImageButton mBackButtonOfSpecificChat;

    RecyclerView mMessageRecyclerView;

    String currentTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    MessagesAdapter messagesAdapter;
    ArrayList<Message> messageArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_chat);


        mGetMessage = findViewById(R.id.getmessage);
        mSendMessage = findViewById(R.id.carviewofsendmessage);
        mSendMessageButton = findViewById(R.id.imageviewsendmessage);
        mToolBarOfSpecificChat = findViewById(R.id.toolbarofspecificchat);
        mNameOfSpecificUser = findViewById(R.id.Nameofspecificuser);
        mImageViewOfSpecificUser = findViewById(R.id.specificuserimageinimageview);
        mBackButtonOfSpecificChat = findViewById(R.id.backbuttonofspecificchat);
        intent = getIntent();

        setSupportActionBar(mToolBarOfSpecificChat);

        mToolBarOfSpecificChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ToolBar is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        messageArrayList = new ArrayList<>();
        mMessageRecyclerView = findViewById(R.id.recyclerviewofspecific);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(SpecificChat.this,messageArrayList);
        mMessageRecyclerView.setAdapter(messagesAdapter);

        mSenderUID = firebaseAuth.getUid();
        mReceiverUID = getIntent().getStringExtra("receiveruid");
        mReceiverName = getIntent().getStringExtra("name");
        senderRoom = mSenderUID + mReceiverUID;
        receiverRoom = mReceiverUID + mSenderUID;

        Log.e("Specific Chat","senderroom = "+senderRoom);
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("chats")
                .child(senderRoom)
                .child("messages");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageArrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Message message = snapshot1.getValue(Message.class);
                    messageArrayList.add(message);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        mBackButtonOfSpecificChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mNameOfSpecificUser.setText(mReceiverName);
        String uri = intent.getStringExtra("imageuri");

        if(uri.isEmpty()){
            Toast.makeText(getApplicationContext(), "Null is Received", Toast.LENGTH_SHORT).show();
        }
        else {
            Picasso.get().load(uri).into(mImageViewOfSpecificUser);
        }

        mSendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredMessage = mGetMessage.getText().toString();
                if(enteredMessage.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Message First", Toast.LENGTH_SHORT).show();
                }
                else {
                    Date date = new Date();
                    currentTime = simpleDateFormat.format(calendar.getTime());
                    Message message = new Message(enteredMessage,firebaseAuth.getUid(),date.getTime(),currentTime);
                    firebaseDatabase.getReference()
                            .child("chats")
                            .child(senderRoom)
                            .child("messages")
                            .push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            firebaseDatabase.getReference()
                                    .child("chats")
                                    .child(receiverRoom)
                                    .child("messages")
                                    .push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.e("Chat","message sent");
                                }
                            });

                        }
                    });

                    mGetMessage.setText(null);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        messagesAdapter.notifyDataSetChanged();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Now User is Offline", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if(messagesAdapter != null){
            messagesAdapter.notifyDataSetChanged();
        }
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status","Offline").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Now User is Offline", Toast.LENGTH_SHORT).show();
            }
        });
    }

}