package com.example.rent.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rent.Rent.RentActivity;
import com.example.rent.confReservation.ConfActivity;
import com.example.rent.message.MessageActivity;
import com.example.rent.money.CashActivity;
import com.example.rent.R;
import com.example.rent.addHome.AddHomeActivity;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.log.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity implements ISelectTaskUser {

    BottomNavigationView bottomNavigationView;
    private Button logout, addHome, money;
    TextView userName;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.logout = findViewById(R.id.logout);
        this.addHome = findViewById(R.id.add_new_home);
        this.money = findViewById(R.id.money);
        this.userName = findViewById(R.id.name);

        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);

        int userId = sharedPreferences.getInt("USER", 0);

        // If the user is not loged
        // redirect him to the login page
        if (userId == 0){
            Intent log = new Intent(UserActivity.this, MainActivity.class);
            startActivity(log);
        }

        GetUser user = new GetUser(UserActivity.this, userId);
        user.execute();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent home = new Intent(UserActivity.this, homepageActivity.class);
                        startActivity(home);
                        return true;
                    case R.id.destination:
                        Intent res = new Intent(UserActivity.this, ConfActivity.class);
                        startActivity(res);
                        return true;
                    case R.id.message:
                        Intent mess = new Intent(UserActivity.this, MessageActivity.class);
                        startActivity(mess);
                        return true;
                    case R.id.rent:
                        Intent rent = new Intent(UserActivity.this, RentActivity.class);
                        startActivity(rent);
                        return true;
                    case R.id.user:
                        Intent user = new Intent(UserActivity.this, UserActivity.class);
                        startActivity(user);
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

        this.addHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newHome = new Intent(UserActivity.this, AddHomeActivity.class);
                startActivity(newHome);
            }
        });

        this.money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent money = new Intent(UserActivity.this, CashActivity.class);
                startActivity(money);
            }
        });
    }

    @Override
    public void onTaskCompleteGetUser(String content) {
        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);

                String name = o.getString("name");
                String firstname = o.getString("firstname");

                userName.setText(name.toUpperCase() + " " + firstname.toUpperCase());


            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(UserActivity.this, "Can't get User name" + content, Toast.LENGTH_SHORT).show();
        };

    }
}