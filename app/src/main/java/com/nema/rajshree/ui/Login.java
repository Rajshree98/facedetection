package com.nema.rajshree.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
EditText editTextphone,editTextcode;
FirebaseAuth mAuth;
String codeSent;
Button veri,otpveri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth=FirebaseAuth.getInstance();

        /*if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }*/

        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();
        editTextphone=(EditText)findViewById(R.id.rmobile);
        editTextcode=(EditText)findViewById(R.id.otp);
        veri= (Button)findViewById(R.id.verify);
        otpveri=(Button)findViewById(R.id.otpsubmit);

       veri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });

        otpveri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode();
            }
        });

    }


    private  void verifyCode(){
        String code=editTextcode.getText().toString();
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(codeSent,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Login.this, "Incorrect Verification Code", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });
    }



    private void sendVerificationCode(){

        String phone =editTextphone.getText().toString();

        if(phone.isEmpty()){
            editTextphone.setError("Phone number is required");
            editTextphone.requestFocus();
            return;
        }
        if(phone.length() <10){
            editTextphone.setError("Please enter a valid phone");
            editTextphone.requestFocus();
            return;
        }
       // if(phone==)

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent=s;
        }
    };




}
