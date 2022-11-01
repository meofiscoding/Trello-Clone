package com.example.trello;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {
   private Toolbar toolbar;

    private void setuptoolbar(){
     toolbar=findViewById(R.id.toolbar_sign_up_activity);
     setSupportActionBar(toolbar);
     ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp);
        }
        toolbar.setNavigationOnClickListener(this::OnBack);

    }

    private void OnBack(View view) {
        Intent i= new Intent(this,IntroActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setuptoolbar();
    }
}