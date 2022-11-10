package com.example.trello;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.adapters.MembersListItemAdapter;
import com.example.trello.model.Board;
import com.example.trello.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemberActivity extends AppCompatActivity {
    private Board boardDetail;
    private Toolbar toolbar;
    private MembersListItemAdapter membersListItemAdapter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerViewMemberList;
    private Dialog dialog;
    private List<User> assignedMemberList;
    private Boolean anyChange = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        boardDetail = getIntent().getParcelableExtra("boardDetails");
        firebaseFirestore = FirebaseFirestore.getInstance();
        membersListItemAdapter = new MembersListItemAdapter(this);
        bindingView();
        setupActionBar();
        if (boardDetail != null) {
            getAssignedMemberListDetail(boardDetail.getAssignedto());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.membermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_member:
                showDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_search_member);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttribute = window.getAttributes();
            windowAttribute.gravity = Gravity.CENTER;
            window.setAttributes(windowAttribute);
            TextView add = dialog.findViewById(R.id.tv_add);
            TextView cancel = dialog.findViewById(R.id.tv_cancel);
            EditText email = dialog.findViewById(R.id.et_email_search_member);
            cancel.setOnClickListener(v -> dialog.dismiss());
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (email.getText().toString().isEmpty()){
                        Toast.makeText(MemberActivity.this, "Please enter members email address", Toast.LENGTH_SHORT).show();
                    }else{
                        getMemberDetailsActivity(email.getText().toString());
                    }
                }
            });
        }

        dialog.show();
    }

    private void assignedMembertoBoard(Board board, User user) {
        var assignedToHashMap = new HashMap<String, Object>();
        assignedToHashMap.put("assignedto",board.getAssignedto());
        firebaseFirestore.collection("boards").document(board.getDocumentId()).update(assignedToHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                assignedMemberList.add(user);
                anyChange = true;
                setupMemberList(assignedMemberList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Error","Cannot add member");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (anyChange){
            setResult(Activity.RESULT_OK);
        }
        super.onBackPressed();
    }

    private void getMemberDetailsActivity(String email){
        firebaseFirestore.collection("Users").whereEqualTo("email", email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.size() > 0){
                    User user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                    boardDetail.getAssignedto().add(user.getId());
                    assignedMembertoBoard(boardDetail,user);
                }else{
                    Toast.makeText(MemberActivity.this, "No user found !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getAssignedMemberListDetail(List<String> assignedTo) {
        firebaseFirestore.collection("Users").whereIn("id", assignedTo).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<User> users = new ArrayList<>();
                for (var i : queryDocumentSnapshots.getDocuments()) {
                    User user = i.toObject(User.class);
                    users.add(user);
                }
                setupMemberList(users);
            }
        });
    }

    private void setupMemberList(List<User> users) {
        assignedMemberList = users;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewMemberList.setLayoutManager(linearLayoutManager);
        recyclerViewMemberList.setHasFixedSize(true);
        membersListItemAdapter.setData(users);
        recyclerViewMemberList.setAdapter(membersListItemAdapter);
    }

    private void bindingView() {
        toolbar = findViewById(R.id.toolbar_members_activity);
        recyclerViewMemberList = findViewById(R.id.rv_members_list);
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp);
            actionBar.setTitle("Members");
            //actionBar.setTitle(((Card)((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().get(this.mCardPosition)).getName());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemberActivity.this, MainActivity.class));
            }
        });
    }

}
