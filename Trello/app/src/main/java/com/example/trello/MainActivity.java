package com.example.trello;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trello.adapters.BoardItemsAdapter;
import com.example.trello.databinding.ActivityMainBinding;
import com.example.trello.model.Board;
import com.example.trello.ui.gallery.GalleryFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{
    //Firebase init
    private FirebaseAuth mAuth;
    public static final int REQUEST_CODE = 101;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Animation rotateOpen, rotateClose, fromBottom, toBottom;
    private boolean isFabClicked;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FloatingActionButton fabCreate, fabCreateCard, fabCreateBoard;
    private ImageView imgAvatar;
    private TextView txtUserName;
    private TextView txtEmail;
    GalleryFragment galleryFragment = new GalleryFragment();
    final private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                if (intent == null) {
                    return;
                } else {
                    Uri uri = intent.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        galleryFragment.setBitmapImageView(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });

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
        bindingView();
        bindingAction();
        //load animation
        loadAnimation();
        setSupportActionBar(toolbar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        showUserInfo();
    }

    private void bindingAction() {
        fabCreate.setOnClickListener(this::onFABCreateClick);
        fabCreateBoard.setOnClickListener(this::onFABCreateBoardClick);
    }

    private void onFABCreateBoardClick(View view) {
        startActivity(new Intent(MainActivity.this, CreateBoardActivity.class));
    }

    private void onFABCreateClick(View view) {
        isFabClicked = !isFabClicked;
        setVisibilityOfFabs(isFabClicked);
        setAnimationOfFabs(isFabClicked);
        setClickableOfFabs(isFabClicked);
    }

    private void loadAnimation() {
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
    }

    private void showUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.getTenantId();
            if (user.getDisplayName().isEmpty()) {
                txtUserName.setVisibility(View.GONE);
            } else {
                txtUserName.setVisibility(View.VISIBLE);
                txtUserName.setText(user.getDisplayName());
            }
            txtEmail.setText(user.getEmail());
            Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.ic_baseline_supervised_user_circle_24).into(imgAvatar);
        }
    }

    private void bindingView() {
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        toolbar = binding.appBarMain.toolbar;
        fabCreate = binding.appBarMain.fabCreate;
        fabCreateBoard = binding.appBarMain.fabCreateBoard;
        fabCreateCard = binding.appBarMain.fabCreateCard;
        imgAvatar = binding.navView.getHeaderView(0).findViewById(R.id.imgAvatar);
        txtUserName = binding.navView.getHeaderView(0).findViewById(R.id.txtUserName);
        txtEmail = binding.navView.getHeaderView(0).findViewById(R.id.txtEmail);
    }

    private void setClickableOfFabs(boolean isFabClicked) {
        if (isFabClicked) {
            fabCreateBoard.setClickable(true);
            fabCreateCard.setClickable(true);
        } else {
            fabCreateBoard.setClickable(false);
            fabCreateCard.setClickable(false);
        }
    }

    private void setAnimationOfFabs(boolean isFabClicked) {
        if (isFabClicked) {
            fabCreate.startAnimation(rotateOpen);
            fabCreateBoard.startAnimation(fromBottom);
            fabCreateCard.startAnimation(fromBottom);
        } else {
            fabCreate.startAnimation(rotateClose);
            fabCreateBoard.startAnimation(toBottom);
            fabCreateCard.startAnimation(toBottom);
        }
    }

    private void setVisibilityOfFabs(boolean isFabClicked) {
        if (isFabClicked) {
            fabCreateBoard.setVisibility(View.VISIBLE);
            fabCreateCard.setVisibility(View.VISIBLE);
        } else {
            fabCreateBoard.setVisibility(View.INVISIBLE);
            fabCreateCard.setVisibility(View.INVISIBLE);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                // If don't have permission so prompt the user.
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Gallery Access")
                            .setMessage("This permission is needed accept pls")
                            .setPositiveButton("OK", (DialogInterface dialogInterface, int i) -> {
                                this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
                                return;
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                }
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }
}