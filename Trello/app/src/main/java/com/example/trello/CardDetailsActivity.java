package com.example.trello;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.dialogs.LabelColorListDialog;
import com.example.trello.dialogs.MembersListDialog;
import com.example.trello.firebase.FirestoreClass;
import com.example.trello.model.Board;
import com.example.trello.model.Card;
import com.example.trello.model.Task;
import com.example.trello.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CardDetailsActivity extends BaseActivity {
    private Card card;
    private Board mBoardDetails;
    private int mTaskListPosition = -1;
    private int mCardPosition = -1;
    private String mSelectedColor;
    private ArrayList<User> mMembersDetailList;
    private long mSelectedDueDateMilliSeconds;
    private EditText et_name_card_details;
    private TextView tv_select_label_color;
    private TextView tv_select_members;
    private TextView tv_select_due_date;
    private Toolbar toolbar;
    private RecyclerView rv_selected_members;
    private Button btn_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        bindingView();
        getIntentData();
        setupActionBar();
        bindingAction();

    }

    private void bindingAction() {
        et_name_card_details.setText(card.getName());
        et_name_card_details.setSelection(et_name_card_details.getText().toString().length());
        //et_name_card_details.setText(((Card)((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().get(this.mCardPosition)).getName());
        //et_name_card_details.setSelection(et_name_card_details.getText().toString().length());
        //mSelectedColor = ((Card)((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().get(this.mCardPosition)).getLabelColor();
        mSelectedColor = card.getLabelColor();
        if (!(mSelectedColor == null)) {
            setColor();
        }
        tv_select_label_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                labelColorsListDialog();
            }
        });
        setupSelectedMembersList();
        tv_select_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                membersListDialog();
            }
        });
        //mSelectedDueDateMilliSeconds = ((Card)((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().get(this.mCardPosition)).getDueDate();
        if (card.getDueDate() != null) {
            mSelectedDueDateMilliSeconds = card.getDueDate();
            if (mSelectedDueDateMilliSeconds > 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(new Date(mSelectedDueDateMilliSeconds));
                tv_select_due_date.setText(date);
            }
        }
        tv_select_due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_name_card_details.getText().toString().isEmpty()) {
                    //Toast.makeText(this,"Enter card name",Toast.LENGTH_SHORT).show();
                } else {
                    updateCardDetails();
                }
            }
        });
    }

    private void bindingView() {
        toolbar = findViewById(R.id.toolbar_card_details_activity);
        et_name_card_details = findViewById(R.id.et_name_card_details);
        tv_select_label_color = findViewById(R.id.tv_select_label_color);
        tv_select_members = findViewById(R.id.tv_select_members);
        tv_select_due_date = findViewById(R.id.tv_select_due_date);
        rv_selected_members = findViewById(R.id.rv_selected_members_list);
        btn_update = findViewById(R.id.btn_update_card_details);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_delete_card, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_card:
                alertDialogForDeleteCard(((Card) ((Task) mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().get(this.mCardPosition)).getName());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp);
            actionBar.setTitle(card.getName());
            //actionBar.setTitle(((Card)((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().get(this.mCardPosition)).getName());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardDetailsActivity.this.onBackPressed();
            }
        });
    }

    private final void getIntentData() {
        if (this.getIntent().hasExtra("task_list_item_position")) {
            this.mTaskListPosition = this.getIntent().getIntExtra("task_list_item_position", -1);
        }

        if (this.getIntent().hasExtra("card_list_item_position")) {
            this.mCardPosition = this.getIntent().getIntExtra("card_list_item_position", -1);
        }

        if (this.getIntent().hasExtra("board_detail")) {
            this.mBoardDetails = (Board) this.getIntent().getParcelableExtra("board_detail");
        }

        if (this.getIntent().hasExtra("board_members_list")) {
            this.mMembersDetailList = this.getIntent().getParcelableArrayListExtra("board_members_list");
        }
        if (this.getIntent().hasExtra("card")) {
            this.card = (Card) this.getIntent().getSerializableExtra("card");
        }
    }

    public void addUpdateTaskListSuccess() {
        //hideProgressDialog();
        setResult(Activity.RESULT_OK);
        Intent i = new Intent(this, TaskListActivity.class);
        i.putExtra(Constants.DOCUMENT_ID, mBoardDetails.getName());
        startActivity(i);
    }

    private void updateCardDetails() {
        Card cardUpdate = new Card(et_name_card_details.getText().toString()
                , card.getCreatedBy()
                , card.getAssignedTo()
                , mSelectedColor
                , mSelectedDueDateMilliSeconds
                , card.getTaskname());
//        ArrayList<Task> taskList = mBoardDetails.getTaskList();
//        taskList.remove(taskList.size()-1);
//        ((Task)mBoardDetails.getTaskList().get(this.mTaskListPosition)).getCards().set(this.mCardPosition, card);
        //showProgressDialog("Please Wait");
        FirestoreClass firestoreClass = new FirestoreClass();
        firestoreClass.updateCard(this, card, cardUpdate);
    }

    private final void deleteCard() {
        ArrayList<Card> cardsList = mBoardDetails.getTaskList().get(this.mTaskListPosition).getCards();
        cardsList.remove(mCardPosition);
        ArrayList<Task> taskList = mBoardDetails.getTaskList();
        taskList.remove(taskList.size() - 1);
        ((Task) taskList.get(this.mTaskListPosition)).setCards(cardsList);
        //showProgressDialog("Please Wait");
        //FirestoreClass.addUpdateTaskList(this, mBoardDetails);
    }

    private void alertDialogForDeleteCard(String cardName) {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context) this);
        builder.setTitle(R.string.alert);
        builder.setMessage("Are you sure you want to delete card " + cardName + " ?");
        builder.setIcon(R.drawable.ic_delete);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                deleteCard();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void setColor() {
        tv_select_label_color.setText("");
        tv_select_label_color.setBackgroundColor(Color.parseColor(mSelectedColor));
    }

    private ArrayList<String> colorsList() {
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

    private void labelColorsListDialog() {
        ArrayList colorsList = colorsList();
        LabelColorListDialog listDialog = new LabelColorListDialog(this, colorsList(), "Test", mSelectedColor) {
            @Override
            public void onItemSelected(int position) {
                mSelectedColor = colorsList().get(position);
                setColor();
            }
        };
        listDialog.show();
    }

    private void setupSelectedMembersList() {
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
    }

    private void membersListDialog() {
        ArrayList<String> cardAssignedMembersList = mBoardDetails.getTaskList().get(this.mTaskListPosition).getCards().get(this.mCardPosition).getAssignedTo();
        if (cardAssignedMembersList.size() > 0) {
            for (int i = 0; i < mMembersDetailList.size() - 1; i++) {
                for (String memberId : cardAssignedMembersList) {
                    if (mMembersDetailList.get(i).getId().equalsIgnoreCase(memberId)) {
                        (mMembersDetailList.get(i)).setSelected(true);
                    }
                }
            }
        } else {
            for (int i = 0; i < mMembersDetailList.size() - 1; i++) {
                (mMembersDetailList.get(i)).setSelected(false);
            }
        }
        MembersListDialog dialog = new MembersListDialog(this, mMembersDetailList, "Select member") {
            @Override
            public void onItemSelected(User user, String action) {

            }
        };
//        MembersListDialog dialog = new MembersListDialog(this,mMembersDetailList,"Select member") {
//            @Override
//            public void onItemSelected(int position) {
//
//            }
//        };
        dialog.show();
    }

    private void showDatePicker() {
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