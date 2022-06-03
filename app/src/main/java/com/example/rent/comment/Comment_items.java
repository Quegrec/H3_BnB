package com.example.rent.comment;

public class Comment_items {

    private String id, logement_id, user_id, comment, user_name, user_firstname;

    public Comment_items(String id, String logement_id, String user_id, String comment, String user_name, String user_firstname){
        this.id = id;
        this.logement_id = logement_id;
        this.user_id = user_id;
        this.comment = comment;
        this.user_name = user_name;
        this.user_firstname = user_firstname;
    }

    public static void add(Comment_items comment_items) {
    }

    public String getId(){ return id;}
    public String getLogement_id(){ return logement_id;}
    public String getUser_id(){ return user_id;}
    public String getComment(){ return comment;}
    public String getUser_name(){ return user_name;}
    public String getUser_firstname(){ return user_firstname;}


}
