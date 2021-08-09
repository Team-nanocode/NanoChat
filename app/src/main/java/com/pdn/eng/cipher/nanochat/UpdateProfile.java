package com.pdn.eng.cipher.nanochat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {

    EditText mNewUserName;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    FirebaseFirestore firebaseFirestore;

    ImageView mGetNewImageInImageView;

    StorageReference storageReference;

    FirebaseStorage firebaseStorage;

    private String imageURIAccessToken;

    androidx.appcompat.widget.Toolbar mToolBarOfUpdateProfile;

    ImageButton mBackButtonOfUpdateProfile;

    ProgressBar mProgressBarOfUpdateProfile;

    private Uri imagePath;

    Intent intent;

    android.widget.Button mUpdateProfileButton;

    private final static int PICK_IMAGE = 123;

    String newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mToolBarOfUpdateProfile = findViewById(R.id.toolbarofupdateprofile);
        mBackButtonOfUpdateProfile = findViewById(R.id.backbuttonofupdateprofile);
        mGetNewImageInImageView = findViewById(R.id.getnewuserimageinimageview);
        mProgressBarOfUpdateProfile = findViewById(R.id.progressbarofupdateprofile);
        mNewUserName = findViewById(R.id.getnewusername);
        mUpdateProfileButton = findViewById(R.id.updateprofilebutton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        intent = getIntent();

        setSupportActionBar(mToolBarOfUpdateProfile);

        mBackButtonOfUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mNewUserName.setText(intent.getStringExtra("nameOfUser"));

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        mUpdateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newName = mNewUserName.getText().toString();
                if(newName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Name is Empty", Toast.LENGTH_SHORT).show();
                }
                else if(imagePath != null){
                    mProgressBarOfUpdateProfile.setVisibility(View.VISIBLE);
                    UserProfile mUserProfile = new UserProfile(newName,firebaseAuth.getUid());
                    databaseReference.setValue(mUserProfile);

                    updateImageToStorage();

                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    mProgressBarOfUpdateProfile.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(UpdateProfile.this,ChatActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    //only want to change name
                    mProgressBarOfUpdateProfile.setVisibility(View.VISIBLE);
                    UserProfile mUserProfile = new UserProfile(newName,firebaseAuth.getUid());
                    databaseReference.setValue(mUserProfile);

                    updateNameOnCloudFireStore();
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    mProgressBarOfUpdateProfile.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(UpdateProfile.this,ChatActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        mGetNewImageInImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICK_IMAGE);
            }
        });

        storageReference = firebaseStorage.getReference();
        storageReference.child("Images").child(firebaseAuth.getUid()).child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageURIAccessToken = uri.toString();
                Picasso.get().load(uri).into(mGetNewImageInImageView);
            }
        });

    }

    private void updateNameOnCloudFireStore() {
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        Map<String, Object> userData = new HashMap<>();
        userData.put("name",newName);
        userData.put("image",imageURIAccessToken);
        userData.put("uid",firebaseAuth.getUid());
        userData.put("status","Online");

        documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void updateImageToStorage() {
        StorageReference imageRef = storageReference.child("Images").child(firebaseAuth.getUid()).child("Profile Pic");

        //Image Compression
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,25,byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        //putting image to storage
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageURIAccessToken = uri.toString();
                        Toast.makeText(getApplicationContext(), "URI Get Success", Toast.LENGTH_SHORT).show();
                        updateNameOnCloudFireStore();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "URI Get Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(getApplicationContext(), "Image is Updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Images Not Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imagePath = data.getData();
            mGetNewImageInImageView.setImageURI(imagePath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status","Offline").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Now User is Offline", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Now User is Offline", Toast.LENGTH_SHORT).show();
            }
        });
    }
}