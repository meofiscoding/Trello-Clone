package com.example.trello;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    //Firebase init
    private FirebaseAuth mAuth;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        btnSignOut.setOnClickListener(this::signOutClick);
    }

    private void signOutClick(View view) {
        mAuth.signOut();
        Intent i = new Intent(this, IntroActivity.class);
        startActivity(i);
    }

    private void bindingView() {
        btnSignOut = findViewById(R.id.btnSignOut);
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(this, SplachActivity.class));
        }else{
            Toast.makeText(this,"Welcome "+currentUser.getEmail() , Toast.LENGTH_SHORT).show();
        }
    }
    // [END on_start_check_user]
}