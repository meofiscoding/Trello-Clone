package com.example.trello;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trello.firebase.FirestoreClass;
import com.example.trello.model.Board;
import com.example.trello.model.Card;
import com.example.trello.model.SelectedMembers;
import com.example.trello.model.Task;
import com.example.trello.model.User;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

public class CardDetailsActivity extends BaseActivity {
    private Board mBoardDetails;
    private int mTaskListPosition = -1;
    private int mCardPosition = -1;
    private String mSelectedColor;
    private ArrayList mMembersDetailList;
    private long mSelectedDueDateMilliSeconds;
    private EditText et_name_card_details;
    private TextView tv_select_label_color;
    private TextView tv_select_members;
    private TextView tv_select_due_date;
    private Toolbar toolbar;
    private RecyclerView rv_selected_members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
    }

    private void bindingView() {
        et_name_card_details = findViewById(R.id.et_name_card_details);
        tv_select_label_color = findViewById(R.id.tv_select_label_color);
        tv_select_members = findViewById(R.id.tv_select_members);
        tv_select_due_date = findViewById(R.id.tv_select_due_date);
        toolbar = findViewById(R.id.toolbar_card_details_activity);
        rv_selected_members = findViewById(R.id.rv_selected_members_list);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        this.getMenuInflater().inflate(R.menu.menu_delete_card,menu);
        return super.onCreateOptionsMenu(menu);
    }

//    public boolean onOptionItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case R.id.action_delete_card:
//                alertDialogForDeleteCard(((Card)((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().get(this.mCardPosition)).getName());
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    private final void setupActionBar() {
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = this.getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp);
//            actionBar.setTitle(((Card)((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().get(this.mCardPosition)).getName());
//        }
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CardDetailsActivity.this.onBackPressed();
//            }
//        });//    }

    private final void getIntentData() {
        if (this.getIntent().hasExtra("task_list_item_position")) {
            this.mTaskListPosition = this.getIntent().getIntExtra("task_list_item_position", -1);
        }

        if (this.getIntent().hasExtra("card_list_item_position")) {
            this.mCardPosition = this.getIntent().getIntExtra("card_list_item_position", -1);
        }

        if (this.getIntent().hasExtra("board_detail")) {
            this.mBoardDetails = this.getIntent().getParcelableExtra("board_detail");
        }

        if (this.getIntent().hasExtra("board_members_list")) {
            this.mMembersDetailList = this.getIntent().getParcelableArrayListExtra("board_members_list");
        }
    }

    public void addUpdateTaskListSuccess(){
        hideProgressDialog();
        setResult(Activity.RESULT_OK);
        finish();
    }

//    private void updateCardDetails(){
//        Card card = new Card(et_name_card_details.getText().toString()
//        ,((Card)((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().get(this.mCardPosition)).getCreatedBy()
//        ,((Card)((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().get(this.mCardPosition)).getAssignedTo()
//        , mSelectedColor
//        ,mSelectedDueDateMilliSeconds);
//        ArrayList<Task> taskList = mBoardDetails.getTaskList();
//        taskList.remove(taskList.size()-1);
//        ((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().set(this.mCardPosition, card);
//        //showProgressDialog("Please Wait");
//        //FIXME
//        //FirestoreClass().addUpdateTaskList(this@CardDetailsActivity, mBoardDetails)
//    }

//    private final void deleteCard() {
//        ArrayList<Card> cardsList = ((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards();
//        cardsList.remove(mCardPosition);
//        ArrayList<Task> taskList = mBoardDetails.getTaskList();
//        taskList.remove(taskList.size()-1);
//        ((Task)taskList.get(this.mTaskListPosition)).setCards(cardsList);
//        //showProgressDialog("Please Wait");
//        //FIXME
//        //FirestoreClass.addUpdateTaskList(this@CardDetailsActivity, mBoardDetails)
//    }

//    private final void alertDialogForDeleteCard(String cardName) {
//        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
//        builder.setTitle(R.string.alert);
//        builder.setMessage("Are you sure you want to delete card "+ cardName+" ?");
//        builder.setIcon(R.drawable.ic_delete);
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                deleteCard();
//            }
//        });
//        builder.setPositiveButton("No", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.setCancelable(false);
//        alertDialog.show();
//    }

    private void setColor(){
        tv_select_label_color.setText("");
        tv_select_label_color.setBackgroundColor(Color.parseColor(mSelectedColor));
    }

    private ArrayList<String> colorsList(){
        ArrayList<String> colorsList = new ArrayList<>();
        colorsList.add("#43C86F");
        colorsList.add("#0C90F1");
        colorsList.add("#F72400");
        colorsList.add("#7A8089");
        colorsList.add("#D57C1D");
        colorsList.add("#770000");
        colorsList.add("#0022F8");

        return colorsList;
    }
//FIXME
//    private void labelColorsListDialog() {
//        ArrayList colorsList = this.colorsList();
//        Context var10004 = (Context)this;
//        String var10006 = this.getResources().getString(1900114);
//        Intrinsics.checkExpressionValueIsNotNull(var10006, "resources.getString(R.st…g.str_select_label_color)");
//      <undefinedtype> listDialog = new LabelColorListDialog(var10004, colorsList, var10006, this.mSelectedColor) {
//            protected void onItemSelected(@NotNull String color) {
//                Intrinsics.checkParameterIsNotNull(color, "color");
//                CardDetailsActivity.this.mSelectedColor = color;
//                CardDetailsActivity.this.setColor();
//            }
//        };
//        listDialog.show();
//    }

//FIXME
//    private void setupSelectedMembersList() {
//        ArrayList<String> cardAssignedMembersList = ((Card)((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().get(this.mCardPosition)).getAssignedTo();
//        ArrayList<SelectedMembers> selectedMembersList = new ArrayList<>();
//        for(int i=0;i<mMembersDetailList.size()-1;i++){
//            for(int j=0;j<cardAssignedMembersList.size()-1;j++){
//                if(mMembersDetailList.get(i)==cardAssignedMembersList.get(j)){
//                    SelectedMembers selectedMembers = new SelectedMembers(((User)mMembersDetailList.get(i)).getId(),((User)mMembersDetailList.get(i)).getImage());
//                    selectedMembersList.add(selectedMembers);
//                }
//            }
//        }
//
//        if(selectedMembersList.size()>0){
//            selectedMembersList.add(new SelectedMembers("",""));
//            tv_select_members.setVisibility(View.GONE);
//            rv_selected_members.setVisibility(View.VISIBLE);
//
//            rv_selected_members.setLayoutManager(new GridLayoutManager(this,6));
//            CardMemberListItemsAdapter adapter = new CardMemberListItemsAdapter(this,selectedMembersList,true);
//            rv_selected_members.setAdapter(adapter);
//            adapter.setOnClickListener((com.projemanag.adapters.CardMemberListItemsAdapter.OnClickListener)(new com.projemanag.adapters.CardMemberListItemsAdapter.OnClickListener() {
//                public void onClick() {
//                    CardDetailsActivity.this.membersListDialog();
//                }
//            }));
//        }else{
//            tv_select_members.setVisibility(View.VISIBLE);
//            rv_selected_members.setVisibility(View.GONE);
//        }
//    }

    private void showDataPicker(){
        Calendar c = Calendar.getInstance();
        int year = c.get(1);
        int month = c.get(2);
        int day = c.get(5);
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public final void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String sDayOfMonth = dayOfMonth < 10 ? "" + '0' + dayOfMonth : String.valueOf(dayOfMonth);
                String sMonthOfYear = monthOfYear + 1 < 10 ? "" + '0' + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
                String selectedDate = sDayOfMonth + '/' + sMonthOfYear + '/' + year;
                tv_select_due_date.setText(selectedDate);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                Date theDate = null;
                try {
                    theDate = sdf.parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mSelectedDueDateMilliSeconds = theDate.getTime();
            }
        }, year, month, day);
        dpd.show();
    }
}