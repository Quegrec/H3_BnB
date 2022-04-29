package com.example.rent;

public class Biens_items {

    private String img;
    private String name;
    private String note;
    private String pays;
    private double prix;

    public Biens_items(String img, String name, String note, String pays, double prix){
        this.img = img;
        this.name = name;
        this.note = note;
        this.pays = pays;
        this.prix = prix;
    }

    public static void add(Biens_items biens_items) {
    }

    public String getName(){ return name;}
    public String getImg(){ return img;}
    public String getNote(){ return note;}
    public String getPays(){ return pays;}
    public double getPrix(){ return prix;}

}
