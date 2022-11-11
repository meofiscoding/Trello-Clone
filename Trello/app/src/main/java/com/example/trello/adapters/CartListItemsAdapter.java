package com.example.trello.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.R;
import com.example.trello.model.Card;
import com.example.trello.onItemClick;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartListItemsAdapter extends RecyclerView.Adapter<CartListItemsAdapter.CardViewHolder>{

    private Context context;
    private ArrayList<Card> list;
    private onItemClick itemClick;

    public CartListItemsAdapter(Context context, ArrayList<Card> list) {
        this.context = context;
        this.list = list;
    }

    public CartListItemsAdapter(Context context, ArrayList<Card> list, onItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_card,parent,false);
        return new CartListItemsAdapter.CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        Card model = list.get(position);

        if(model.getLabelColor()!=null){
            holder.itemView.findViewById(R.id.view_label_color).setVisibility(View.VISIBLE);
            holder.itemView.findViewById(R.id.view_label_color).setBackgroundColor(Color.parseColor(model.getLabelColor()));
        }else{
            holder.itemView.findViewById(R.id.view_label_color).setVisibility(View.GONE);
        }

        TextView tv = holder.itemView.findViewById(R.id.tv_card_name);
        tv.setText(model.getName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_card_name;
        public CardViewHolder(@NotNull View view) {
            super(view);
            tv_card_name = view.findViewById(R.id.tv_card_name);
        }
    }
}
