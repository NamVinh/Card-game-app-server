package com.example.ps08877_dangnamvinh_asm.Model;

import com.google.gson.annotations.SerializedName;

public class Register {
    private Boolean status;
    @SerializedName("data")
    private User user;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }
}
