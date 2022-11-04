package com.example.trello.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trello.R;
import com.example.trello.model.User;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MembersListItemAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<User> list;


    public MembersListItemAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_member,parent,false);
        return new MembersListItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User item = list.get(position);
        if(holder instanceof MembersListItemAdapter.MyViewHolder){
            View v = holder.itemView;
            TextView tv_name = v.findViewById(R.id.tv_member_name);
            TextView tv_email = v.findViewById(R.id.tv_member_name);
            ImageView iv = v.findViewById(R.id.iv_selected_member);
            tv_name.setText(item.getName());
            tv_email.setText(item.getEmail());
            if(item.isSelected()){
                iv.setVisibility(View.VISIBLE);
            }else{
                iv.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NotNull View view) {
            super(view);

        }
    }
}
