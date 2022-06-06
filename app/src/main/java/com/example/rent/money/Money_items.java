package com.example.rent.money;

public class Money_items {

    private String price, name;

    public Money_items(String price, String name){
        this.price = price;
        this.name = name;
    }

    public static void add(Money_items money_items) {
    }

    public String getName(){ return name;}
    public String getPrice(){ return price;}



}
