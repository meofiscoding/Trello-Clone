package com.example.trello.ui.gallery;

import static com.example.trello.MainActivity.REQUEST_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.trello.MainActivity;
import com.example.trello.R;
import com.example.trello.databinding.FragmentGalleryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private ImageView imgAvatar;
    private EditText edtName, edtEmail;
    private Button btnUpdate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bindingView();
        bindingAction();
        setUserInfo();
        return root;
    }

    private void setUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            edtName.setText(user.getDisplayName());
            edtEmail.setText(user.getEmail());
            Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_baseline_supervised_user_circle_24).into(imgAvatar);
        }
    }

    private void bindingAction() {
        imgAvatar.setOnClickListener(this::onClickAvatar);
    }

    private void onClickAvatar(View view) {
        onClickRequestPermission();
    }

    private void onClickRequestPermission() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mainActivity.openGallery();
            return;
        }
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mainActivity.openGallery();
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permissions, REQUEST_CODE);
        }
    }

    private void bindingView() {
        imgAvatar = binding.ivProfileUserImage;
        edtEmail = binding.edtEmail;
        edtName = binding.edtName;
        btnUpdate = binding.btnUpdate;
    }

    public void setBitmapImageView(Bitmap bitmap){
        if (imgAvatar == null){
            imgAvatar = binding.ivProfileUserImage;
        }
        imgAvatar.setImageBitmap(bitmap);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}