package com.example.ps08877_dangnamvinh_asm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardList {
    private Boolean status;

    @SerializedName("data")
    private List<Card> cardsList;

    public Boolean getStatus() {
        return status;
    }

    public List<Card> getCardsList() {
        return cardsList;
    }
}
