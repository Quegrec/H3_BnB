package com.example.rent.Rent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rent.R;
import com.example.rent.addHome.AddHomeActivity;
import com.example.rent.confReservation.ConfActivity;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.message.MessageActivity;
import com.example.rent.user.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RentActivity extends AppCompatActivity implements ISelectTaskRent {

    ListView listReservation;

    BottomNavigationView bottomNavigationView;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        getSupportActionBar().hide();

        this.listReservation = findViewById(R.id.list_reservation);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);
        int user = sharedPreferences.getInt("USER", 0);

        // Loading page
        // Initialize progress dialog
        progressDialog = new ProgressDialog(RentActivity.this);
        // Show dialog
        progressDialog.show();
        // Set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        // Set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent home = new Intent(RentActivity.this, homepageActivity.class);
                        startActivity(home);
                        return true;
                    case R.id.destination:
                        Intent res = new Intent(RentActivity.this, ConfActivity.class);
                        startActivity(res);
                        return true;
                    case R.id.message:
                        Intent mess = new Intent(RentActivity.this, MessageActivity.class);
                        startActivity(mess);
                        return true;
                    case R.id.rent:
                        Intent rent = new Intent(RentActivity.this, RentActivity.class);
                        startActivity(rent);
                        return true;
                    case R.id.user:
                        Intent user = new Intent(RentActivity.this, UserActivity.class);
                        startActivity(user);
                        return true;
                }
                return false;
            }
        });

        GetRent st = new GetRent(RentActivity.this, String.valueOf(user));
        st.execute();


    }

    @Override
    public void onTaskCompleteRent(String content) {
        List<Rent_items> rentItemsList = new ArrayList<>();
        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);

                int id = o.getInt("id");
                String date_from = o.getString("date_from");
                String date_to = o.getString("date_to");
                String name = o.getString("name");
                String firstname = o.getString("firstname");
                String logement_name = o.getString("logement_name");
                String price_owner = o.getString("price_owner");

                rentItemsList.add(new Rent_items( date_from, date_to, name, firstname, logement_name, price_owner));

                ListView rentItems = findViewById(R.id.list_reservation);
                rentItems.setAdapter(new RentAdapter(RentActivity.this, rentItemsList));

            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(RentActivity.this, "il y a un pb", Toast.LENGTH_SHORT).show();
        };
        progressDialog.dismiss();
    }
}