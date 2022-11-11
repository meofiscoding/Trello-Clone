package com.example.trello.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trello.R;
import com.example.trello.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MembersListItemAdapter extends RecyclerView.Adapter<MembersListItemAdapter.MyViewHolder> {
    private Context context;
    private List<User> list;

    public MembersListItemAdapter(Context context, List<User> list, OnClickListener holderClickListener) {
        this.context = context;
        this.list = list;
        this.holderClickListener = holderClickListener;
    }

    public interface OnClickListener {
        public void onItemClick(int position, User user, String action);
    }

    public MembersListItemAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<User> uList) {
        this.list = uList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_member, parent, false);
        return new MembersListItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User item = list.get(position);
        if (item == null) {
            return;
        }
        Glide.with(context).load(item.getImage()).centerCrop().placeholder(R.drawable.ic_user_place_holder).into(holder.userAvatar);
        holder.username.setText(item.getName());
        holder.email.setText(item.getEmail());
        if (item.isSelected()) {
            holder.selectedMember.setVisibility(View.VISIBLE);
        } else {
            holder.selectedMember.setVisibility(View.GONE);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isSelected()){
                    holderClickListener.onItemClick(holder.getBindingAdapterPosition(),item,"Select");
                }else{
                    holderClickListener.onItemClick(holder.getBindingAdapterPosition(),item,"unSelect");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.setOnClickListener(onClickListener);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView userAvatar;
        private ImageView selectedMember;
        private TextView email;
        private TextView username;
        private LinearLayout linearLayout;

        public MyViewHolder(@NotNull View view) {
            super(view);
            email = view.findViewById(R.id.tv_member_email);
            userAvatar = view.findViewById(R.id.iv_member_image);
            selectedMember = view.findViewById(R.id.iv_selected_member);
            username = view.findViewById(R.id.tv_member_name);
            linearLayout = view.findViewById(R.id.main_container);
        }
    }
}
