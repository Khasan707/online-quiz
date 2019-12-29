package com.khasan.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.khasan.quiz.R;

public class MainActivity
        extends AppCompatActivity
        implements
        View.OnClickListener,
        FirebaseAuth.AuthStateListener {

    private EditText etEmail;
    private EditText etPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        findViewById(R.id.btn_login).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (!task.isSuccessful()) {
                                signUp();
                            }
                        });
            } else {
                Toast.makeText(
                        this,
                        R.string.signing_in_failed,
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, SelectTestActivity.class));
            finish();
        }
    }

    private void signUp() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(
                                this,
                                R.string.signing_in_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
