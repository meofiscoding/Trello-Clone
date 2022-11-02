package com.example.trello.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.trello.Constants;
import com.example.trello.CreateBoardActivity;
//import com.example.trello.TaskListActivity;
import com.example.trello.model.Board;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class FirestoreClass {
    private static final FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();
    //FIXME
//    public final void registerUser(@NotNull final SignUpActivity activity, @NotNull User userInfo) {
//        Intrinsics.checkParameterIsNotNull(activity, "activity");
//        Intrinsics.checkParameterIsNotNull(userInfo, "userInfo");
//        this.mFireStore.collection("users").document(this.getCurrentUserID()).set(userInfo, SetOptions.merge()).addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
//            // $FF: synthetic method
//            // $FF: bridge method
//            public void onSuccess(Object var1) {
//                this.onSuccess((Void)var1);
//            }
//
//            public final void onSuccess(Void it) {
//                activity.userRegisteredSuccess();
//            }
//        })).addOnFailureListener((OnFailureListener)(new OnFailureListener() {
//            public final void onFailure(@NotNull Exception e) {
//                Intrinsics.checkParameterIsNotNull(e, "e");
//                activity.hideProgressDialog();
//                Log.e(activity.getClass().getSimpleName(), "Error writing document", (Throwable)e);
//            }
//        }));
//    }
//FIXME
//    public final void loadUserData(Activity activity,boolean readBoardsList) {
//        mFireStore.collection(Constants.USERS)
//                .document(getCurrentUserID()).get()
//                .addOnSuccessListener(document -> {
//                    Object loggedInUser =document.toObject(User.class);
//                    if(activity instanceof SignInActivity){
//                        activity.signInSuccess(loggedInUser);
//                    }else if(activity instanceof MainActivity){
//                        activity.updateNavigationUserDetails(loggedInUser, readBoardsList);
//                    }else if(activity instanceof MyProfileActivity){
//                        activity.setUserDataInUI(loggedInUser);
//                    }
//                }).addOnFailureListener(e->{
//                    if(activity instanceof SignInActivity){
//                        activity.hideProgressDialog();
//                    }else if(activity instanceof MainActivity){
//                        activity.hideProgressDialog();
//                    }else if(activity instanceof MyProfileActivity){
//                        activity.hideProgressDialog();
//                    }
//                });
//    }
//FIXME
//    public final void updateUserProfileData(@NotNull final Activity activity, @NotNull HashMap userHashMap) {
//        Intrinsics.checkParameterIsNotNull(activity, "activity");
//        Intrinsics.checkParameterIsNotNull(userHashMap, "userHashMap");
//        this.mFireStore.collection("users").document(this.getCurrentUserID()).update((Map)userHashMap).addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
//            // $FF: synthetic method
//            // $FF: bridge method
//            public void onSuccess(Object var1) {
//                this.onSuccess((Void)var1);
//            }
//
//            public final void onSuccess(Void it) {
//                Log.e(activity.getClass().getSimpleName(), "Data updated successfully!");
//                Activity var2 = activity;
//                if (var2 instanceof MainActivity) {
//                    ((MainActivity)activity).tokenUpdateSuccess();
//                } else if (var2 instanceof MyProfileActivity) {
//                    ((MyProfileActivity)activity).profileUpdateSuccess();
//                }
//
//            }
//        })).addOnFailureListener((OnFailureListener)(new OnFailureListener() {
//            public final void onFailure(@NotNull Exception e) {
//                Intrinsics.checkParameterIsNotNull(e, "e");
//                Activity var2 = activity;
//                if (var2 instanceof MainActivity) {
//                    ((MainActivity)activity).hideProgressDialog();
//                } else if (var2 instanceof MyProfileActivity) {
//                    ((MyProfileActivity)activity).hideProgressDialog();
//                }
//
//                Log.e(activity.getClass().getSimpleName(), "Error while creating a board.", (Throwable)e);
//            }
//        }));
//    }

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

//    public final void getBoardsList(@NotNull final MainActivity activity) {
//        Intrinsics.checkParameterIsNotNull(activity, "activity");
//        this.mFireStore.collection("boards").whereArrayContains("assignedTo", this.getCurrentUserID()).get().addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
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
//                ArrayList boardsList = new ArrayList();
//                Iterator var4 = document.getDocuments().iterator();
//
//                while(var4.hasNext()) {
//                    DocumentSnapshot i = (DocumentSnapshot)var4.next();
//                    Object var6 = i.toObject(Board.class);
//                    if (var6 == null) {
//                        Intrinsics.throwNpe();
//                    }
//
//                    Intrinsics.checkExpressionValueIsNotNull(var6, "i.toObject(Board::class.java)!!");
//                    Board board = (Board)var6;
//                    Intrinsics.checkExpressionValueIsNotNull(i, "i");
//                    String var10001 = i.getId();
//                    Intrinsics.checkExpressionValueIsNotNull(var10001, "i.id");
//                    board.setDocumentId(var10001);
//                    boardsList.add(board);
//                }
//
//                activity.populateBoardsListToUI(boardsList);
//            }
//        })).addOnFailureListener((OnFailureListener)(new OnFailureListener() {
//            public final void onFailure(@NotNull Exception e) {
//                Intrinsics.checkParameterIsNotNull(e, "e");
//                activity.hideProgressDialog();
//                Log.e(activity.getClass().getSimpleName(), "Error while creating a board.", (Throwable)e);
//            }
//        }));
//    }

//    public final void getBoardDetails(@NotNull final TaskListActivity activity, @NotNull String documentId) {
//        Intrinsics.checkParameterIsNotNull(activity, "activity");
//        Intrinsics.checkParameterIsNotNull(documentId, "documentId");
//        this.mFireStore.collection("boards").document(documentId).get().addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
//            // $FF: synthetic method
//            // $FF: bridge method
//            public void onSuccess(Object var1) {
//                this.onSuccess((DocumentSnapshot)var1);
//            }
//
//            public final void onSuccess(DocumentSnapshot document) {
//                Log.e(activity.getClass().getSimpleName(), document.toString());
//                Object var10000 = document.toObject(Board.class);
//                if (var10000 == null) {
//                    Intrinsics.throwNpe();
//                }
//
//                Intrinsics.checkExpressionValueIsNotNull(var10000, "document.toObject(Board::class.java)!!");
//                Board board = (Board)var10000;
//                Intrinsics.checkExpressionValueIsNotNull(document, "document");
//                String var10001 = document.getId();
//                Intrinsics.checkExpressionValueIsNotNull(var10001, "document.id");
//                board.setDocumentId(var10001);
//                activity.boardDetails(board);
//            }
//        })).addOnFailureListener((OnFailureListener)(new OnFailureListener() {
//            public final void onFailure(@NotNull Exception e) {
//                Intrinsics.checkParameterIsNotNull(e, "e");
//                activity.hideProgressDialog();
//                Log.e(activity.getClass().getSimpleName(), "Error while creating a board.", (Throwable)e);
//            }
//        }));
//    }

//    public final void addUpdateTaskList(@NotNull final Activity activity, @NotNull Board board) {
//        Intrinsics.checkParameterIsNotNull(activity, "activity");
//        Intrinsics.checkParameterIsNotNull(board, "board");
//        HashMap taskListHashMap = new HashMap();
//        ((Map)taskListHashMap).put("taskList", board.getTaskList());
//        this.mFireStore.collection("boards").document(board.getDocumentId()).update((Map)taskListHashMap).addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
//            // $FF: synthetic method
//            // $FF: bridge method
//            public void onSuccess(Object var1) {
//                this.onSuccess((Void)var1);
//            }
//
//            public final void onSuccess(Void it) {
//                Log.e(activity.getClass().getSimpleName(), "TaskList updated successfully.");
//                if (activity instanceof TaskListActivity) {
//                    ((TaskListActivity)activity).addUpdateTaskListSuccess();
//                } else if (activity instanceof CardDetailsActivity) {
//                    ((CardDetailsActivity)activity).addUpdateTaskListSuccess();
//                }
//
//            }
//        })).addOnFailureListener((OnFailureListener)(new OnFailureListener() {
//            public final void onFailure(@NotNull Exception e) {
//                Intrinsics.checkParameterIsNotNull(e, "e");
//                if (activity instanceof TaskListActivity) {
//                    ((TaskListActivity)activity).hideProgressDialog();
//                } else if (activity instanceof TaskListActivity) {
//                    ((TaskListActivity)activity).hideProgressDialog();
//                }
//
//                Log.e(activity.getClass().getSimpleName(), "Error while creating a board.", (Throwable)e);
//            }
//        }));
//    }

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
