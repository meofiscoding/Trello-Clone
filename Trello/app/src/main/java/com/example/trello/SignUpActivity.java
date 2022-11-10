package com.example.trello;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.trello.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnSignUp;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private String userId;
    private FirebaseFirestore firebaseFirestore;
    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private ProgressDialog progressDialog;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bindingView();
        bindingAction();
        setuptoolbar();
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        // [END initialize_auth]
    }

    private void bindingAction() {
        btnSignUp.setOnClickListener(this::onSignUpClick);
    }

    private void onSignUpClick(View view) {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String name = edtName.getText().toString();

        if (validateField(email, password, name)) {
            progressDialog.show();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(edtName.getText().toString()).build();
            mAuth.getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("CHANGE NAME", "okeoke");
                    }
                }
            });
            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email.trim(), password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                InputMethodManager imm = (InputMethodManager) SignUpActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                Toast.makeText(SignUpActivity.this, "Signup successfully!! ", Toast.LENGTH_SHORT).show();
                                // Sign in success, update UI with the signed-in user's information
                                userId = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);
                                User user = new User(userId, edtName.getText().toString(), edtEmail.getText().toString(), "", "");
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.w(TAG, "createUserWithEmail:success", task.getException());
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed due to " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            // [END create_user_with_email]
        }
    }

    public ActionCodeSettings buildActionCodeSettings() {
        // [START auth_build_action_code_settings]
        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://trelloclone-e6a10.firebaseapp.com/")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("com.example.ios")
                        .setAndroidPackageName(
                                "com.example.trello",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();
        // [END auth_build_action_code_settings]
        return actionCodeSettings;
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
        // [END send_email_verification]
    }

    private boolean validateField(String name, String password, String email) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.ValidateForm, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void bindingView() {
        toolbar = findViewById(R.id.toolbar_sign_up_activity);
        btnSignUp = findViewById(R.id.btn_sign_up);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPwd);
        progressDialog = new ProgressDialog(this);
    }
}