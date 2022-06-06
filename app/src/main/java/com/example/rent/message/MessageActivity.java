package com.example.rent.message;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rent.Rent.RentActivity;
import com.example.rent.addHome.AddHomeActivity;
import com.example.rent.conversation.ConversationActivity;
import com.example.rent.R;
import com.example.rent.confReservation.ConfActivity;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.user.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements IMessages{

    ListView allMessages;

    BottomNavigationView bottomNavigationView;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getSupportActionBar().hide();

        this.allMessages = findViewById(R.id.all_messages);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER", 0);




        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent home = new Intent(MessageActivity.this, homepageActivity.class);
                        startActivity(home);
                        return true;
                    case R.id.destination:
                        Intent res = new Intent(MessageActivity.this, ConfActivity.class);
                        startActivity(res);
                        return true;
                    case R.id.message:
                        Intent mess = new Intent(MessageActivity.this, MessageActivity.class);
                        startActivity(mess);
                        return true;
                    case R.id.rent:
                        Intent rent = new Intent(MessageActivity.this, RentActivity.class);
                        startActivity(rent);
                        return true;
                    case R.id.user:
                        Intent user = new Intent(MessageActivity.this, UserActivity.class);
                        startActivity(user);
                        return true;
                }
                return false;
            }
        });

        // Loading page
        // Initialize progress dialog
        progressDialog = new ProgressDialog(MessageActivity.this);
        // Show dialog
        progressDialog.show();
        // Set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        // Set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        GetAllMessages st = new GetAllMessages(MessageActivity.this, userId);
        st.execute();

    }

    @Override
    public void onTaskCompleteAllMessages(String content) {
        List<Message_items> messageItemsList = new ArrayList<>();
        List<Integer> listID = new ArrayList<Integer>();
        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);

                sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);
                int userId = sharedPreferences.getInt("USER", 0);

                Integer id = o.getInt("id");
                Integer message_to = o.getInt("to");
                String message_from = o.getString("from");
                String text = o.getString("text");
                String name = o.getString("name");
                String firstname = o.getString("firstname");
                listID.add(message_to);

                messageItemsList.add(new Message_items( id, name, firstname, text));

                ListView messageItems = findViewById(R.id.all_messages);
                messageItems.setAdapter(new MessageAdapter(MessageActivity.this, messageItemsList));

                allMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent in = new Intent(MessageActivity.this, ConversationActivity.class);
                        in.putExtra("id_send", String.valueOf(listID.get(position)));
                        startActivity(in);
                    }
                });
            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(MessageActivity.this, "ca marche pas" + content, Toast.LENGTH_SHORT).show();
        };
        progressDialog.dismiss();
    }
}