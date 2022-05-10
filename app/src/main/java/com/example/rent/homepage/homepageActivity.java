package com.example.rent.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rent.Biens_items;
import com.example.rent.R;
import com.example.rent.addHome.AddHomeActivity;
import com.example.rent.user.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class homepageActivity extends AppCompatActivity implements IHomepage {

    BottomNavigationView bottomNavigationView;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.destination:
                    case R.id.message:
                    case R.id.rent:
                        Toast.makeText(homepageActivity.this, "La page n'existe pas encore", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.user:
                        Intent user = new Intent(homepageActivity.this, UserActivity.class);
                        startActivity(user);
                        return true;

                }
                return false;
            }
        });

        // Loading page
        // Initialize progress dialog
        progressDialog = new ProgressDialog(homepageActivity.this);
        // Show dialog
        progressDialog.show();
        // Set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        // Set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        GetHome st = new GetHome(homepageActivity.this);
        st.execute();

        //progressDialog.dismiss();
    }

    // ListView
    @Override
    public void onTaskCompleteHome(String content){
        List<Biens_items> biensItemsList = new ArrayList<>();
        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);

                Integer id = o.getInt("id");
                String img = o.getString("main_picture");
                String name = o.getString("name");
                String type = o.getString("type");
                String voyageur_max = o.getString("voyageur_max");
                String note = o.getString("note");
                String localisation = o.getString("localisation");
                String short_description = o.getString("short_description");
                Integer price = o.getInt("price");

                //Toast.makeText(homepageActivity.this, img+  note+ type+  name+  short_description+ 120 + voyageur_max+ localisation, Toast.LENGTH_SHORT).show();

                biensItemsList.add(new Biens_items( id, img,  note,  type,  name,  short_description, price , voyageur_max, localisation));

                ListView homeItems = findViewById(R.id.homeItems);
                homeItems.setAdapter(new BienMaisonAdapter(homepageActivity.this, biensItemsList));
            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(homepageActivity.this, "ca marche pas" + content, Toast.LENGTH_SHORT).show();
        };
    progressDialog.dismiss();
    }
}