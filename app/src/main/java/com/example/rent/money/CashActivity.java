package com.example.rent.money;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rent.R;
import com.example.rent.Rent.RentActivity;
import com.example.rent.addHome.AddHomeActivity;
import com.example.rent.comment.CommentActivity;
import com.example.rent.comment.CommentAdapter;
import com.example.rent.comment.Comment_items;
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

public class CashActivity extends AppCompatActivity implements IMoney{

    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent home = new Intent(CashActivity.this, homepageActivity.class);
                        startActivity(home);
                        return true;
                    case R.id.destination:
                        Intent res = new Intent(CashActivity.this, ConfActivity.class);
                        startActivity(res);
                        return true;
                    case R.id.message:
                        Intent mess = new Intent(CashActivity.this, MessageActivity.class);
                        startActivity(mess);
                        return true;
                    case R.id.rent:
                        Intent rent = new Intent(CashActivity.this, RentActivity.class);
                        startActivity(rent);
                        return true;
                    case R.id.user:
                        Intent user = new Intent(CashActivity.this, UserActivity.class);
                        startActivity(user);
                        return true;
                }
                return false;
            }
        });

        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);

        int userId = sharedPreferences.getInt("USER", 0);

        GetMoney money = new GetMoney(CashActivity.this, userId);
        money.execute();

    }

    @Override
    public void onTaskCompleteGetMoney(String content) {
        List<Money_items> moneyItemsList = new ArrayList<>();
        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);

                String name = o.getString("name");
                String price = o.getString("price_owner");

                moneyItemsList.add(new Money_items(price, name));

                ListView moneyItems = findViewById(R.id.list_money);
                moneyItems.setAdapter(new MoneyAdapter(CashActivity.this, moneyItemsList));

            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(CashActivity.this, "Ca marche pas pour le l'affichage de l'argent" + content, Toast.LENGTH_SHORT).show();
        };
    }
}