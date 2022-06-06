package com.example.rent.conversation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rent.R;
import com.example.rent.addHome.AddHomeActivity;
import com.example.rent.addHome.AddHomeConfActivity;
import com.example.rent.confReservation.ConfActivity;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.message.GetAllMessages;
import com.example.rent.message.MessageActivity;
import com.example.rent.message.MessageAdapter;
import com.example.rent.message.Message_items;
import com.example.rent.user.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationActivity extends AppCompatActivity implements IConversation{

    ListView conversation;
    Button send;
    EditText message;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        getSupportActionBar().hide();

        this.conversation = findViewById(R.id.conversation);
        this.send = findViewById(R.id.btn_send);
        this.message = findViewById(R.id.message);

        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER", 0);

        String send_to = getIntent().getStringExtra("id_send");

        // Loading page
        // Initialize progress dialog
        progressDialog = new ProgressDialog(ConversationActivity.this);
        // Show dialog
        progressDialog.show();
        // Set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        // Set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        GetConversation st = new GetConversation(ConversationActivity.this, userId, Integer.valueOf(send_to));
        st.execute();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content_message = message.getText().toString();




                String url = getResources().getString(R.string.url) + "/sendMessage.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                finish();
                                startActivity(getIntent());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ConversationActivity.this, "Erreur", Toast.LENGTH_LONG).show();

                            }
                        }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("from", String.valueOf(userId));
                        params.put("to", send_to);
                        params.put("message", content_message);

                        return params;
                    }
                };

                if (!content_message.equals("")) {
                    RequestQueue requestQueue = Volley.newRequestQueue(ConversationActivity.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    @Override
    public void onTaskCompleteConversation(String content) {
        List<Conversation_items> conversationItemsList = new ArrayList<>();
        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);

                Integer id = o.getInt("id");
                String message_to = o.getString("to");
                String message_from = o.getString("from");
                String text = o.getString("text");
                conversationItemsList.add(new Conversation_items( id, message_to, message_from, text));

                ListView conversationItems = findViewById(R.id.conversation);
                conversationItems.setAdapter(new ConversationAdapter(ConversationActivity.this, conversationItemsList));

            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(ConversationActivity.this, "ca marche pas" + content, Toast.LENGTH_SHORT).show();
        };
        progressDialog.dismiss();
    }
}