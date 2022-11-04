package com.example.trello.model;

import java.util.ArrayList;

public class Card {
    private String name;
    private String createdBy;
    private ArrayList<String> assignedTo;
    private String labelColor;
    private Long dueDate;
    private String taskname;

    public Card(String name, String createdBy, ArrayList<String> assignedTo, String labelColor, Long dueDate) {
        this.name = name;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.labelColor = labelColor;
        this.dueDate = dueDate;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public Card(String name, String taskname) {
        this.name = name;
        this.taskname = taskname;
    }

    public Card(String cardName, String currentUserID, ArrayList<String> cardAssignedUsersList, String name) {
        this.name = cardName;
        this.createdBy = currentUserID;
        this.assignedTo = cardAssignedUsersList;
        this.taskname =name;
    }

    public Card(String name, String createdBy, ArrayList<String> assignedTo, String labelColor, Long dueDate, String taskname) {
        this.name = name;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.labelColor = labelColor;
        this.dueDate = dueDate;
        this.taskname = taskname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ArrayList<String> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(ArrayList<String> assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }
}
