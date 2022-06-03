package com.example.rent.confReservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rent.Home.HomeActivity;
import com.example.rent.R;
import com.example.rent.comment.CommentActivity;
import com.example.rent.comment.CommentAdapter;
import com.example.rent.comment.Comment_items;
import com.example.rent.comment.GetComment;
import com.example.rent.homepage.BienMaisonAdapter;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.user.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ConfActivity extends AppCompatActivity implements ISelectTaskConfReservation {

    ListView listReservation;

    BottomNavigationView bottomNavigationView;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.listReservation = findViewById(R.id.list_reservation_conf);
        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);

        int user = sharedPreferences.getInt("USER", 0);

        // Loading page
        // Initialize progress dialog
        progressDialog = new ProgressDialog(ConfActivity.this);
        // Show dialog
        progressDialog.show();
        // Set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        // Set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );



        GetConfReservation st = new GetConfReservation(ConfActivity.this, String.valueOf(user));
        st.execute();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent home = new Intent(ConfActivity.this, homepageActivity.class);
                        startActivity(home);
                        return true;
                    case R.id.destination:
                        return true;
                    case R.id.message:
                    case R.id.rent:
                        Toast.makeText(ConfActivity.this, "La page n'existe pas encore", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.user:
                        Intent user = new Intent(ConfActivity.this, UserActivity.class);
                        startActivity(user);
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onTaskCompleteGetConfReservation(String content) {

        List<Reservation_items> reservationItemsList = new ArrayList<>();
        List<Integer> listID = new ArrayList<Integer>();
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
                int logement_id = o.getInt("logement_id");
                String price_owner = o.getString("price_owner");
                listID.add(id);

                reservationItemsList.add(new Reservation_items( date_from, date_to, name, firstname, logement_name, price_owner));

                ListView reservationItems = findViewById(R.id.list_reservation_conf);
                reservationItems.setAdapter(new ReservationAdapter(ConfActivity.this, reservationItemsList));


                listReservation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        popUp(listID.get(position));
                    }
                });

            }

        }catch(Exception e){
            // Reload activity
            finish();
            startActivity(getIntent());
        };

        progressDialog.dismiss();
    }

    @Override
    public void onTaskCompleteConfirmed(String content) {
        // Reload activity
        finish();
        startActivity(getIntent());
    }

    public void popUp(int logement_id){
        new AlertDialog.Builder(ConfActivity.this)
                .setTitle("Confirmer")
                .setMessage("Voulez-vous accepter la demande de réservation ?")
                .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UpdateConfirmation st = new UpdateConfirmation(ConfActivity.this, String.valueOf(logement_id));
                        st.execute();
                    }
                })
                .setNegativeButton("non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Reponse Negative
                    }
                })
                .setCancelable(false)
                .show();
    }
}