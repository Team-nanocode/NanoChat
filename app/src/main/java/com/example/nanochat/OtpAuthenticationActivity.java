package com.example.nanochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpAuthenticationActivity extends AppCompatActivity {

    TextView changeNumber;
    EditText getOTP;
    android.widget.Button verifyOTP;
    String enteredOTP;

    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_authentication);

        changeNumber = findViewById(R.id.changenumber);
        verifyOTP = findViewById(R.id.verifyotp);
        getOTP = findViewById(R.id.getotp);
        progressBar = findViewById(R.id.progressbarofotpauth);

        firebaseAuth = FirebaseAuth.getInstance();

        changeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtpAuthenticationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enteredOTP = getOTP.getText().toString();
                String codeRecieved= getIntent().getStringExtra("otp");

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeRecieved,enteredOTP);
                signInWithPhoneAuthCredentials(credential);


            }
        });



    }

    private void signInWithPhoneAuthCredentials(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Login Succeed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OtpAuthenticationActivity.this, ChatActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }

            }
        });

    }
}