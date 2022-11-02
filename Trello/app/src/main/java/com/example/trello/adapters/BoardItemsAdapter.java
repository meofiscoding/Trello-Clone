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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.jvm.internal.Intrinsics;

public class BoardItemsAdapter extends RecyclerView.Adapter<BoardItemsAdapter.MyViewHolder> {

    private BoardItemsAdapter.OnClickListener onClickListener;
    private Context context;
    private ArrayList<Board> list;

    public BoardItemsAdapter(Context context, ArrayList<Board> list) {
        this.context = context;
        this.list = list;
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

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView boardImage;
        public TextView boardname;
        public TextView boardcreated;
        public MyViewHolder(@NotNull View view) {
            super(view);
            boardImage= view.findViewById(R.id.iv_board_image);
            boardname=view.findViewById(R.id.tv_name);
            boardcreated=view.findViewById(R.id.tv_created_by);


        }
    }
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    interface OnClickListener {
        void onClick(int position,Board model);
    }
}
