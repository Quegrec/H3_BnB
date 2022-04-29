package com.example.rent.Inscription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rent.homepage;
import com.example.rent.log.ISelectTaskLog;
import com.example.rent.R;
import com.example.rent.log.MainActivity;

public class InscriptionActivity extends AppCompatActivity implements ISelectTaskInscription {

    private Button inscription;
    private EditText name, firstname, mail, password, conf_password, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        getSupportActionBar().hide();

        this.inscription = findViewById(R.id.inscription);
        this.name = findViewById(R.id.name);
        this.firstname = findViewById(R.id.firstname);
        this.mail = findViewById(R.id.mail_log);
        this.password = findViewById(R.id.password_log_one);
        this.conf_password = findViewById(R.id.password_log_two);
        this.phone = findViewById(R.id.phone_number);

        this.inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content_mail = mail.getText().toString();
                String content_password = password.getText().toString();
                String content_conf_password = conf_password.getText().toString();
                String content_phone = phone.getText().toString();
                String content_name = name.getText().toString();
                String content_firstname = firstname.getText().toString();

                // If the element aren't empty
                if ((!content_mail.equals("")) && (!content_password.equals("")) && (!content_phone.equals("")) && (!content_name.equals("")) && (!content_firstname.equals(""))){
                    if (content_mail.contains("@") && (content_mail.contains(".com")) || (content_mail.contains(".fr"))){
                        if (content_password.equals(content_conf_password)){
                            SendUser su = new SendUser(InscriptionActivity.this, content_name, content_firstname, content_mail, content_password, content_phone);
                            su.execute();

                        }else{
                            Toast.makeText(InscriptionActivity.this, "Le mot de pass n'est pas correct", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(InscriptionActivity.this, "Le mail n'est pas corret", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(InscriptionActivity.this, "Tous les elements ne sont pas renseigné", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onTaskCompleteLog(String content) {
        // Task for registration
        Toast.makeText(InscriptionActivity.this, "Utilisateur sauvegardé", Toast.LENGTH_SHORT).show();
        Intent log = new Intent(InscriptionActivity.this, MainActivity.class);
        startActivity(log);
    }
}