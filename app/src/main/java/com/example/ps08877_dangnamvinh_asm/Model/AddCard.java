package com.example.ps08877_dangnamvinh_asm.Model;

public class AddCard {
    public int _id;
    public String idsanpham, name, description, image, price,num,idtheloai;

    public AddCard(String idsanpham, String name, String description, String image, String price, String num, String idtheloai) {
        this.idsanpham = idsanpham;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.num = num;
        this.idtheloai = idtheloai;
    }

    public AddCard(int _id, String idsanpham, String name, String description, String image, String price, String num, String idtheloai) {
        this._id = _id;
        this.idsanpham = idsanpham;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.num = num;
        this.idtheloai = idtheloai;
    }
}
