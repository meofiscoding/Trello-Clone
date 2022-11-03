package com.example.trello.model;

import java.util.ArrayList;

public class Board {
    private String name;
    private String image;
    private String createdby;
    private ArrayList<String> assignedto;
    private String documentId;
    private ArrayList taskList;

    public Board() {
    }

    public Board(String name, String image, String createdby, ArrayList<String> assignedUsersArrayList) {
        this.name = name;
        this.image = image;
        this.createdby = createdby;
        this.assignedto = assignedUsersArrayList;
    }

    public Board(String name, String image, String createdby, ArrayList<String> assignedto, String documentId, ArrayList taskList) {
        this.name = name;
        this.image = image;
        this.createdby = createdby;
        this.assignedto = assignedto;
        this.documentId = documentId;
        this.taskList = taskList;
    }

    public String getName() {
        return name;
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

    public ArrayList<String> getAssignedto() {
        return assignedto;
    }

    public void setAssignedto(ArrayList<String> assignedto) {
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


}
