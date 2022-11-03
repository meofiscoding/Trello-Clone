package com.example.trello.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.R;

import org.jetbrains.annotations.NotNull;

public class MyBoardViewHolder extends RecyclerView.ViewHolder {
    public ImageView boardImage;
    public TextView boardname;
    public TextView boardcreated;
    public CardView cardView;

    public MyBoardViewHolder(@NotNull View view) {
        super(view);
        boardImage = view.findViewById(R.id.iv_board_image);
        boardname = view.findViewById(R.id.tv_name);
        boardcreated = view.findViewById(R.id.tv_created_by);
        cardView = view.findViewById(R.id.board_container);
    }
}
