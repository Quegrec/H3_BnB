package com.example.rent;

import android.os.AsyncTask;
import android.util.Log;

import com.example.rent.log.MainActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SelectTask extends AsyncTask<String,String,String> {

    private MainActivity act;

    public SelectTask(MainActivity aa){
        this.act = aa;
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
            url = new URL(this.act.getResources().getString(R.string.url));

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
        this.act.onTaskComplete(s);
    }
}
