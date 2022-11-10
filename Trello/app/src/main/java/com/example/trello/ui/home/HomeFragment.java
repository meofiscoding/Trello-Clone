package com.example.trello.ui.home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trello.Constants;
import com.example.trello.R;
import com.example.trello.RecycleClick.RecyclerItemClickListener;
import com.example.trello.SplachActivity;
import com.example.trello.TaskListActivity;
import com.example.trello.adapters.MyBoardViewHolder;
import com.example.trello.databinding.FragmentHomeBinding;
import com.example.trello.model.Board;
import com.example.trello.onItemClick;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements onItemClick {

    private FragmentHomeBinding binding;
    private RecyclerView rv_boards_list;
    private TextView tv_no_boards_available;
    private Query boardQuery;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private List<Board> boardArrayList;
    private View root;
    private FirestoreRecyclerAdapter<Board, MyBoardViewHolder> boardAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        bindingView();
        bindingAction();
        rv_boards_list.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv_boards_list.setHasFixedSize(true);
        db = FirebaseFirestore.getInstance();
        boardArrayList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        boardQuery = db.collection(Constants.BOARDS).whereArrayContains(Constants.ASSIGNED_TO, mAuth.getCurrentUser().getUid());
        FirestoreRecyclerOptions<Board> boardRecyclerOptions = new FirestoreRecyclerOptions.Builder<Board>()
                .setQuery(boardQuery, Board.class)
                .build();
        boardAdapter = new FirestoreRecyclerAdapter<Board, MyBoardViewHolder>(boardRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull MyBoardViewHolder holder, int position, @NonNull Board model) {
                Glide
                        .with(root.getContext())
                        .load(boardArrayList.get(position).getImage())
                        .centerCrop()
                        .placeholder(R.drawable.ic_board_place_holder)
                        .into(holder.boardImage);
                holder.boardname.setText(boardArrayList.get(position).getName());
                holder.boardcreated.setText(boardArrayList.get(position).getCreatedby());
            }

            @NonNull
            @Override
            public MyBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(root.getContext()).inflate(R.layout.item_board, parent, false);
                return new MyBoardViewHolder(v);
            }

            @Override
            public int getItemCount() {
                if (boardArrayList != null) {
                    return boardArrayList.size();
                }
                return 0;
            }
        };

        boardQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }

                if (!value.isEmpty()) {
                    List<DocumentSnapshot> list = value.getDocuments();
                    for (DocumentSnapshot d : list) {
                        Board board = d.toObject(Board.class);
                        board.setDocumentId(d.getId());
                        boardArrayList.add(board);
                    }
                }
                populateBoardsListToUI(boardArrayList);
            }
        });
        rv_boards_list.setAdapter(boardAdapter);
        boardAdapter.startListening();

    }

    private void bindingView() {
        rv_boards_list = binding.rvBoardsList;
        tv_no_boards_available = binding.tvNoBoardsAvailable;
    }

    private void bindingAction() {
    }

    public void populateBoardsListToUI(List<Board> boardsList) {

        //hideProgressDialog()

        if (boardArrayList != null && boardArrayList.size() > 0) {
            rv_boards_list.setVisibility(View.VISIBLE);
            tv_no_boards_available.setVisibility(View.GONE);
            rv_boards_list.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), rv_boards_list, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getContext(), TaskListActivity.class);
                            intent.putExtra(Constants.DOCUMENT_ID, boardsList.get(position).getName());
                            startActivity(intent);
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }

                    })
            );

        } else {
            rv_boards_list.setVisibility(View.GONE);
            tv_no_boards_available.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClicked(Board board) {
        Toast.makeText(this.getContext(), board.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this.getContext(), TaskListActivity.class);
        startActivity(intent);
    }
}