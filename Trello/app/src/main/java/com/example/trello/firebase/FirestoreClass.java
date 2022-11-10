package com.example.trello.firebase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.trello.CardDetailsActivity;
import com.example.trello.Constants;
import com.example.trello.CreateBoardActivity;
//import com.example.trello.TaskListActivity;
import com.example.trello.TaskListActivity;
import com.example.trello.model.Board;
import com.example.trello.model.Card;
import com.example.trello.model.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreClass {
    private static final FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

    public  void addUpdateTaskList(Activity activity,  Task task) {

        mFireStore.collection("tasks").document().set(task, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e(activity.getClass().getSimpleName(), "Task created successfully.");
                        Toast.makeText((Context)activity, (CharSequence)"Task created successfully.", Toast.LENGTH_LONG).show();
                        //activity.boardCreatedSuccessfully();
                    }
                }).addOnFailureListener(e->{
                    // activity.hideProgressDialog();
                    Log.e(activity.getClass().getSimpleName(), "Error while creating a board.", (Throwable)e);
                });

    }

    public  void addUpdateCard(TaskListActivity activity,  Card card) {

        mFireStore.collection("cards").document().set(card, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e(activity.getClass().getSimpleName(), "Card created successfully.");
                        Toast.makeText((Context)activity, (CharSequence)"Card created successfully.", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(e->{
                    Log.e(activity.getClass().getSimpleName(), "Error while creating a board.", (Throwable)e);
                });

    }

    public static final void createBoard(CreateBoardActivity activity, Board board) {
        mFireStore.collection(Constants.BOARDS).document().set(board, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e(activity.getClass().getSimpleName(), "Board created successfully.");
                        Toast.makeText((Context)activity, (CharSequence)"Board created successfully.", Toast.LENGTH_LONG).show();
                        activity.boardCreatedSuccessfully();
                    }
                }).addOnFailureListener(e->{
                    activity.hideProgressDialog();
                    Log.e(activity.getClass().getSimpleName(), "Error while creating a board.", (Throwable)e);
                });
    }


    public  void getBoardDetails( TaskListActivity activity,  String documentId) {
        this.mFireStore.collection("boards").whereEqualTo("name",documentId).get().addOnSuccessListener(new OnSuccessListener() {
            public void onSuccess(Object var1) {
                this.onSuccess((QuerySnapshot)var1);
            }

            public final void onSuccess(QuerySnapshot document) {
                Log.e(activity.getClass().getSimpleName(), document.toString());
               Board board=document.getDocuments().get(0).toObject(Board.class);
                //board.setDocumentId(document.getId());
                activity.boardDetails(board);
            }
        });
    }

    public  void getTaskDetails( TaskListActivity activity,  String board) {
        this.mFireStore.collection("tasks").whereEqualTo("boardname",board).get().addOnSuccessListener(new OnSuccessListener() {
            // $FF: synthetic method
            // $FF: bridge method
            public void onSuccess(Object var1) {
                this.onSuccess((QuerySnapshot)var1);
            }

            public final  void onSuccess(QuerySnapshot document) {
                Log.e(activity.getClass().getSimpleName(), document.toString());
               // Board board=document.getDocuments().get(0).toObject(Board.class);
                ArrayList<Task> taskArrayList = new ArrayList<Task>();
                List<DocumentSnapshot> list = document.getDocuments();
                for (DocumentSnapshot d : list) {
                    Task task = d.toObject(Task.class);
                    //board.setDocumentId(d.getId());
                    taskArrayList.add(task);
                }
                //board.setDocumentId(document.getId());
                activity.taskDetails(taskArrayList);
            }
        });
    }

    public  void getCardDetails( TaskListActivity activity,  ArrayList<Task> tasks) {
        HashMap<String,ArrayList<Card>> listHashMap= new HashMap<>();
        if(tasks!=null) {
            for (Task task : tasks) {
                this.mFireStore.collection("cards").whereEqualTo("title", task.getTitle()).get().addOnSuccessListener(new OnSuccessListener() {
                    // $FF: synthetic method
                    // $FF: bridge method
                    public void onSuccess(Object var1) {
                        this.onSuccess((QuerySnapshot) var1);
                    }

                    public final void onSuccess(QuerySnapshot document) {
                        Log.e(activity.getClass().getSimpleName(), document.toString());
                        // Board board=document.getDocuments().get(0).toObject(Board.class);
                        HashMap<String, ArrayList<Card>> listHashMap = new HashMap<>();
                        ArrayList<Card> cardArrayList = new ArrayList<Card>();
                        List<DocumentSnapshot> list = document.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Card card = d.toObject(Card.class);
                            //board.setDocumentId(d.getId());
                            cardArrayList.add(card);
                        }
                        listHashMap.put(task.getTitle(), cardArrayList);
                        //board.setDocumentId(document.getId());

                    }
                });
                activity.carddetail(listHashMap);
            }
        }

    }





            // $FF: synthetic method
            // $FF: bridge method



//    public final void getAssignedMembersListDetails(@NotNull final Activity activity, @NotNull ArrayList assignedTo) {
//        Intrinsics.checkParameterIsNotNull(activity, "activity");
//        Intrinsics.checkParameterIsNotNull(assignedTo, "assignedTo");
//        this.mFireStore.collection("users").whereIn("id", (List)assignedTo).get().addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
//            // $FF: synthetic method
//            // $FF: bridge method
//            public void onSuccess(Object var1) {
//                this.onSuccess((QuerySnapshot)var1);
//            }
//
//            public final void onSuccess(QuerySnapshot document) {
//                String var10000 = activity.getClass().getSimpleName();
//                Intrinsics.checkExpressionValueIsNotNull(document, "document");
//                Log.e(var10000, document.getDocuments().toString());
//                ArrayList usersList = new ArrayList();
//                Iterator var4 = document.getDocuments().iterator();
//
//                while(var4.hasNext()) {
//                    DocumentSnapshot i = (DocumentSnapshot)var4.next();
//                    Object var6 = i.toObject(User.class);
//                    if (var6 == null) {
//                        Intrinsics.throwNpe();
//                    }
//
//                    Intrinsics.checkExpressionValueIsNotNull(var6, "i.toObject(User::class.java)!!");
//                    User user = (User)var6;
//                    usersList.add(user);
//                }
//
//                if (activity instanceof MembersActivity) {
//                    ((MembersActivity)activity).setupMembersList(usersList);
//                } else if (activity instanceof TaskListActivity) {
//                    ((TaskListActivity)activity).boardMembersDetailList(usersList);
//                }
//
//            }
//        })).addOnFailureListener((OnFailureListener)(new OnFailureListener() {
//            public final void onFailure(@NotNull Exception e) {
//                Intrinsics.checkParameterIsNotNull(e, "e");
//                if (activity instanceof MembersActivity) {
//                    ((MembersActivity)activity).hideProgressDialog();
//                } else if (activity instanceof TaskListActivity) {
//                    ((TaskListActivity)activity).hideProgressDialog();
//                }
//
//                Log.e(activity.getClass().getSimpleName(), "Error while creating a board.", (Throwable)e);
//            }
//        }));
//    }

//    public final void getMemberDetails(@NotNull final MembersActivity activity, @NotNull String email) {
//        Intrinsics.checkParameterIsNotNull(activity, "activity");
//        Intrinsics.checkParameterIsNotNull(email, "email");
//        this.mFireStore.collection("users").whereEqualTo("email", email).get().addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
//            // $FF: synthetic method
//            // $FF: bridge method
//            public void onSuccess(Object var1) {
//                this.onSuccess((QuerySnapshot)var1);
//            }
//
//            public final void onSuccess(QuerySnapshot document) {
//                String var10000 = activity.getClass().getSimpleName();
//                Intrinsics.checkExpressionValueIsNotNull(document, "document");
//                Log.e(var10000, document.getDocuments().toString());
//                if (document.getDocuments().size() > 0) {
//                    Object var3 = ((DocumentSnapshot)document.getDocuments().get(0)).toObject(User.class);
//                    if (var3 == null) {
//                        Intrinsics.throwNpe();
//                    }
//
//                    Intrinsics.checkExpressionValueIsNotNull(var3, "document.documents[0].toObject(User::class.java)!!");
//                    User user = (User)var3;
//                    activity.memberDetails(user);
//                } else {
//                    activity.hideProgressDialog();
//                    activity.showErrorSnackBar("No such member found.");
//                }
//
//            }
//        })).addOnFailureListener((OnFailureListener)(new OnFailureListener() {
//            public final void onFailure(@NotNull Exception e) {
//                Intrinsics.checkParameterIsNotNull(e, "e");
//                activity.hideProgressDialog();
//                Log.e(activity.getClass().getSimpleName(), "Error while getting user details", (Throwable)e);
//            }
//        }));
//    }

//    public final void assignMemberToBoard(@NotNull final MembersActivity activity, @NotNull Board board, @NotNull final User user) {
//        Intrinsics.checkParameterIsNotNull(activity, "activity");
//        Intrinsics.checkParameterIsNotNull(board, "board");
//        Intrinsics.checkParameterIsNotNull(user, "user");
//        HashMap assignedToHashMap = new HashMap();
//        ((Map)assignedToHashMap).put("assignedTo", board.getAssignedTo());
//        this.mFireStore.collection("boards").document(board.getDocumentId()).update((Map)assignedToHashMap).addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
//            // $FF: synthetic method
//            // $FF: bridge method
//            public void onSuccess(Object var1) {
//                this.onSuccess((Void)var1);
//            }
//
//            public final void onSuccess(Void it) {
//                Log.e(activity.getClass().getSimpleName(), "TaskList updated successfully.");
//                activity.memberAssignSuccess(user);
//            }
//        })).addOnFailureListener((OnFailureListener)(new OnFailureListener() {
//            public final void onFailure(@NotNull Exception e) {
//                Intrinsics.checkParameterIsNotNull(e, "e");
//                activity.hideProgressDialog();
//                Log.e(activity.getClass().getSimpleName(), "Error while creating a board.", (Throwable)e);
//            }
//        }));
//    }

    public static final String getCurrentUserID() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String currentUserID = "";
        if (currentUser != null) {
            currentUserID = currentUser.getUid();
        }

        return currentUserID;
    }

}
