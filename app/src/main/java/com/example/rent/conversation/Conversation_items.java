package com.example.rent.conversation;

public class Conversation_items {

    private int id;
    private String to, from, text;

    public Conversation_items(int id, String to, String from, String text){
        this.id = id;
        this.to = to;
        this.from = from;
        this.text = text;
    }

    public static void add(Conversation_items comment_items) {
    }

    public int getId(){ return id;}
    public String getTo(){ return to;}
    public String getFrom(){ return from;}
    public String getText(){ return text;}



}
