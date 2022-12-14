package com.example.trello;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateBoardActivity extends BaseActivity {
    private Uri mSelectedImageFileUri;
    private String mUserName;
    private String mBoardImageURL = "";
    private Toolbar toolbar;
    private CircleImageView circleImageView;
    private Button btn_create;
    private EditText et_board_name;

    private void bindingView(){
        toolbar=findViewById(R.id.toolbar_create_board_activity);
        circleImageView = findViewById(R.id.iv_board_image);
        et_board_name = findViewById(R.id.et_board_name);
    }

    private void bindingAction(){
        circleImageView.setOnClickListener(this::onBoardImageClick);
        btn_create.setOnClickListener(this::onButtonCreateClick);
    }

    private void onButtonCreateClick(View view) {
        if(mSelectedImageFileUri!=null){
            uploadBoardImage();
        }else{
            showProgressDialog("Please wait");
            createBoard();
        }
    }

    private void onBoardImageClick(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            Constants.INSTANCE.showImageChooser(this);
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.READ_STORAGE_PERMISSION_CODE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_board);
        setupActionBar();

        if(this.getIntent().hasExtra("name")){
            mUserName = this.getIntent().getStringExtra("name");
        }

        bindingView();
        bindingAction();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.INSTANCE.showImageChooser(this);
            } else {
                Toast.makeText(this, "Oops, you just denied the permission for storage. You can also allow it from settings.", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
            if (data.getData() != null) {
                mSelectedImageFileUri = data.getData();
                ((RequestBuilder)((RequestBuilder) Glide.with((FragmentActivity)this).load(Uri.parse(String.valueOf(this.mSelectedImageFileUri))).centerCrop())
                        .placeholder(R.drawable.ic_user_place_holder)).into(circleImageView);
            }
        }

    }

    private final void setupActionBar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateBoardActivity.this.onBackPressed();
            }
        });
    }

    private final void uploadBoardImage(){
        /*showProgessDialog("Please Wait");*/
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("BOARD_IMAGE"+System.currentTimeMillis() + "."
                + Constants.INSTANCE.getFileExtension(this,mSelectedImageFileUri));
        storageReference.putFile(mSelectedImageFileUri).addOnSuccessListener(taskSnapshot->{
            Log.e("Firebase Image URL",taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(uri->{
                Log.e("Downloadable Image URL",uri.toString());
                mBoardImageURL = uri.toString();
                createBoard();
            });
        }).addOnFailureListener(exception->{
            Toast.makeText(this,exception.getMessage(),Toast.LENGTH_SHORT).show();
            hideProgressDialog();
        });
    }

    private void createBoard() {
        ArrayList assignedUsersArrayList = new ArrayList();
        assignedUsersArrayList.add(getCurrentUserID());
        Board board = new Board(et_board_name.getText().toString(),mBoardImageURL,mUserName,assignedUsersArrayList);
//        FirestoreClass().createBoard(this, board);
    }

    public final void boardCreatedSuccessfully() {
        hideProgressDialog();
        this.setResult(-1);
        this.finish();
    }


}