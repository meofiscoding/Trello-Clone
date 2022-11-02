package com.example.trello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText edtEmail;
    private Button btnResetPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        bindingView();
        bindingAction();
    }

    private void bindingView(){
        edtEmail = findViewById(R.id.edtReEmail);
        btnResetPwd = findViewById(R.id.btn_reset_pwd);
    }

    private void bindingAction() {
        btnResetPwd.setOnClickListener(this::sendPasswordReset);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void sendPasswordReset(View view) {
        // [START send_password_reset]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = edtEmail.getText().toString();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "Reset password email has sent to your email, please check it now!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END send_password_reset]
    }

}