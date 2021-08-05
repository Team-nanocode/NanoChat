package com.pdn.eng.cipher.nanochat;

public class UserProfile {
    public String userName;
    public String userUID;

    public UserProfile(String userName, String userUID) {
        this.userName = userName;
        this.userUID = userUID;
    }

    public UserProfile() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
