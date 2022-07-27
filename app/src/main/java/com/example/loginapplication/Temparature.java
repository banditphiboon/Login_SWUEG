package com.example.loginapplication;

public class Temparature {
    private  String usertemp;
    private String useremail;

    public Temparature() {

    }
    public Temparature(String usertemp, String useremail) {

        this.usertemp = usertemp;
        this.useremail = useremail;
    }

    public String getUsertemp() {

        return usertemp;
    }
    public  String getUseremail(){
        return  useremail;
    }

    public void setUsertemp(String usertemp) {

        this.usertemp = usertemp;

    }

    public void getUsertemp(String useremail) {
        this.useremail= useremail;
    }
}
