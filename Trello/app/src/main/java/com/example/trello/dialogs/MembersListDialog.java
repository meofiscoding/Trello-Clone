package com.example.trello.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.CardDetailsActivity;
import com.example.trello.R;
import com.example.trello.RecycleClick.RecyclerItemClickListener;
import com.example.trello.adapters.MembersListItemAdapter;
import com.example.trello.model.User;

import java.util.ArrayList;

public abstract class MembersListDialog extends Dialog {
    private Context context;
    private ArrayList<User> list = new ArrayList<>();
    private String title;
    private TextView tv_Title;
    private RecyclerView rvList;

    public MembersListDialog(@NonNull Context context, ArrayList<User> list, String title) {
        super(context);
        this.context = context;
        this.list = list;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_list,null);
        bindingView(v);
        setContentView(v);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        setUpRecyclerView(v);
    }

    private void setUpRecyclerView(View v) {
        tv_Title.setText(title);
        if(list.size()>0){
            rvList.setLayoutManager(new LinearLayoutManager(context));
            MembersListItemAdapter adapter = new MembersListItemAdapter(context);
            rvList.setAdapter(adapter);
            rvList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvList, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    dismiss();
                    onItemSelected(position);
                }

                @Override
                public void onLongItemClick(View view, int position) {

                }
            }));
        }
    }

    private void bindingView(View v) {
        tv_Title = v.findViewById(R.id.tvTitle);
        rvList = v.findViewById(R.id.rvList);
    }

    public abstract void onItemSelected(int position);

    public MembersListDialog(@NonNull Context context) {
        super(context);
    }
}
