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
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.trello.model.User;
import com.example.trello.ui.gallery.GalleryFragment;
import com.example.trello.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Firebase init
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_MYPROFILE = 1;
    private static final int FRAGMENT_SETTING = 2;
    private int mCurrentFragment = FRAGMENT_HOME;
    private FirebaseAuth mAuth;
    public static final int REQUEST_CODE = 101;
    private Animation rotateOpen, rotateClose, fromBottom, toBottom;
    private boolean isFabClicked;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FloatingActionButton fabCreate, fabCreateCard, fabCreateBoard;
    private ImageView imgAvatar;
    private TextView txtUserName;
    private TextView txtEmail;
    private User user;
    private ActionBarDrawerToggle toggle;
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
                    galleryFragment.setmUri(uri);
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
        setContentView(R.layout.activity_main);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        bindingView();
        setSupportActionBar(toolbar);
        bindingAction();
        //load animation
        loadAnimation();
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_drawer_navigation, R.string.close_drawer_navigation);
        toggle.syncState();
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        user = getIntent().getParcelableExtra("user");
        showUserInfo();
    }

    private void bindingAction() {
        fabCreate.setOnClickListener(this::onFABCreateClick);
        fabCreateBoard.setOnClickListener(this::onFABCreateBoardClick);
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
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

    public void showUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.getTenantId();
            if (user.getDisplayName()==null) {
                txtUserName.setVisibility(View.GONE);
            } else {
                txtUserName.setVisibility(View.VISIBLE);
                txtUserName.setText(user.getDisplayName());
            }
            txtEmail.setText(user.getEmail());
            Glide.with(this).load(user.getPhotoUrl()).centerCrop().error(R.drawable.ic_baseline_supervised_user_circle_24).into(imgAvatar);
        }
    }

    private void bindingView() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        fabCreate = findViewById(R.id.fab_create);
        fabCreateBoard = findViewById(R.id.fab_create_board);
        fabCreateCard = findViewById(R.id.fab_create_card);
        imgAvatar = navigationView.getHeaderView(0).findViewById(R.id.imgAvatar);
        txtUserName = navigationView.getHeaderView(0).findViewById(R.id.txtUserName);
        txtEmail = navigationView.getHeaderView(0).findViewById(R.id.txtEmail);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        }
        if (id == R.id.nav_setting) {
            if (mCurrentFragment != FRAGMENT_SETTING) {
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_SETTING;
            }
        }
        if (id == R.id.nav_profile) {
            if (mCurrentFragment != FRAGMENT_MYPROFILE) {
                replaceFragment(galleryFragment);
                mCurrentFragment = FRAGMENT_MYPROFILE;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}