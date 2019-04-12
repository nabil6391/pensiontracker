package com.rootsoftit.pensiontracker.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @Expose
    @SerializedName("username")
    private String Name = "";
    @Expose
    @SerializedName("email")
    private String Email = "";
    @Expose
    @SerializedName("password")
    private String password = "";


    @SerializedName("image")
    private String image;

    @SerializedName("id")
    private int id;

    @SerializedName("confirm_password")
    private String confirmPassword;

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {

        this.password = pass;
    }


    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
