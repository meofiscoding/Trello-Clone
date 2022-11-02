package com.example.trello;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;

import com.example.trello.firebase.FirestoreClass;
import com.example.trello.model.Board;
import com.example.trello.model.Card;
import com.example.trello.model.Task;
import com.example.trello.model.User;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.databinding.ActivityTaskListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kotlin.jvm.internal.Intrinsics;


public class TaskListActivity extends BaseActivity {
    private Board mBoardDetails;
    private String mBoardDocumentId;
    ArrayList mAssignedMembersDetailList;
    private RecyclerView rv_task_list;

    public ArrayList getmAssignedMembersDetailList() {
        return mAssignedMembersDetailList;
    }

    public static final int MEMBERS_REQUEST_CODE = 13;
    public static final int CARD_DETAILS_REQUEST_CODE = 14;

    private Toolbar toolbar;

    private void bindingAction() {
    }

    private void bindingView() {
        toolbar = findViewById(R.id.toolbar_task_list_activity);
        rv_task_list = findViewById(R.id.rv_task_list);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        bindingView();
        bindingAction();
        if(this.getIntent().hasExtra(Constants.DOCUMENT_ID)){
            mBoardDocumentId = this.getIntent().getStringExtra(Constants.DOCUMENT_ID);
        }
        //showProgressDialog("Please Wait");
        //FIXME
        //FirestoreClass.getBoardDetails(this,mBoardDocumentId);
    }



    private final void setupActionBar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp);
            actionBar.setTitle(mBoardDetails.getName());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskListActivity.this.onBackPressed();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //FIXME
//        this.getMenuInflater().inflate(R.menu.menu_members,menu);
        return super.onCreateOptionsMenu(menu);
    }
//FIXME
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
//            case R.id.action_members:
//                Intent intent = new Intent((Context)this, MembersActivity.class);
//                intent.putExtra(Constants.BOARD_DETAIL, mBoardDetails);
//                startActivityForResult(intent, MEMBERS_REQUEST_CODE);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK&&(requestCode == MEMBERS_REQUEST_CODE || requestCode == CARD_DETAILS_REQUEST_CODE)){
            showProgressDialog("Please wait");
            //FIXME
            //FirestoreCLass.getBoardDeatils(this,mBoardDocumentId);
        }
    }

    public void boardDetails(Board board){
        mBoardDetails = board;
        hideProgressDialog();
        setupActionBar();
        showProgressDialog("Please Wait");
        //FIXME
        //FirestoreClass().getAssignedMembersListDetails(this,mBoardDetails.getAssignedto());
    }

    public void createTaskList(String taskListName){
        Task task = new Task(taskListName,FirestoreClass.getCurrentUserID());
        mBoardDetails.getTaskList().add(0,task);
        mBoardDetails.getTaskList().remove(mBoardDetails.getTaskList().size()-1);
        showProgressDialog("Please Wait");
        //FIXME
        //FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
    }

    public void updateTaskList(int position,String listName,Task model){
        Task task = new Task(listName,model.getCreatedBy());
        //FIXME
        mBoardDetails.getTaskList().set(position,task);
        mBoardDetails.getTaskList().remove(mBoardDetails.getTaskList().size()-1);
        showProgressDialog("Please Wait");
        //FIXME
        //FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
    }

    public void deleteTaskList(int position){
        mBoardDetails.getTaskList().remove(position);
        mBoardDetails.getTaskList().remove(mBoardDetails.getTaskList().size()-1);
        showProgressDialog("Please Wait");
        //FIXME
        //FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
    }

    public void addUpdateTaskListSuccess(){
        hideProgressDialog();
        showProgressDialog("Please Wait");
        //FIXME
        //FirestoreClass().getBoardDetails(this@TaskListActivity, mBoardDetails.documentId)
    }

    public void addCardToTaskList(int position, String cardName){
        mBoardDetails.getTaskList().remove(mBoardDetails.getTaskList().size()-1);
        ArrayList<String> cardAssignedUsersList = new ArrayList<>();
        cardAssignedUsersList.add(FirestoreClass.getCurrentUserID());
        Card card = new Card(cardName,FirestoreClass.getCurrentUserID(),cardAssignedUsersList);
        ArrayList<Card> cardsList = ((Task)mBoardDetails.getTaskList().get(position)).getCards();
        cardsList.add(card);
        Task task = new Task(((Task) mBoardDetails.getTaskList().get(position)).getTitle(),
                ((Task) mBoardDetails.getTaskList().get(position)).getCreatedBy(),
                cardsList);
        mBoardDetails.getTaskList().set(position,task);
        showProgressDialog("Please Wait");
        //FIXME
        //FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
    }
//FIXME
//    public void cardDetatils(int taskListPosition, int cardPosition){
//        Intent intent = new Intent(this,Cards)
//    }

    public void boardMembersDetailList(ArrayList<User> list){
        mAssignedMembersDetailList = list;
        hideProgressDialog();

        Task addTaskList = new Task("Add List");
        mBoardDetails.getTaskList().add(addTaskList);
        rv_task_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv_task_list.setHasFixedSize(true);
        //FIXME
        //TaskListItemsAdapter adapter = new TaskListItemsAdapter(this, mBoardDetails.getTaskList());
        //rv_task_list.setAdapter(adapter);
    }

    public void updateCardsInTaskList(int taskListPosition,ArrayList<Card> cards){
        mBoardDetails.getTaskList().remove(mBoardDetails.getTaskList().size()-1);
        ((Task)mBoardDetails.getTaskList().get(taskListPosition)).setCards(cards);
        showProgressDialog("Please Wait");
        //FIXME
        //FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails);
    }
}