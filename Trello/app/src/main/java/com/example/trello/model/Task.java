package com.example.trello.model;

import java.util.ArrayList;

public class Task {
    private String title;
    private String createdBy;
    private ArrayList<Card> cards;

    public Task(String title, String createdBy, ArrayList<Card> cards) {
        this.title = title;
        this.createdBy = createdBy;
        this.cards = cards;
    }

    public Task(String title, String createdBy) {
        this.title = title;
        this.createdBy = createdBy;
    }

    public Task(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
