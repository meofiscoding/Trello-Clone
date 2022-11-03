package com.example.trello;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.adapters.BoardItemsAdapter;
import com.example.trello.databinding.ActivityMainBinding;
import com.example.trello.model.Board;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{
    //Firebase init
    private FirebaseAuth mAuth;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Animation rotateOpen, rotateClose, fromBottom, toBottom;
    private boolean isFabClicked;
    private RecyclerView rv_boards_list;
    private TextView tv_no_boards_available;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //load animation
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        setSupportActionBar(binding.appBarMain.toolbar);
        //set onclick to FAB Create Board
        binding.appBarMain.fabCreateBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateBoardActivity.class));
            }
        });
        //set onClick to fab Create
        binding.appBarMain.fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFabClicked = !isFabClicked;
                setVisibilityOfFabs(isFabClicked);
                setAnimationOfFabs(isFabClicked);
                setClickableOfFabs(isFabClicked);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void setClickableOfFabs(boolean isFabClicked) {
        if (isFabClicked){
            binding.appBarMain.fabCreateBoard.setClickable(true);
            binding.appBarMain.fabCreateCard.setClickable(true);
        }else{
            binding.appBarMain.fabCreateBoard.setClickable(false);
            binding.appBarMain.fabCreateCard.setClickable(false );
        }
    }

    private void setAnimationOfFabs(boolean isFabClicked) {
        if (isFabClicked) {
            binding.appBarMain.fabCreate.startAnimation(rotateOpen);
            binding.appBarMain.fabCreateBoard.startAnimation(fromBottom);
            binding.appBarMain.fabCreateCard.startAnimation(fromBottom);
        } else {
            binding.appBarMain.fabCreate.startAnimation(rotateClose);
            binding.appBarMain.fabCreateBoard.startAnimation(toBottom);
            binding.appBarMain.fabCreateCard.startAnimation(toBottom);
        }
    }

    private void setVisibilityOfFabs(boolean isFabClicked) {
        if (isFabClicked) {
            binding.appBarMain.fabCreateBoard.setVisibility(View.VISIBLE);
            binding.appBarMain.fabCreateCard.setVisibility(View.VISIBLE);
        } else {
            binding.appBarMain.fabCreateBoard.setVisibility(View.INVISIBLE);
            binding.appBarMain.fabCreateCard.setVisibility(View.INVISIBLE);
        }
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser == null) {
//            startActivity(new Intent(this, SplachActivity.class));
//        } else {
//            Toast.makeText(this, "Welcome " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
//        }
    }
    // [END on_start_check_user]

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}