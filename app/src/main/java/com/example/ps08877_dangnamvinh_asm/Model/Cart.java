package com.example.ps08877_dangnamvinh_asm.Model;

public class Cart {
    private int id;
    private Card card;
    private String num;

    public Cart() {
    }

    public Cart(int id, Card card, String num) {
        this.id = id;
        this.card = card;
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
