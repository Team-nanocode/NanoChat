package com.pdn.eng.cipher.nanochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    EditText mViewUserName;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    TextView mMoveToUpdateProfile;

    FirebaseFirestore firebaseFirestore;

    ImageView mViewUserImageInImageView;

    StorageReference storageReference;

    FirebaseStorage firebaseStorage;

    private String imageURIAccessToken;

    androidx.appcompat.widget.Toolbar mToolBarOfViewProfile;

    ImageButton mBackButtonOfViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mViewUserImageInImageView = findViewById(R.id.viewuserimageinimageview);
        mViewUserName = findViewById(R.id.viewusername);
        mMoveToUpdateProfile = findViewById(R.id.movetoupdateprofile);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mToolBarOfViewProfile = findViewById(R.id.toolbarofviewprofile);
        mBackButtonOfViewProfile = findViewById(R.id.backbuttonofviewprofile);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        setSupportActionBar(mToolBarOfViewProfile);

        mBackButtonOfViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        storageReference = firebaseStorage.getReference();
        storageReference.child("Images").child(firebaseAuth.getUid()).child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageURIAccessToken = uri.toString();
                Picasso.get().load(uri).into(mViewUserImageInImageView);
            }
        });

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile mUserProfile = snapshot.getValue(UserProfile.class);
                mViewUserName.setText(mUserProfile.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed To Fetch", Toast.LENGTH_SHORT).show();
            }
        });

        mMoveToUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,UpdateProfile.class);
                startActivity(intent);
            }
        });
        
    }
}