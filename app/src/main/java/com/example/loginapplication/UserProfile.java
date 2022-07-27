package com.example.loginapplication;

import android.widget.Spinner;

public class UserProfile {
    public String userEmail;
    public String userAddress;
    public String userPhone;
    public String userName;

    public  UserProfile(String email, String address, String phone, String name, String userstatus){

    }

    public UserProfile(String userEmail, String userAddress, String userPhone, String userName) {
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
