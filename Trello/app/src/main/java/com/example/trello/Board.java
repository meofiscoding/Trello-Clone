package com.example.trello;

import java.util.ArrayList;

public class Board {
    private String name;
    private String image;
    private String createdby;

    public Board(String name, String mBoardImageURL, String mUserName, ArrayList assignedUsersArrayList) {
        this.name = name;
        this.image = image;
        this.createdby = createdby;
        this.assignedto = assignedto;
    }

    public String getName() {
        return name;
    }

    public Board(String name, String image, String createdby, ArrayList assignedto, String documentId, ArrayList taskList) {
        this.name = name;
        this.image = image;
        this.createdby = createdby;
        this.assignedto = assignedto;
        this.documentId = documentId;
        this.taskList = taskList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public ArrayList getAssignedto() {
        return assignedto;
    }

    public void setAssignedto(ArrayList assignedto) {
        this.assignedto = assignedto;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public ArrayList getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList taskList) {
        this.taskList = taskList;
    }

    private ArrayList assignedto;
    private String documentId;
    private ArrayList taskList;
}
