package com.example.trello;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    private String email;
    private  String password;
    private Button signin;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;


    private void setuptoolbar(){
        toolbar=findViewById(R.id.toolbar_sign_in_activity);
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp);
        }
        toolbar.setNavigationOnClickListener(this::OnBack);

    }
    private void loginUser() {
        //Get text in form
       email=((EditText)findViewById(R.id.et_email)).getText().toString();
        password=((EditText)findViewById(R.id.et_password)).getText().toString();
        //Authentication with Firebase
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(SignInActivity.this, "User login successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, IntroActivity.class));
            } else {
                Toast.makeText(SignInActivity.this, "Login error"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void OnBack(View view) {
        Intent i= new Intent(this,IntroActivity.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setuptoolbar();
        signin =(Button) findViewById(R.id.btn_sign_in);
        signin.setOnClickListener(v -> loginUser());
    }
}