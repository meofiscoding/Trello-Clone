package com.example.trello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {
   private Button signin;
   private Button signup;

    private void bindingView(){
        SharedPreferences pref = getSharedPreferences("MyPref",  MODE_PRIVATE);
        signin=findViewById(R.id.btn_sign_in_intro);
        signup=findViewById(R.id.btn_sign_up_intro);
    }
    private void bindingAction(){
        signin.setOnClickListener(this::onButtonSignIn);
        signup.setOnClickListener(this::onButtonSignUp);
    }

    private void onButtonSignUp(View view) {
        Intent i= new Intent(this,SignUpActivity.class);
        startActivity(i);
    }

    private void onButtonSignIn(View view) {
        Intent i= new Intent(this,SignInActivity.class);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        bindingView();
        bindingAction();
    }
}