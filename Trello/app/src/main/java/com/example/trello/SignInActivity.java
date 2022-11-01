package com.example.trello;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    private String email;
    private String password;
    private Button signin;
    private EditText txtEmail;
    private EditText txtPwd;
    //Firebase init
    private FirebaseAuth mAuth;
    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bindingView();
        bindingAction();
        initFirebase();
        setuptoolbar();
    }

    private void bindingAction() {
        signin.setOnClickListener(this::onSigninClick);
    }

    private void onSigninClick(View view) {
        //Get text in form
        email = txtEmail.getText().toString();
        password = txtPwd.getText().toString();
        //validate form
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_SHORT).show();
        }else{
            //Authentication with Firebase
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(SignInActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                                showMainActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void showMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void bindingView() {
        toolbar = findViewById(R.id.toolbar_sign_in_activity);
        txtEmail = findViewById(R.id.et_email);
        txtPwd = findViewById(R.id.et_password);
        signin = findViewById(R.id.btn_sign_in);
    }

    private void initFirebase() {
        //Init firebase auth
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
           showMainActivity();
        }
    }

    private void setuptoolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp);
        }
        toolbar.setNavigationOnClickListener(this::OnBack);

    }


    private void OnBack(View view) {
        Intent i = new Intent(this, IntroActivity.class);
        startActivity(i);
    }

}