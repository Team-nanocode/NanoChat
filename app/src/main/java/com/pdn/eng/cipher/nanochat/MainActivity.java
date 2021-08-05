package com.pdn.eng.cipher.nanochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText mGetPhoneNumber;
    android.widget.Button mSendOtp;
    CountryCodePicker mCountryCodePicker;
    String countryCode;
    String phoneNumber;

    FirebaseAuth firebaseAuth;
    ProgressBar mProgressBarOfMain;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    String codeSent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCountryCodePicker = findViewById(R.id.countrycodepicker);
        mSendOtp = findViewById(R.id.sendotpbutton);
        mGetPhoneNumber = findViewById(R.id.getphonenumber);
        mProgressBarOfMain = findViewById(R.id.progressbarofmain);

        firebaseAuth = FirebaseAuth.getInstance();

        countryCode = mCountryCodePicker.getSelectedCountryCodeWithPlus();

        mCountryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = mCountryCodePicker.getSelectedCountryCodeWithPlus();
            }
        });

        mSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number;
                number = mGetPhoneNumber.getText().toString();
                if(number.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter Your Number",Toast.LENGTH_SHORT).show();
                }
                else if(number.length() != 9){
                    Toast.makeText(getApplicationContext(),"Please Enter Correct Number",Toast.LENGTH_SHORT).show();
                }
                else{
                    mProgressBarOfMain.setVisibility(View.VISIBLE);
                    phoneNumber = countryCode+number;
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(phoneNumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(MainActivity.this)
                            .setCallbacks(mCallBacks)
                            .build();

                    Log.e("MainActivity","phone = "+phoneNumber);
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //TODO:how to automatically fetch code here
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(getApplicationContext(),"OTP is Sent",Toast.LENGTH_SHORT).show();
                mProgressBarOfMain.setVisibility(View.INVISIBLE);
                codeSent = s;
                Intent intent = new Intent(MainActivity.this,OtpAuthenticationActivity.class);
                intent.putExtra("otp",codeSent);
                startActivity(intent);
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent = new Intent(MainActivity.this,ChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}