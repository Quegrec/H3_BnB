package com.example.rent.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rent.Rent.RentActivity;
import com.example.rent.confReservation.ConfActivity;
import com.example.rent.Home.HomeActivity;
import com.example.rent.R;
import com.example.rent.log.MainActivity;
import com.example.rent.message.MessageActivity;
import com.example.rent.user.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class homepageActivity extends AppCompatActivity implements IHomepage {

    EditText search;
    ListView listHome;
    ImageView search_btn;

    BottomNavigationView bottomNavigationView;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.search = findViewById(R.id.search);
        this.listHome = findViewById(R.id.homeItems);
        this.search_btn = findViewById(R.id.search_btn);

        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER", 0);

        // If the user is not loged
        // redirect him to the login page
        if (userId == 0){
            Intent log = new Intent(homepageActivity.this, MainActivity.class);
            startActivity(log);
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.destination:
                        Intent res = new Intent(homepageActivity.this, ConfActivity.class);
                        startActivity(res);
                        return true;
                    case R.id.message:
                        Intent mess = new Intent(homepageActivity.this, MessageActivity.class);
                        startActivity(mess);
                        return true;
                    case R.id.rent:
                        Intent rent = new Intent(homepageActivity.this, RentActivity.class);
                        startActivity(rent);
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

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                if (String.valueOf(search.getText()).equals("")){
                    listHome.setAdapter(null);
                    GetHome st = new GetHome(homepageActivity.this);
                    st.execute();
                }else{
                    listHome.setAdapter(null);
                    GetHomeResearch st = new GetHomeResearch(homepageActivity.this, String.valueOf(search.getText()));
                    st.execute();
                }


            }
        });


        GetHome st = new GetHome(homepageActivity.this);
        st.execute();

        //progressDialog.dismiss();
    }

    // ListView
    @Override
    public void onTaskCompleteHome(String content){
        List<Biens_items> biensItemsList = new ArrayList<>();
        List<Integer> listID = new ArrayList<Integer>();
        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);

                Integer idHome = o.getInt("id");
                String img = o.getString("main_picture");
                String name = o.getString("name");
                String type = o.getString("type");
                String voyageur_max = o.getString("voyageur_max");
                String note = o.getString("note");
                String localisation = o.getString("localisation");
                String short_description = o.getString("short_description");
                Integer price = o.getInt("price");
                listID.add(idHome);

                biensItemsList.add(new Biens_items( idHome, img,  note,  type,  name,  short_description, price , voyageur_max, localisation));

                ListView homeItems = findViewById(R.id.homeItems);
                homeItems.setAdapter(new BienMaisonAdapter(homepageActivity.this, biensItemsList));

                listHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent in = new Intent(homepageActivity.this, HomeActivity.class);
                        in.putExtra("id", String.valueOf(listID.get(position)));
                        startActivity(in);
                    }
                });
            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(homepageActivity.this, "ca marche pas" + content, Toast.LENGTH_SHORT).show();
        };
    progressDialog.dismiss();
    }

    @Override
    public void onTaskCompleteResearch(String content) {
        List<Biens_items> biensItemsList = new ArrayList<>();
        List<Integer> listID = new ArrayList<Integer>();
        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);

                Integer idHome = o.getInt("id");
                String img = o.getString("main_picture");
                String name = o.getString("name");
                String type = o.getString("type");
                String voyageur_max = o.getString("voyageur_max");
                String note = o.getString("note");
                String localisation = o.getString("localisation");
                String short_description = o.getString("short_description");
                Integer price = o.getInt("price");
                listID.add(idHome);

                //Toast.makeText(homepageActivity.this, img+  note+ type+  name+  short_description+ 120 + voyageur_max+ localisation, Toast.LENGTH_SHORT).show();

                biensItemsList.add(new Biens_items( idHome, img,  note,  type,  name,  short_description, price , voyageur_max, localisation));

                ListView homeItems = findViewById(R.id.homeItems);
                homeItems.setAdapter(new BienMaisonAdapter(homepageActivity.this, biensItemsList));

                listHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent in = new Intent(homepageActivity.this, HomeActivity.class);
                        in.putExtra("id", String.valueOf(listID.get(position)));
                        startActivity(in);
                    }
                });
            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(homepageActivity.this, "ca marche pas pour la recherche" + content, Toast.LENGTH_SHORT).show();
        };
        progressDialog.dismiss();
    }
}