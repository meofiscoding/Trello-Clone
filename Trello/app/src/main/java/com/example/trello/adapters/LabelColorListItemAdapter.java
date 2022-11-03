package com.example.trello.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.R;
import com.example.trello.RecycleClick.RecyclerItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LabelColorListItemAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<String> list;
    private String mSelectedColor;

    public LabelColorListItemAdapter(Context context, ArrayList<String> list, String mSelectedColor) {
        this.context = context;
        this.list = list;
        this.mSelectedColor = mSelectedColor;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_label_color,parent,false);
        return new LabelColorListItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String item = list.get(position);
        if(holder instanceof LabelColorListItemAdapter.MyViewHolder){
            View v = holder.itemView;
            v.findViewById(R.id.view_main).setBackgroundColor(Color.parseColor(item));
            if(item.equalsIgnoreCase(mSelectedColor)){
                v.findViewById(R.id.iv_selected_color).setVisibility(View.VISIBLE);
            }else {
                v.findViewById(R.id.iv_selected_color).setVisibility(View.GONE);
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
