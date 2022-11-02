package com.example.trello;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trello.model.User;

public class MyProfileActivity extends AppCompatActivity {
    private User user= new User("1","Thinh","thinh123@thinh","image","0987556601");

    private ImageView image;
    private EditText name;
    private EditText moblie;

    private void bindingView(){
        SharedPreferences pref = getSharedPreferences("MyPref",  MODE_PRIVATE);
        image=findViewById(R.id.iv_profile_user_image);
        name=findViewById(R.id.edtName);
        moblie=findViewById(R.id.et_mobile);
    }
    private void bindingAction(){
        image.setImageResource(R.drawable.ic_launcher_foreground);
        name.setText(user.getName());
        moblie.setText(user.getMobile());

    }

    @Override
    protected void onStart() {
        super.onStart();
        bindingView();
        bindingAction();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
    }
}