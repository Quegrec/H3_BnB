package com.example.rent.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.rent.Home.HomeActivity;
import com.example.rent.R;
import com.example.rent.addHome.AddHomeActivity;
import com.example.rent.addHome.AddHomeConfActivity;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.log.GetLog;
import com.example.rent.log.MainActivity;
import com.example.rent.user.UserActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservationActivity extends AppCompatActivity implements ISelectTaskReservation {
    private Button reservation, getPrice;
    private TextView name, note, type, prix, info_nbr_voyageur, final_price;
    private EditText nombreVoyageur;
    private DatePicker dateFrom, dateTo;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        getSupportActionBar().hide();

        //Button
        this.reservation = findViewById(R.id.btn_reservation);
        this.getPrice = findViewById(R.id.get_price);

        //TextView
        this.name = findViewById(R.id.biens_name);
        this.note = findViewById(R.id.biens_note);
        this.type = findViewById(R.id.biens_type);
        this.prix = findViewById(R.id.prix);
        this.info_nbr_voyageur = findViewById(R.id.info_nbr_max);
        this.final_price = findViewById(R.id.final_price);

        // EditText
        this.nombreVoyageur = findViewById(R.id.nombre_voyageur);

        // DatePicker
        this.dateFrom = findViewById(R.id.date_from);
        this.dateTo = findViewById(R.id.date_to);

        sharedPreferences = getSharedPreferences("LOG", MODE_PRIVATE);

        // Get informations form the last activity
        String homeID = getIntent().getStringExtra("id");
        int user = sharedPreferences.getInt("USER", 0);


        GetForReservation st = new GetForReservation(ReservationActivity.this, homeID);
        st.execute();

        this.getPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nbr_voyageur = Integer.valueOf(nombreVoyageur.getText().toString());
                int priceNight = Integer.valueOf(prix.getText().toString());

                Date reservationFrom = getDateFromDatePicker(dateFrom);
                Date reservationTo = getDateFromDatePicker(dateTo);

                long diff = reservationTo.getTime() - reservationFrom.getTime()  ;

                float frais = nbr_voyageur*3*(diff / (1000*60*60*24));
                float forOwner = (diff / (1000*60*60*24))*priceNight;

                float finalPrice = frais + forOwner;

                final_price.setText(String.valueOf(finalPrice));
            }
        });

        this.reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Loading page
                // Initialize progress dialog
                progressDialog= new ProgressDialog(ReservationActivity.this);
                // Show dialog
                progressDialog.show();
                // Set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                // Set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );


                String nbr_voyageur = info_nbr_voyageur.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                Date reservationFrom = getDateFromDatePicker(dateFrom);
                Date reservationTo = getDateFromDatePicker(dateTo);

                int nbr_voyageur_final = Integer.valueOf(nombreVoyageur.getText().toString());
                if (nbr_voyageur_final == 0){
                    nombreVoyageur.setText("1");
                    nbr_voyageur_final = Integer.valueOf(nombreVoyageur.getText().toString());
                }
                int priceNight = Integer.valueOf(prix.getText().toString());

                long diff = reservationTo.getTime() - reservationFrom.getTime()  ;

                float frais = nbr_voyageur_final * 3 * (diff / (1000*60*60*24));
                float forOwner = (diff / (1000*60*60*24))*priceNight;

                float finalPrice = frais + forOwner;



                String url = getResources().getString(R.string.url) + "/newReservation.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                Toast.makeText(ReservationActivity.this, "Il y a eu un probleme pour la reservation", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(ReservationActivity.this, "Le biens est ajouté", Toast.LENGTH_LONG).show();
                                // redirect to reservation page
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", String.valueOf(user));
                        params.put("logement_id", homeID);
                        params.put("date_from", String.valueOf(reservationFrom));
                        params.put("date_to", String.valueOf(reservationTo));
                        params.put("confirmed", "0");
                        params.put("price_owner", String.valueOf(forOwner));
                        params.put("frais", String.valueOf(frais));

                        return params;
                    }
                };
                
                if ((!(nombreVoyageur.getText().toString()).equals("")) && (Integer.valueOf(nombreVoyageur.getText().toString()) <= Integer.valueOf(nbr_voyageur))){
                    if ((reservationFrom.after(currentTime)) && reservationTo.after(reservationFrom)){
                        Toast.makeText(ReservationActivity.this, reservationFrom.toString(), Toast.LENGTH_SHORT).show();
                        RequestQueue requestQueue = Volley.newRequestQueue(ReservationActivity.this);
                        requestQueue.add(stringRequest);


                    }else{
                        Toast.makeText(ReservationActivity.this, "Les dates ne sont pas correct", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }else {
                    Toast.makeText(ReservationActivity.this, "Le biens ne peut accueille " + nombreVoyageur.getText().toString() + " voyageurs", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }

    @Override
    public void onTaskCompleteForReservation(String content) {
        try{
            JSONArray js = new JSONArray(content);
            for(int cpt = 0; cpt<js.length(); cpt++){
                JSONObject o = js.getJSONObject(cpt);

                String nbrVoyageurMax = o.getString("voyageur_max");

                name.setText(o.getString("name"));
                note.setText(o.getString("note"));
                type.setText(o.getString("type"));
                prix.setText(o.getString("price"));
                info_nbr_voyageur.setText(nbrVoyageurMax);
            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(ReservationActivity.this, "Imposible d'avoir les informations", Toast.LENGTH_SHORT).show();
        };
    }
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        System.out.print(calendar.getTime());

        return calendar.getTime();
    }
}