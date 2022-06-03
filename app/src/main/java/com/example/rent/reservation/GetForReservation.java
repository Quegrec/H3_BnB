package com.example.rent.reservation;

import android.os.AsyncTask;
import android.util.Log;

import com.example.rent.R;
import com.example.rent.reservation.ReservationActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetForReservation extends AsyncTask<String,String,String> {

    private ReservationActivity act;
    private String userId;

    public GetForReservation(ReservationActivity aa, String userId) {
        this.act = aa;
        this.userId = userId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(this.act.getResources().getString(R.string.url) + "/getForReservation.php?id=" + userId);
            Log.d("url", this.act.getResources().getString(R.string.url) + "/getForReservation.php?id=" + userId);

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);

            String resultat = new String();
            StringBuffer line = new StringBuffer();
            int val = isw.read();
            while (val != -1) {
                line.append((char) val);
                val = isw.read();
            }
            Log.d("HTTP_REPONSE", line.toString());
            return line.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        this.act.onTaskCompleteForReservation(s);
    }
}