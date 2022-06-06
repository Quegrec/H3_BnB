
package com.example.rent.log;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rent.reservation.ReservationActivity;
import com.example.rent.Inscription.InscriptionActivity;
import com.example.rent.R;
import com.example.rent.homepage.homepageActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ISelectTaskLog {

    private Button login;
    private EditText mail, password;
    private TextView SignUp;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        this.login = findViewById(R.id.signin);
        this.mail = findViewById(R.id.mail);
        this.password = findViewById(R.id.password);
        this.SignUp = findViewById(R.id.SignUp);

        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER" , 0);
        editor.apply();


        this.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get email and password
                String content_mail = mail.getText().toString();
                String content_password = password.getText().toString();

                // If its not empty
                if ((!content_mail.equals("")) && (!content_password.equals(""))){
                    // Do the async task
                    GetLog st = new GetLog(MainActivity.this, content_mail, content_password);
                    st.execute();
                }else{
                    Toast.makeText(MainActivity.this, "Il faut indiquer sont mail et mot de passe", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registration = new Intent(MainActivity.this, InscriptionActivity.class);
                startActivity(registration);
            }
        });
    }

    @Override
    public void onTaskCompleteLogin(String content) {
        // Processe at the end of the async task login
        if ((!content.equals("False")) && (Integer.valueOf(content) > 0) ){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("USER" , Integer.valueOf(content));
            editor.apply();
            Intent home = new Intent(MainActivity.this, homepageActivity.class);
            startActivity(home);
        }else{
            Toast.makeText(MainActivity.this, "Le mot de passe ou le mail est incorrect", Toast.LENGTH_LONG).show();
            password.setText("");
        }
    }

    @Override
    public void onTaskComplete(String content) {
        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);
                String name = o.getString("name");
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(MainActivity.this, "Contenu = " + content, Toast.LENGTH_SHORT).show();
        };
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        popUp("Voulez-vous quitter ?");

    }
    public void popUp(String message){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Quitter")
                .setMessage(message)
                .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Response possitive
                        finish();
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