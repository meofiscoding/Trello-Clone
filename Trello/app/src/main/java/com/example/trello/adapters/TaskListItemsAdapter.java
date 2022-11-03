package com.example.trello.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.R;
import com.example.trello.TaskListActivity;
import com.example.trello.model.Board;
import com.example.trello.model.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TaskListItemsAdapter extends RecyclerView.Adapter<TaskListItemsAdapter.TaskViewHolder> {

    private Context context;
    private ArrayList<Task> list;

    private int mPositionDraggedFrom = -1;
    // A global variable for position dragged TO.
    private int mPositionDraggedTo = -1;

    public TaskListItemsAdapter(Context context, ArrayList<Task> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) ((double) parent.getWidth() * 0.7D), -2);
        // Here the dynamic margins are applied to the view.
        layoutParams.setMargins(15,0,40,0);
        view.setLayoutParams((android.view.ViewGroup.LayoutParams) layoutParams);
        return new TaskListItemsAdapter.TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task model = list.get(position);


        if (position == list.size() ) {
            holder.tv_add_task_list.setVisibility(View.VISIBLE);
            holder.ll_task_item.setVisibility(View.GONE);
        } else {
            holder.tv_add_task_list.setVisibility(View.GONE);
            holder.ll_task_item.setVisibility(View.VISIBLE);
        }

        holder.tv_task_list_title.setText(model.getTitle());

        holder.tv_add_task_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_add_task_list.setVisibility(View.GONE);
                holder.cv_add_task_list_name.setVisibility(View.VISIBLE);
            }
        });




        holder.ib_close_list_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            holder.tv_add_task_list.setVisibility(View.VISIBLE);
            holder.cv_add_task_list_name.setVisibility(View.GONE);
            }
        });

        holder.ib_done_list_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String listName = holder.et_task_list_name.getText().toString();

            if (!listName.equals("")) {
                // Here we check the context is an instance of the TaskListActivity.
                if (context instanceof TaskListActivity){
                    //FIXME
                    //context.createTaskList(listName)
                }
            } else {
                Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show();
            }
            }
        });

        holder.ib_edit_list_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.et_edit_task_list_name.setText(model.getTitle()) ;// Set the existing title
            holder.ll_title_view.setVisibility(View.GONE);
            holder.cv_edit_task_list_name.setVisibility(View.VISIBLE);
            }
        });


        holder.ib_close_editable_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ll_title_view.setVisibility(View.VISIBLE);
                holder.cv_edit_task_list_name.setVisibility(View.GONE);
            }
        });

        holder.ib_done_edit_list_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listName = holder.et_edit_task_list_name.getText().toString();

                if (!listName.equals("")) {
                    if (context instanceof TaskListActivity){
                        //FIXME
                        //context.updateTaskList(position, listName, model)
                    }
                } else {
                    Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show();
                }
            }
        });

       holder.ib_delete_list.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              // alertDialogForDeleteList(position, model.getTitle());
           }
       });

        holder.tv_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_add_card.setVisibility(View.GONE);
                holder.cv_add_card.setVisibility(View.VISIBLE);

                holder.ib_close_card_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.tv_add_card.setVisibility(View.VISIBLE);
                        holder.cv_add_card.setVisibility(View.GONE);
                    }
                });

                holder.ib_done_card_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cardName = holder.et_card_name.getText().toString();

                        if (!cardName.equals("")) {
                            if (context instanceof TaskListActivity){
                               // context.addCardToTaskList(position, cardName)
                            }
                        } else {
                            Toast.makeText(context, "Please Enter Card Detail.", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
            }
        }); {


        }

        holder.rv_card_list.setLayoutManager(new LinearLayoutManager(context));
        holder.rv_card_list.setHasFixedSize(true);

//        val adapter =
//                CardListItemsAdapter(context, model.cards)
//        holder.rv_card_list.adapter = adapter
//
//        adapter.setOnClickListener(object :
//        CardListItemsAdapter.OnClickListener {
//            override fun onClick(cardPosition:Int){
//                if (context is TaskListActivity){
//                    context.cardDetails(position, cardPosition)
//                }
//            }
//        })

        /**
         * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
         * {@link LinearLayoutManager}.
         *
         * @param context Current context, it will be used to access resources.
         * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
         */
        DividerItemDecoration dividerItemDecoration =new
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
       holder.rv_card_list.addItemDecoration(dividerItemDecoration);
//
//        //  Creates an ItemTouchHelper that will work with the given Callback.
//        val helper = ItemTouchHelper(object :
//        ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
//
//                /*Called when ItemTouchHelper wants to move the dragged item from its old position to
//                 the new position.*/
//            override fun onMove(
//                    recyclerView:RecyclerView,
//                    dragged:ViewHolder,
//                    target:ViewHolder
//                ):Boolean {
//                val draggedPosition = dragged.adapterPosition
//                val targetPosition = target.adapterPosition
//
//                if (mPositionDraggedFrom == -1) {
//                    mPositionDraggedFrom = draggedPosition
//                }
//                mPositionDraggedTo = targetPosition
//
//                /**
//                 * Swaps the elements at the specified positions in the specified list.
//                 */
//                Collections.swap(list[position].cards, draggedPosition, targetPosition)
//
//                // move item in `draggedPosition` to `targetPosition` in adapter.
//                adapter.notifyItemMoved(draggedPosition, targetPosition)
//
//                return false // true if moved, false otherwise
//            }
//
//            // Called when a ViewHolder is swiped by the user.
//            override fun onSwiped(
//                    viewHolder:ViewHolder,
//                    direction:Int
//                ){ // remove from adapter
//            }
//
//                /*Called by the ItemTouchHelper when the user interaction with an element is over and it
//                 also completed its animation.*/
//            fun clearView (recyclerView:RecyclerView, viewHolder:ViewHolder){
//                super.clearView(recyclerView, viewHolder)
//
//                if (mPositionDraggedFrom != -1 && mPositionDraggedTo != -1 && mPositionDraggedFrom != mPositionDraggedTo) {
//
//                    (context as TaskListActivity).updateCardsInTaskList(
//                            position,
//                            list[position].cards
//                    )
//                }
//
//                // Reset the global variables
//                mPositionDraggedFrom = -1
//                mPositionDraggedTo = -1
//            }
//        })

            /*Attaches the ItemTouchHelper to the provided RecyclerView. If TouchHelper is already
            attached to a RecyclerView, it will first detach from the previous one.*/
        //helper.attachToRecyclerView(holder.itemView.rv_card_list)

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_add_task_list;
        public CardView cv_add_task_list_name;
        public ImageButton ib_close_list_name;
        public EditText et_task_list_name;
        public ImageButton ib_done_list_name;
        public LinearLayout ll_task_item;
        public LinearLayout ll_title_view;
        public TextView tv_task_list_title;
        public ImageButton ib_edit_list_name;
        public ImageButton ib_delete_list;
        public CardView cv_edit_task_list_name;
        public ImageButton ib_close_editable_view;
        public EditText et_edit_task_list_name;
        public ImageButton ib_done_edit_list_name;
        public RecyclerView rv_card_list;
        public CardView cv_add_card;
        public ImageButton ib_close_card_name;
        public EditText et_card_name;
        public ImageButton ib_done_card_name;
        public TextView tv_add_card;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_add_task_list = itemView.findViewById(R.id.tv_add_task_list);
            cv_add_task_list_name = itemView.findViewById(R.id.cv_add_task_list_name);
            ib_close_list_name = itemView.findViewById(R.id.ib_close_list_name);
            et_task_list_name = itemView.findViewById(R.id.et_task_list_name);
            ib_done_list_name = itemView.findViewById(R.id.ib_done_list_name);
            ll_task_item = itemView.findViewById(R.id.ll_task_item);
            ll_title_view = itemView.findViewById(R.id.ll_title_view);
            tv_task_list_title = itemView.findViewById(R.id.tv_task_list_title);
            ib_edit_list_name = itemView.findViewById(R.id.ib_edit_list_name);
            ib_delete_list = itemView.findViewById(R.id.ib_delete_list);
            cv_edit_task_list_name = itemView.findViewById(R.id.cv_edit_task_list_name);
            ib_close_editable_view = itemView.findViewById(R.id.ib_close_editable_view);
            et_edit_task_list_name = itemView.findViewById(R.id.et_edit_task_list_name);
            ib_done_edit_list_name = itemView.findViewById(R.id.ib_done_edit_list_name);
            rv_card_list = itemView.findViewById(R.id.rv_card_list);
            cv_add_card = itemView.findViewById(R.id.cv_add_card);
            ib_close_card_name = itemView.findViewById(R.id.ib_close_card_name);
            tv_add_task_list = itemView.findViewById(R.id.et_card_name);
            ib_done_card_name = itemView.findViewById(R.id.ib_done_card_name);
            tv_add_card = itemView.findViewById(R.id.tv_add_card);
        }
    }
}
