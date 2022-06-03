package com.example.rent.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rent.alterHome.AlterHomeActivity;
import com.example.rent.R;
import com.example.rent.comment.CommentActivity;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.reservation.ReservationActivity;
import com.example.rent.user.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements ISelectTaskOneHome{

    private TextView nameHome ,description, note, commentaire, localisation, prix, conf_maison;
    private ImageView image;
    private Button alter, reserve, remove;

    ProgressDialog progressDialog;
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        this.image = findViewById(R.id.home_img);
        this.nameHome = findViewById(R.id.name);
        this.description = findViewById(R.id.description);
        this.note = findViewById(R.id.biens_note);
        this.localisation = findViewById(R.id.localisation);
        this.prix = findViewById(R.id.prix);
        this.alter = findViewById(R.id.alter);
        this.reserve = findViewById(R.id.reserver);
        this.remove = findViewById(R.id.remove);
        this.conf_maison = findViewById(R.id.conf_maison);
        this.commentaire = findViewById(R.id.biens_commentaire);

        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        int userId = sharedPreferences.getInt("USER", 0);
        String idHome = getIntent().getStringExtra("id"); //Get information from the first page


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent home = new Intent(HomeActivity.this, UserActivity.class);
                        startActivity(home);
                    case R.id.destination:
                    case R.id.message:
                    case R.id.rent:
                        Toast.makeText(HomeActivity.this, "La page n'existe pas encore", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.user:
                        Intent user = new Intent(HomeActivity.this, UserActivity.class);
                        startActivity(user);
                        return true;

                }
                return false;
            }
        });

        this.alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alter = new Intent(HomeActivity.this, AlterHomeActivity.class);
                alter.putExtra("id", idHome);
                startActivity(alter);
            }
        });

        this.commentaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alter = new Intent(HomeActivity.this, CommentActivity.class);
                alter.putExtra("id", idHome);
                startActivity(alter);
            }
        });


        this.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveHome remove = new RemoveHome(HomeActivity.this, idHome);
                remove.execute();
            }
        });

        this.reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reservation = new Intent(HomeActivity.this, ReservationActivity.class);
                reservation.putExtra("id", idHome);
                startActivity(reservation);
            }
        });

        // Loading page
        // Initialize progress dialog
        progressDialog = new ProgressDialog(HomeActivity.this);
        // Show dialog
        progressDialog.show();
        // Set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        // Set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );


        GetOneHome st = new GetOneHome(HomeActivity.this, idHome);
        st.execute();

        }


    @Override
    public void onTaskCompleteGetOneHome(String content) {
        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER", 0);

        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);

                String info_name = o.getString("name");
                String info_price = o.getString("price");
                String info_localisation = o.getString("localisation");
                String info_short_description = o.getString("short_description");
                String info_main_picture = o.getString("main_picture");
                Integer info_owner = o.getInt("owner");

                //Decode base64 string
                byte[] bytes = Base64.decode(info_main_picture, Base64.DEFAULT);
                // Initialize bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                nameHome.setText(info_name.toUpperCase());
                prix.setText(info_price);
                localisation.setText(info_localisation);
                description.setText(info_short_description);
                image.setImageBitmap(bitmap);

                if (info_owner == userId){
                    alter.setVisibility(View.VISIBLE);
                    remove.setVisibility(View.VISIBLE);
                    conf_maison.setText("(C'est votre biens)");
                }

            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(HomeActivity.this, content + content, Toast.LENGTH_SHORT).show();
        };
        progressDialog.dismiss();
    }

    @Override
    public void onTaskCompleteRemoveHome(String content) {
        Intent alter = new Intent(HomeActivity.this, homepageActivity.class);
        startActivity(alter);
        Toast.makeText(HomeActivity.this, "Le biens est supprimé", Toast.LENGTH_SHORT).show();
    }

}