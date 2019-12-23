package com.geo.messanger.model;

import java.util.HashMap;

public class User {

    private String mId;
    private String mUsername;
    private String mImageURL;

    public User(String mId, String mUsername, String mImageURL) {
        this.mId = mId;
        this.mUsername = mUsername;
        this.mImageURL = mImageURL;
    }

    public User(){

    }

    public User(HashMap<String, String> hashMap) {
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }
}
