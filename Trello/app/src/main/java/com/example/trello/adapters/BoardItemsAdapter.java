package com.example.trello.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trello.R;
import com.example.trello.model.Board;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.jvm.internal.Intrinsics;

public class BoardItemsAdapter extends RecyclerView.Adapter {

    private BoardItemsAdapter.OnClickListener onClickListener;
    private Context context;
    private ArrayList<Board> list;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_board,parent,false);
        return new BoardItemsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Board model = list.get(position);
        CircleImageView iv= holder.itemView.findViewById(R.id.iv_board_image);
        TextView tv_name = holder.itemView.findViewById(R.id.tv_name);
        TextView tv_created_by = holder.itemView.findViewById(R.id.tv_created_by);
        if(holder instanceof MyViewHolder){
            Glide.with(context).load(model.getImage()).centerCrop().placeholder(R.drawable.ic_board_place_holder)
                    .into(iv);
            tv_name.setText(model.getName());
            tv_created_by.setText("Created by: "+model.getCreatedby());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClickListener!=null){
                        onClickListener.onClick(position,model);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private static final class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NotNull View view) {
            super(view);
        }
    }
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    interface OnClickListener {
        void onClick(int position,Board model);
    }
}
