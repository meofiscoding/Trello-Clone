package com.example.trello.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.R;
import com.example.trello.model.Board;
import com.example.trello.model.Card;
import com.example.trello.onItemClick;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartListItemsAdapter extends RecyclerView.Adapter<CartListItemsAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Card> list;

    public CartListItemsAdapter(Context context, ArrayList<Card> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CartListItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_card,parent,false);
        return new CartListItemsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListItemsAdapter.MyViewHolder holder, int position) {

        Card model = list.get(position);
        if(holder instanceof MyViewHolder){
            holder.tv_card_name.setText(model.getName());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_card_name;
        public MyViewHolder(@NotNull View view) {
            super(view);
            tv_card_name = view.findViewById(R.id.tv_card_name);
        }
    }
}
