package com.example.trello.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Task implements Serializable {
    private String title;
    private String createdBy;
    private String boardname;
    private ArrayList<Card> cards= new ArrayList<Card>();

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Task setCards(ArrayList<Card> cards) {
        this.cards = cards;
        return this;
    }

    public Task(String title, String createdBy, String cards) {
        this.title = title;
        this.createdBy = createdBy;
        this.boardname = cards;
    }

    public Task(String title, String createdBy) {
        this.title = title;
        this.createdBy = createdBy;
    }

    public Task() {
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

    public String getBoardname() {
        return boardname;
    }

    public void setBoardname(String boardname) {
        this.boardname = boardname;
    }
}
