package com.example.rent.addHome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rent.R;
import com.example.rent.Rent.RentActivity;
import com.example.rent.confReservation.ConfActivity;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.message.MessageActivity;
import com.example.rent.user.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddHomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button btnGallery, btDecode, addHome; // btnGallery == btEncode
    ImageView imgGallery;
    String sImage;
    EditText titre, type, adresse, nombreVoyageur, nombreLit, prix, code, description;
    private final int GALLERT_RAQ_CODE = 1000;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);
        getSupportActionBar().hide();

        //Img
        this.imgGallery = findViewById(R.id.home_img);

        // Button
        this.btnGallery = findViewById(R.id.btn_add_img);
        this.addHome = findViewById(R.id.add_home);


        // EditText
        this.titre = findViewById(R.id.titre);
        this.type = findViewById(R.id.type);
        this.adresse = findViewById(R.id.adresse);
        this.nombreVoyageur = findViewById(R.id.nombre_voyageur);
        this.nombreLit = findViewById(R.id.nombre_lit);
        this.prix = findViewById(R.id.prix);
        this.code = findViewById(R.id.code);
        this.description = findViewById(R.id.description);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);

        int owner = sharedPreferences.getInt("USER", 0);



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent home = new Intent(AddHomeActivity.this, homepageActivity.class);
                        startActivity(home);
                        return true;
                    case R.id.destination:
                        Intent res = new Intent(AddHomeActivity.this, ConfActivity.class);
                        startActivity(res);
                        return true;
                    case R.id.message:
                        Intent mess = new Intent(AddHomeActivity.this, MessageActivity.class);
                        startActivity(mess);
                        return true;
                    case R.id.rent:
                        Intent rent = new Intent(AddHomeActivity.this, RentActivity.class);
                        startActivity(rent);
                        return true;
                    case R.id.user:
                        Intent user = new Intent(AddHomeActivity.this, UserActivity.class);
                        startActivity(user);
                        return true;
                }
                return false;
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERT_RAQ_CODE);
                btnGallery.setText("Changer d'image");


            }
        });

        this.addHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Loading page
                // Initialize progress dialog
                progressDialog= new ProgressDialog(AddHomeActivity.this);
                // Show dialog
                progressDialog.show();
                // Set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                // Set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );

                String content_titre = titre.getText().toString();
                String content_type = type.getText().toString();
                String content_adresse = adresse.getText().toString();
                String content_nbr_voyageur = nombreVoyageur.getText().toString();
                String content_nbr_lit = nombreLit.getText().toString();
                String content_prix = prix.getText().toString();
                String content_code = code.getText().toString();
                String content_description = description.getText().toString();



                String url = getResources().getString(R.string.url) + "/newHomeTest.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                Toast.makeText(AddHomeActivity.this, "Le biens est ajouté", Toast.LENGTH_SHORT).show();
                                Intent home = new Intent(AddHomeActivity.this, AddHomeConfActivity.class);
                                startActivity(home);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(AddHomeActivity.this, "Il y a une erreur", Toast.LENGTH_LONG).show();
                                Intent user = new Intent(AddHomeActivity.this, homepageActivity.class);
                                startActivity(user);
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("owner", String.valueOf(owner));
                        params.put("img", sImage);
                        params.put("titre", content_titre);
                        params.put("type", content_type);
                        params.put("adresse", content_adresse);
                        params.put("nbr_voyageur", content_nbr_voyageur);
                        params.put("nbr_lit", content_nbr_lit);
                        params.put("prix", content_prix);
                        params.put("code", content_code);
                        params.put("description", content_description);

                        return params;
                    }
                };

            if ((!content_titre.equals("")) && (!content_type.equals("")) && (!content_adresse.equals("")) && (!content_nbr_voyageur.equals("")) && (!content_nbr_lit.equals(""))
                    && (!content_prix.equals("")) && (!content_code.equals(""))){
                if (!sImage.equals("")){
                    RequestQueue requestQueue = Volley.newRequestQueue(AddHomeActivity.this);
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(AddHomeActivity.this, "Vous devez importer une image", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(AddHomeActivity.this, "Tous les elements ne sont pas renseigné", Toast.LENGTH_SHORT).show();
            }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode==GALLERT_RAQ_CODE){
            // For Gallery
            imgGallery.setImageURI(data.getData());

            // When result is ok
            // Initialize uri
            Uri uri = data.getData();
            try{
                // Inirialise bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                // Initialize bitmap
                byte[] bytes = stream.toByteArray();
                // Get base64 encoded string
                this.sImage = Base64.encodeToString(bytes, Base64.DEFAULT);
            }catch( IOException e){
                e.printStackTrace();
            }

        }
    }
}