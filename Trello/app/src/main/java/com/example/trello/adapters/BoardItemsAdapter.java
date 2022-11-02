package com.example.trello.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trello.Constants;
import com.example.trello.R;
import com.example.trello.TaskListActivity;
import com.example.trello.model.Board;
import com.example.trello.onItemClick;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BoardItemsAdapter extends RecyclerView.Adapter<BoardItemsAdapter.MyViewHolder> {
    private onItemClick itemClick;
    private Context context;
    private ArrayList<Board> list;

    public BoardItemsAdapter(Context context, ArrayList<Board> list, onItemClick onItemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = onItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_board,parent,false);
        return new BoardItemsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide
                .with(context)
                .load(list.get(position).getImage())
                .centerCrop()
                .placeholder(R.drawable.ic_board_place_holder)
                .into(holder.boardImage);
        holder.boardname.setText(list.get(position).getName());
        holder.boardcreated.setText(list.get(position).getCreatedby());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onItemClicked(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView boardImage;
        public TextView boardname;
        public TextView boardcreated;
        public CardView cardView;
        public MyViewHolder(@NotNull View view) {
            super(view);
            boardImage= view.findViewById(R.id.iv_board_image);
            boardname=view.findViewById(R.id.tv_name);
            boardcreated=view.findViewById(R.id.tv_created_by);
            cardView = view.findViewById(R.id.board_container);
        }

    }
}
