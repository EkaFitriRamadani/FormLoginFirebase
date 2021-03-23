package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Button button;
    private EditText emailid;
    private String email;
    private TextView menulogin;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mFirebaseAuth = FirebaseAuth.getInstance();

        emailid = findViewById(R.id.email);
        button = findViewById(R.id.button);
        menulogin = findViewById(R.id.backToLogin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
        menulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoLogin = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intoLogin);
            }
        });
    }
    private void validateData() {
        email = emailid.getText().toString();
        if (email.isEmpty()) {
            emailid.setError("Required");
        }
        else {
            forgetPass();
        }
    }
    private void forgetPass() {
        mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPasswordActivity.this, "Check Your Email!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(ForgetPasswordActivity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}