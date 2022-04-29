package com.example.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        List<Biens_items> biensItemsList = new ArrayList<>();
        biensItemsList.add(new Biens_items("danemark", "Maison de TOTO", "4", "Danemark", 150));
        biensItemsList.add(new Biens_items("japon1", "Maison a Tokyo", "5", "Japon", 250));
        biensItemsList.add(new Biens_items("japon", "Maison de DADA", "3", "Japon", 50));

        ListView homeItems = findViewById(R.id.homeItems);
        homeItems.setAdapter(new BienMaisonAdapter(this, biensItemsList));
    }

}