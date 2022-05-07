package com.example.rent;

public class Biens_items {

    private String img, type, description, name, note, voyageur_max, localisation;
    private double id, prix;


    public Biens_items(double id, String img, String note, String type, String name, String description, double prix, String voyageur_max, String localisation){
        this.id = id;
        this.img = img;
        this.note = note;
        this.type = type;
        this.description = description;
        this.name = name;
        this.note = note;
        this.prix = prix;
        this.voyageur_max = voyageur_max;
        this.localisation = localisation;
    }

    public static void add(Biens_items biens_items) {
    }

    public double getId(){ return id;}
    public String getName(){ return name;}
    public String getImg(){ return img;}
    public String getType(){ return type;}
    public String getDescription(){return description;}
    public String getNote(){ return note;}
    public double getPrix(){ return prix;}
    public String getVoyageur(){ return voyageur_max;}
    public String getLocalisation(){ return localisation;}

}
