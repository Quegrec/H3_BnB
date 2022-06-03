package com.example.rent.Home;

import android.os.AsyncTask;
import android.util.Log;

import com.example.rent.Home.HomeActivity;
import com.example.rent.R;
import com.example.rent.user.UserActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoveHome extends AsyncTask<String,String,String> {

    private HomeActivity act;
    private String homeId;

    public RemoveHome(HomeActivity aa, String homeId){
        this.act = aa;
        this.homeId = homeId;
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
            url = new URL(this.act.getResources().getString(R.string.url) + "/removeHome.php?id=" + homeId);

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);

            String resultat = new String();
            StringBuffer line = new StringBuffer();
            int val = isw.read();
            while(val!=-1){
                line.append((char)val);
                val = isw.read();
            }
            Log.d("HTTP_REPONSE",line.toString());
            return line.toString();

        }catch(Exception e){
            e.printStackTrace();
        };
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        this.act.onTaskCompleteRemoveHome(s);
    }
}
