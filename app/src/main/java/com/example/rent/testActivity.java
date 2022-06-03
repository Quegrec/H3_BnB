package com.example.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class testActivity extends AppCompatActivity {

    EditText nameet;
    Button save, show;
    TextView text;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        this.nameet = findViewById(R.id.name);
        this.save = findViewById(R.id.add);
        this.show = findViewById(R.id.show);
        this.text = findViewById(R.id.text);

        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);

        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameet.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("NAME" , name);
                editor.apply();
            }
        });

        this.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int name = sharedPreferences.getInt("USER", 0);
                text.setText(String.valueOf(name));
            }
        });
    }
}