package com.example.rent.comment;

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
import com.example.rent.Home.HomeActivity;
import com.example.rent.R;
import com.example.rent.Rent.RentActivity;
import com.example.rent.addHome.AddHomeActivity;
import com.example.rent.confReservation.ConfActivity;
import com.example.rent.conversation.ConversationActivity;
import com.example.rent.homepage.BienMaisonAdapter;
import com.example.rent.homepage.Biens_items;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.message.MessageActivity;
import com.example.rent.user.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity implements IComment {
    
    ListView listComment;
    Button btnSend;
    EditText newComment;

    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.listComment = findViewById(R.id.list_comment);
        this.newComment = findViewById(R.id.text_comment);
        this.btnSend = findViewById(R.id.btn_send);

        String homeID = getIntent().getStringExtra("id");
        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER", 0);

        GetComment st = new GetComment(CommentActivity.this, homeID);
        st.execute();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent home = new Intent(CommentActivity.this, homepageActivity.class);
                        startActivity(home);
                        return true;
                    case R.id.destination:
                        Intent res = new Intent(CommentActivity.this, ConfActivity.class);
                        startActivity(res);
                        return true;
                    case R.id.message:
                        Intent mess = new Intent(CommentActivity.this, MessageActivity.class);
                        startActivity(mess);
                        return true;
                    case R.id.rent:
                        Intent rent = new Intent(CommentActivity.this, RentActivity.class);
                        startActivity(rent);
                        return true;
                    case R.id.user:
                        Intent user = new Intent(CommentActivity.this, UserActivity.class);
                        startActivity(user);
                        return true;
                }
                return false;
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content_comment = newComment.getText().toString();


                String url = getResources().getString(R.string.url) + "/newcomment.php";
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
                                Toast.makeText(CommentActivity.this, "Erreur", Toast.LENGTH_LONG).show();

                            }
                        }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", String.valueOf(userId));
                        params.put("logement_id", homeID);
                        params.put("comment", content_comment);

                        return params;
                    }
                };

                if (!content_comment.equals("")) {
                    RequestQueue requestQueue = Volley.newRequestQueue(CommentActivity.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
        
        
        
        
    }

    @Override
    public void onTaskCompleteGetComment(String content) {
        List<Comment_items> commentItemsList = new ArrayList<>();
        List<Integer> listID = new ArrayList<Integer>();
        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);

                String id = o.getString("id");
                String logement_id = o.getString("logement_id");
                String user_id = o.getString("user_id");
                String comment = o.getString("comment");
                String user_name = o.getString("name");
                String user_firstname = o.getString("firstname");
                commentItemsList.add(new Comment_items( id, logement_id, user_id, comment, user_name, user_firstname));

                ListView commentItems = findViewById(R.id.list_comment);
                commentItems.setAdapter(new CommentAdapter(CommentActivity.this, commentItemsList));

            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(CommentActivity.this, "ca marche pas" + content, Toast.LENGTH_SHORT).show();
        };
    }
}