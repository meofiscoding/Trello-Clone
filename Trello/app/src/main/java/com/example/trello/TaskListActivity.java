package com.example.trello;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.trello.adapters.MyBoardViewHolder;
import com.example.trello.adapters.TaskListItemsAdapter;
import com.example.trello.firebase.FirestoreClass;
import com.example.trello.model.Board;
import com.example.trello.model.Card;
import com.example.trello.model.Task;
import com.example.trello.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class TaskListActivity extends BaseActivity {
    private Board mBoardDetails;
    private String mBoardDocumentId;
    ArrayList mAssignedMembersDetailList;
    private RecyclerView rv_task_list;
    private String boardname;
    private Query boardQuery;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirestoreRecyclerAdapter<Task, TaskListItemsAdapter.TaskViewHolder> adapter;
    private ArrayList<Task> tasks;


    public ArrayList getmAssignedMembersDetailList() {
        return mAssignedMembersDetailList;
    }

    public static final int MEMBERS_REQUEST_CODE = 13;
    public static final int CARD_DETAILS_REQUEST_CODE = 14;

    private Toolbar toolbar;

//    private void getListItems(String name) {
//        db.collection("tasks").whereEqualTo("boardname",name).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot documentSnapshots) {
//                        if (documentSnapshots.isEmpty()) {
//                            Log.d(TAG, "onSuccess: LIST EMPTY");
//                            return;
//                        } else {
//                            // Convert the whole Query Snapshot to a list
//                            // of objects directly! No need to fetch each
//                            // document.
//                            List<Task> types = documentSnapshots.toObjects(Task.class);
//
//                            // Add all to your list
//                            tasks.addAll(types);
//                            Log.d(TAG, "onSuccess: ");
//                        }
//                    }
//                });
//    }

    private void bindingAction() {


//        Task addTaskList =new Task("TO DO1","Thinh");
//        Task addTaskList2 =new Task("TO DO2","Thinh");
//        taskList.add(addTaskList);
//        taskList.add(addTaskList2);

        rv_task_list.setLayoutManager( new
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_task_list.setHasFixedSize(true);
        if(tasks==null){
            tasks= new ArrayList<>();
            //tasks.add(new Task("Good Morning","Thinh","ABCDEF" ));
        }

        // Create an instance of TaskListItemsAdapter and pass the task list to it.
        TaskListItemsAdapter adapter = new TaskListItemsAdapter(this, tasks);
        rv_task_list.setAdapter(adapter);
    }

    private void bindingView() {
        toolbar = findViewById(R.id.toolbar_task_list_activity);
        rv_task_list = findViewById(R.id.rv_task_list);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        if(this.getIntent().hasExtra(Constants.DOCUMENT_ID)){
            mBoardDocumentId = this.getIntent().getStringExtra(Constants.DOCUMENT_ID);

        }
//        FirestoreClass firestoreClass= new FirestoreClass();
//        firestoreClass.getBoardDetails(this,mBoardDocumentId);
//        firestoreClass.getTaskDetails(this,mBoardDocumentId);
        getData();
       // getListItems(mBoardDocumentId);

    }
    public void getData(){
        FirestoreClass firestoreClass= new FirestoreClass();
        firestoreClass.getBoardDetails(this,mBoardDocumentId);
        firestoreClass.getTaskDetails(this,mBoardDocumentId);
    }
    @Override
    protected void onStart() {
        super.onStart();
        bindingView();
        bindingAction();
        setupActionBar();
    }

    private  void setupActionBar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp);
            actionBar.setTitle(mBoardDocumentId);
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
    public void taskDetails(ArrayList taskList){
        tasks = taskList;
        bindingView();
        bindingAction();
        setupActionBar();
        //hideProgressDialog();
        //setupActionBar();
        // showProgressDialog("Please Wait");
        //FIXME
        //FirestoreClass().getAssignedMembersListDetails(this,mBoardDetails.getAssignedto());
    }

    public void boardDetails(Board board){
        mBoardDetails = board;
        //hideProgressDialog();
        setupActionBar();
       // showProgressDialog("Please Wait");
        //FIXME
        //FirestoreClass().getAssignedMembersListDetails(this,mBoardDetails.getAssignedto());
    }

    public void createTaskList(String taskListName){
        Task task = new Task(taskListName,FirestoreClass.getCurrentUserID(),mBoardDetails.getName());
//        if(mBoardDetails.getTaskList()==null){
//            mBoardDetails.setTaskList(new ArrayList<Task>());
//        }
//        mBoardDetails.getTaskList().add(task.toString());
       // mBoardDetails.getTaskList().remove(mBoardDetails.getTaskList().size()-1);
        //showProgressDialog("Please Wait");
        //FIXME
        FirestoreClass firestoreClass= new FirestoreClass();
        firestoreClass.addUpdateTaskList(this, task);
        getData();
    }

//    public void updateTaskList(int position,String listName,Task model){
//        Task task = new Task(listName,model.getCreatedBy());
//        //FIXME
//        mBoardDetails.getTaskList().set(position,task);
//        mBoardDetails.getTaskList().remove(mBoardDetails.getTaskList().size()-1);
//        showProgressDialog("Please Wait");
//        //FIXME
//        //FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
//    }

//    public void deleteTaskList(int position){
//        mBoardDetails.getTaskList().remove(position);
//        mBoardDetails.getTaskList().remove(mBoardDetails.getTaskList().size()-1);
//        showProgressDialog("Please Wait");
//        //FIXME
//        //FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
//    }

    public void addUpdateTaskListSuccess(){
        hideProgressDialog();
        showProgressDialog("Please Wait");
        //FIXME
        //FirestoreClass().getBoardDetails(this@TaskListActivity, mBoardDetails.documentId)
    }

//    public void addCardToTaskList(int position, String cardName){
//        mBoardDetails.getTaskList().remove(mBoardDetails.getTaskList().size()-1);
//        ArrayList<String> cardAssignedUsersList = new ArrayList<>();
//        cardAssignedUsersList.add(FirestoreClass.getCurrentUserID());
//        Card card = new Card(cardName,FirestoreClass.getCurrentUserID(),cardAssignedUsersList);
//        ArrayList<Card> cardsList = ((Task)mBoardDetails.getTaskList().get(position)).getCards();
//        cardsList.add(card);
//        Task task = new Task(((Task) mBoardDetails.getTaskList().get(position)).getTitle(),
//                ((Task) mBoardDetails.getTaskList().get(position)).getCreatedBy(),
//                cardsList);
//        mBoardDetails.getTaskList().set(position,task);
//        showProgressDialog("Please Wait");
//        //FIXME
//        //FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
//    }
//FIXME
//    public void cardDetatils(int taskListPosition, int cardPosition){
//        Intent intent = new Intent(this,Cards)
//    }

//    public void boardMembersDetailList(ArrayList<User> list){
//        mAssignedMembersDetailList = list;
//        hideProgressDialog();
//
//        Task addTaskList = new Task("Add List");
//        mBoardDetails.getTaskList().add(addTaskList);
//        rv_task_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        rv_task_list.setHasFixedSize(true);
//        //FIXME
//        //TaskListItemsAdapter adapter = new TaskListItemsAdapter(this, mBoardDetails.getTaskList());
//        //rv_task_list.setAdapter(adapter);
//    }

//    public void updateCardsInTaskList(int taskListPosition,ArrayList<Card> cards){
//        mBoardDetails.getTaskList().remove(mBoardDetails.getTaskList().size()-1);
//        ((Task)mBoardDetails.getTaskList().get(taskListPosition)).setCards(cards);
//        showProgressDialog("Please Wait");
//        //FIXME
//        //FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails);
//    }


}