package com.example.rent.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rent.R;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.log.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.logout = findViewById(R.id.logout);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent home = new Intent(UserActivity.this, homepageActivity.class);
                        startActivity(home);
                        return true;
                    case R.id.destination:
                    case R.id.message:
                    case R.id.rent:
                        Toast.makeText(UserActivity.this, "La page n'existe pas encore", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.user:
                        return true;

                }
                return false;
            }
        });

        this.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(UserActivity.this, MainActivity.class);
                startActivity(logout);
            }
        });
    }
}