package com.pdn.eng.cipher.nanochat;

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

    TextView mChangeNumber;
    EditText mGetOtp;
    android.widget.Button mVerifyOtp;
    String enteredOtp;

    FirebaseAuth firebaseAuth;
    ProgressBar mProgressbarOfOtpAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_authentication);

        mChangeNumber = findViewById(R.id.changenumber);
        mVerifyOtp = findViewById(R.id.verifyotp);
        mGetOtp = findViewById(R.id.getotp);
        mProgressbarOfOtpAuth = findViewById(R.id.progressbarofotpauth);

        firebaseAuth = FirebaseAuth.getInstance();

        mChangeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtpAuthenticationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        mVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredOtp = mGetOtp.getText().toString();
                if(enteredOtp.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Your OTP First",Toast.LENGTH_SHORT).show();
                }
                else{
                    mProgressbarOfOtpAuth.setVisibility(View.VISIBLE);
                    String codeReceived = getIntent().getStringExtra("otp");
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeReceived,enteredOtp);
                    signInWithAuthCredential(credential);
                }
            }
        });
    }

    private void signInWithAuthCredential(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mProgressbarOfOtpAuth.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OtpAuthenticationActivity.this,SetProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        mProgressbarOfOtpAuth.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}