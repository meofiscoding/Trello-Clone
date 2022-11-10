package com.example.trello;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce;
    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showProgressDialog(String text){
        TextView tv_progress_text= findViewById(R.id.tv_progress_text);
        mProgressDialog = new Dialog(this);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        tv_progress_text.setText(text);
        mProgressDialog.show();
    }

    public void hideProgressDialog(){
        mProgressDialog.dismiss();
    }

    public String getCurrentUserID(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void doubleBackToExit(){
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Please click back again to exit",
                Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                BaseActivity.this.doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
//FIXME
//    public void showErrorSnackBar(String message){
//        Snackbar snackbar = Snackbar.make(findViewById(R.id.content),message,Snackbar.LENGTH_LONG);
//        View snackbarView = snackbar.getView();
//        snackbarView.setBackgroundColor(ContextCompat.getColor(this,R.color.snackbar_error_color));
//        snackbar.show();
//    }
}