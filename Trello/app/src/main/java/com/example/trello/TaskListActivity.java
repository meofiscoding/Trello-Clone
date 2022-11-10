package com.example.trello;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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

import androidx.appcompat.app.ActionBar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<String, ArrayList<Card>> cards;


    private RecyclerView rv_card_list;


    public ArrayList getmAssignedMembersDetailList() {
        return mAssignedMembersDetailList;
    }

    public static final int MEMBERS_REQUEST_CODE = 13;
    public static final int CARD_DETAILS_REQUEST_CODE = 14;

    private Toolbar toolbar;


    private void bindingAction() {



        rv_task_list.setLayoutManager( new
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_task_list.setHasFixedSize(true);
        if(tasks==null){
            tasks= new ArrayList<>();
            //tasks.add(new Task("Good Morning","Thinh","ABCDEF" ));
        }

            for(Task task: tasks){
                if(cards !=null){
                if(cards.containsKey(task.getTitle())) {
                    task.setCards(cards.get(task.getTitle()));
                }
            }
        }

//        rv_card_list.setLayoutManager( new
//                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        rv_card_list.setHasFixedSize(true);

        TaskListItemsAdapter adapter = new TaskListItemsAdapter(this, tasks);
        rv_task_list.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                recyclerView.getAdapter().notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                // Set movement flags to specify the movement
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        touchHelper.attachToRecyclerView(rv_task_list);
//        rv_card_list.setAdapter(cartAdapter);
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
        FirestoreClass firestoreClass= new FirestoreClass();
        firestoreClass.getBoardDetails(this,mBoardDocumentId);
//        firestoreClass.getTaskDetails(this,mBoardDocumentId);
        getData();
       // getListItems(mBoardDocumentId);

    }
    public void getData(){
        FirestoreClass firestoreClass= new FirestoreClass();
        firestoreClass.getBoardDetails(this,mBoardDocumentId);
        firestoreClass.getTaskDetails(this,mBoardDocumentId);
        //firestoreClass.getCardDetails(this,tasks);
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK&&(requestCode == MEMBERS_REQUEST_CODE || requestCode == CARD_DETAILS_REQUEST_CODE)){
            showProgressDialog("Please wait");
        }
    }
    public void taskDetails(ArrayList taskList){
        tasks = taskList;
        bindingView();
        bindingAction();
        setupActionBar();
    }

    public void carddetail(HashMap<String, ArrayList<Card>> cartgets){
         cards= cartgets;
        bindingView();
        bindingAction();
        setupActionBar();
    }

    public void boardDetails(Board board){
        mBoardDetails = board;
        setupActionBar();
    }

    public void createTaskList(String taskListName){
        mBoardDetails.setDocumentId(mBoardDocumentId);
        Task task = new Task(taskListName,FirestoreClass.getCurrentUserID(),mBoardDetails.getName());
        if(mBoardDetails.getTaskList()!=null){
            mBoardDetails.getTaskList().add(0,task);
        }else{
            ArrayList<Task> taskList = new ArrayList<>();
            mBoardDetails.setTaskList(taskList);
            mBoardDetails.getTaskList().add(task);
        }

        FirestoreClass firestoreClass= new FirestoreClass();
        firestoreClass.addUpdateTaskList(this, task);
        firestoreClass.updateTaskList(this,mBoardDetails);
        getData();
    }
    public void cardDetatils(int taskListPosition, int cardPosition){
        Intent intent = new Intent(this,CardDetailsActivity.class);
startActivity(intent);
    }

    public void updateTaskList(String taskListName){
        Task task = new Task(taskListName,FirestoreClass.getCurrentUserID(),mBoardDetails.getName());
        FirestoreClass firestoreClass= new FirestoreClass();
        firestoreClass.addUpdateTaskList(this, task);
        getData();
    }

    public void cardDetatils(String taskName, int cardPosition){
        Intent intent = new Intent(this,CardDetailsActivity.class);
        FirestoreClass firestoreClass= new FirestoreClass();
        //ArrayList<Card> cardArrayList = firestoreClass.getCardsByTaskname(taskName);
        //Card card = cardArrayList.get(cardPosition);
        //intent.putExtra("card",card);
        startActivity(intent);

    }

    public void addUpdateTaskListSuccess(){
        hideProgressDialog();
        showProgressDialog("Please Wait");
    }

    public void addCardToTaskList(int position, String cardName){

        //ArrayList<String> cardAssignedUsersList = new ArrayList<>();
       // cardAssignedUsersList.add(FirestoreClass.getCurrentUserID());
        Card card = new Card(cardName,tasks.get(position).getTitle());

        //showProgressDialog("Please Wait");

        FirestoreClass firestoreClass =new FirestoreClass();
        firestoreClass.addUpdateCard(this, card);
        getData();
    }

    public void deleteTaskList(String title){
        FirestoreClass firestoreClass = new FirestoreClass();
        firestoreClass.deleteTask(this,title);
        getData();
    }



}