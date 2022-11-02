package com.example.trello.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trello.MainActivity;
import com.example.trello.adapters.BoardItemsAdapter;
import com.example.trello.databinding.FragmentHomeBinding;
import com.example.trello.model.Board;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rv_boards_list;
    private TextView tv_no_boards_available;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bindingView();
        bindingAction();
        return root;


    }

    private void bindingView() {
        rv_boards_list = binding.rvBoardsList;
        tv_no_boards_available = binding.tvNoBoardsAvailable;
    }

    private void bindingAction(){
        ArrayList<Board> boardArrayList= new ArrayList<>();
        Board board1= new Board("Test1","https://cdn.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-1520x800.png","Thinh",new ArrayList());
        Board board2= new Board("Test1","https://cdn.searchenginejournal.com/wp-content/uploads/2022/06/image-search-1600-x-840-px-62c6dc4ff1eee-sej-1520x800.png","Thinh",new ArrayList());
        boardArrayList.add(board1);
        boardArrayList.add(board2);
        populateBoardsListToUI(boardArrayList);
    }

    public void populateBoardsListToUI(ArrayList<Board> boardsList) {

        //hideProgressDialog()

        if (boardsList.size() > 0) {

            rv_boards_list.setVisibility(View.VISIBLE);
            tv_no_boards_available.setVisibility(View.GONE);

            rv_boards_list.setLayoutManager(new LinearLayoutManager(this.getContext()));
            rv_boards_list.setHasFixedSize(true);

            // Create an instance of BoardItemsAdapter and pass the boardList to it.
            BoardItemsAdapter adapter = new BoardItemsAdapter(this.getContext(), boardsList);
            rv_boards_list.setAdapter(adapter);  // Attach the adapter to the recyclerView.

//            adapter.setOnClickListener( :
//            BoardItemsAdapter.OnClickListener {
//                override fun onClick(position: Int, model: Board) {
//                    val intent = Intent(this@MainActivity, TaskListActivity::class.java)
//                    intent.putExtra(Constants.DOCUMENT_ID, model.documentId)
//                    startActivity(intent)
//                }
//            })
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
}