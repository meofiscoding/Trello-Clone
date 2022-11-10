package com.example.trello.adapters;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.R;
import com.example.trello.RecycleClick.RecyclerItemClickListener;
import com.example.trello.TaskListActivity;
import com.example.trello.model.Board;
import com.example.trello.model.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskListItemsAdapter extends RecyclerView.Adapter<TaskListItemsAdapter.TaskViewHolder> {

    private Context context;
    private ArrayList<Task> list;

    private int mPositionDraggedFrom = -1;
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

    private void alertDialogForDeleteList(int position,String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.alert);
        builder.setMessage("Are you sure you want to delete "+ title+" ?");
        builder.setIcon(R.drawable.ic_delete);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //((TaskListActivity) context).deleteTaskList(title);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        if (position == list.size() ) {
            holder.tv_add_task_list.setVisibility(View.VISIBLE);
            holder.ll_task_item.setVisibility(View.GONE);

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
                            ((TaskListActivity) context).createTaskList(listName);
                            ((TaskListActivity) context).getData();
                        }
                    } else {
                        Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            holder.tv_add_task_list.setVisibility(View.GONE);
            holder.ll_task_item.setVisibility(View.VISIBLE);

            Task model = list.get(position);
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
                            ((TaskListActivity) context).updateTaskList(listName);
                            ((TaskListActivity) context).getData();
                        }
                    } else {
                        Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.ib_delete_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogForDeleteList(position, model.getTitle());
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
                                    ((TaskListActivity) context).addCardToTaskList(position, cardName);
                                    ((TaskListActivity) context).getData();
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

            CartListItemsAdapter adapter = new CartListItemsAdapter(context, model.getCards());
            holder.rv_card_list.setAdapter(adapter);

            RecyclerView rv = holder.itemView.findViewById(R.id.rv_card_list);
            rv.addOnItemTouchListener(new RecyclerItemClickListener(context, rv, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int cardPosition) {
                    if(context instanceof TaskListActivity){
                        ((TaskListActivity) context).cardDetatils(list.get(position).getTitle(),cardPosition);
                    }
                }

                @Override
                public void onLongItemClick(View view, int position) {
                }
            }));

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

            //  Creates an ItemTouchHelper that will work with the given Callback.

            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    // Notify Adapter of the moved item!
                    recyclerView.getAdapter().notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                    return true;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    // No swipe action
                }

                @Override
                public boolean isItemViewSwipeEnabled() {
                    // Disable swipe (dont override this method or return true, if you want to have swipe)
                    return false;
                }

                @Override
                public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                    // Set movement flags to specify the movement direction
                    // final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;  <-- for all directions
                    // In this case only up and down is allowed
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(holder.rv_card_list);
        }

    }



    @Override
    public int getItemCount() {
        return list.size()+1;
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
            et_card_name = itemView.findViewById(R.id.et_card_name);
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
            ib_done_card_name = itemView.findViewById(R.id.ib_done_card_name);
            tv_add_card = itemView.findViewById(R.id.tv_add_card);
        }
    }
}
