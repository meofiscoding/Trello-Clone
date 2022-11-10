package com.example.trello;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.trello.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {
    private String email;
    private String password;
    private Button signin;
    private EditText edtEmail;
    private EditText edtPwd;
    private TextView txtForgotPwd;
    private FirebaseFirestore firebaseFirestore;
    //Firebase init
    private FirebaseAuth mAuth;
    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bindingView();
        bindingAction();
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        setuptoolbar();
    }

    private void bindingAction() {
        signin.setOnClickListener(this::onSigninClick);
        txtForgotPwd.setOnClickListener(this::onForgotPwdClick);
    }

    private void onForgotPwdClick(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    private void onSigninClick(View view) {
//        Get text in form
        email = edtEmail.getText().toString();
        password = edtPwd.getText().toString();
        //validate form
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.ValidateForm, Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(SignInActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                                firebaseFirestore.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        var loginUser = documentSnapshot.toObject(User.class);
                                        if (loginUser != null) {
                                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                            intent.putExtra("user",loginUser);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void bindingView() {
        toolbar = findViewById(R.id.toolbar_sign_in_activity);
        edtEmail = findViewById(R.id.edtEmail);
        edtPwd = findViewById(R.id.edtPwd);
        signin = findViewById(R.id.btn_sign_in);
        txtForgotPwd = findViewById(R.id.txtForgotPwd);
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