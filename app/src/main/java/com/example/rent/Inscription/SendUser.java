package com.example.rent.Inscription;

import android.os.AsyncTask;
import android.util.Log;

import com.example.rent.Inscription.InscriptionActivity;
import com.example.rent.R;
import com.example.rent.homepage.homepageActivity;
import com.example.rent.log.MainActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendUser extends AsyncTask<String,String,String> {

    private InscriptionActivity act;
    private String name, firstname, mail, password, phone;

    public SendUser(InscriptionActivity aa, String name, String firstname, String mail, String password, String phone){
        this.act = aa;
        this.name = name;
        this.firstname = firstname;
        this.mail = mail;
        this.password = password;
        this.phone = phone;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        URL url;
        HttpURLConnection urlConnection = null;
        try{
            url = new URL(this.act.getResources().getString(R.string.url) + "/registration.php?name=" + name + "&firstname=" + firstname + "&mail=" + mail + "&password=" + password + "&phone=" + phone);
            Log.d("url", this.act.getResources().getString(R.string.url) + "/registration.php?name=" + name + "&firstname=" + firstname + "&mail=" + mail + "&password=" + password + "&phone=" + phone);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        };
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        this.act.onTaskCompleteLog(s);
    }
}