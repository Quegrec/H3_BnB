package com.example.rent.confReservation;

public class Reservation_items {

    private String date_from, date_to, user_name, user_firstname, logement_name, price;

    public Reservation_items(String date_from, String date_to, String user_name, String user_firstname, String logement_name, String price){
        this.date_from = date_from;
        this.date_to = date_to;
        this.user_name = user_name;
        this.user_firstname = user_firstname;
        this.logement_name = logement_name;
        this.price = price;
    }

    public static void add(Reservation_items comment_items) {
    }

    public String getDate_from(){ return date_from;}
    public String getDate_to(){ return date_to;}
    public String getUser_name(){ return user_name;}
    public String getUser_firstname(){ return user_firstname;}
    public String getLogement_name(){ return logement_name;}
    public String getPrice(){ return price;}


}
