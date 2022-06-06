package com.example.rent.message;

public class Message_items {

    private int id;
    private String name, firstname, lastMessage;

    public Message_items(int id, String name, String firstname, String lastMessage){
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.lastMessage = lastMessage;
    }

    public static void add(Message_items comment_items) {
    }

    public int getId(){ return id;}
    public String getName(){ return name;}
    public String getFirstname(){ return firstname;}
    public String getLastMessage(){ return lastMessage;}



}
