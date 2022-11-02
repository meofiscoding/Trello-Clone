package com.example.trello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PasswordUpdated extends AppCompatActivity {

    private Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_updated);
        bindingView();
        bindingAction();
    }

    private void bindingView() {
        btnSignIn = findViewById(R.id.btn_resign_in);
    }

    private void bindingAction() {
        btnSignIn.setOnClickListener(this::onSignInClick);
    }

    private void onSignInClick(View view) {
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }
}