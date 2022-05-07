package com.example.rent.Inscription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rent.R;
import com.example.rent.homepage.homepageActivity;

public class ConfInscriptionActivity extends AppCompatActivity {

    private Button accueil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_inscription);
        getSupportActionBar().hide();

        this.accueil = findViewById(R.id.btn_accueil);

        this.accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(ConfInscriptionActivity.this, homepageActivity.class);
                startActivity(home);
            }
        });


    }
}