package com.example.rent.comment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rent.Home.HomeActivity;
import com.example.rent.R;
import com.example.rent.homepage.BienMaisonAdapter;
import com.example.rent.homepage.Biens_items;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.user.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements IComment {
    
    ListView listComment;

    BottomNavigationView bottomNavigationView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.listComment = findViewById(R.id.list_comment);

        String homeID = getIntent().getStringExtra("id");

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
                    case R.id.message:
                    case R.id.rent:
                        Toast.makeText(CommentActivity.this, "La page n'existe pas encore", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.user:
                        Intent user = new Intent(CommentActivity.this, UserActivity.class);
                        startActivity(user);
                        return true;

                }
                return false;
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