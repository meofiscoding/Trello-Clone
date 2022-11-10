package com.example.trello.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

public class User implements Parcelable {
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


    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        image = in.readString();
        mobile = in.readString();
        fcmToken = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(image);
        dest.writeString(mobile);
        dest.writeString(fcmToken);
        dest.writeByte((byte) (selected ? 1 : 0));
    }
}
