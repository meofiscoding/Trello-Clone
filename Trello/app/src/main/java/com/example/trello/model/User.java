package com.example.trello.model;

import org.jetbrains.annotations.NotNull;

public class User {
    @NotNull
    private  String id;
    @NotNull
    private  String name;
    @NotNull
    private  String email;
    @NotNull
    private  String image;
    private  String mobile;
   
    private  String fcmToken;
    private boolean selected;

    public User(@NotNull String id, @NotNull String name, @NotNull String email, @NotNull String image, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
        this.mobile = mobile;
    }

    public User() {
    }


    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    @NotNull
    public String getImage() {
        return image;
    }

    public void setImage(@NotNull String image) {
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @NotNull
    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(@NotNull String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
