package com.example.ps08877_dangnamvinh_asm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserList {

    private Boolean status;

    @SerializedName("data")
    private List<User> usersList;

    public Boolean getStatus() {
        return status;
    }
    public List<User> getUsersList() {
        return usersList;
    }
}
