package com.example.rent.addHome;

import android.os.AsyncTask;
import android.util.Log;

import com.example.rent.Inscription.InscriptionActivity;
import com.example.rent.R;
import com.example.rent.addHome.AddHomeActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendNewHome extends AsyncTask<String,String,String> {

    private AddHomeActivity act;
    private String img, titre, type, adresse, nombreVoyageur, nombreLit, prix, code;

    public SendNewHome(AddHomeActivity aa, String img, String titre, String type, String adresse, String nombreVoyageur, String nombreLit, String prix, String code){
        this.act = aa;
        this.img = img;
        this.titre = titre;
        this.type = type;
        this.adresse = adresse;
        this.nombreVoyageur = nombreVoyageur;
        this.nombreLit = nombreLit;
        this.prix = prix;
        this.code = code;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        // Code pour envoie :
        URL url;
        HttpURLConnection urlConnection = null;
        try{
            url = new URL(this.act.getResources().getString(R.string.url) + "/newHome.php?img=" + img + "&titre=" + titre + "&type=" + type + "&adresse=" + adresse + "&nombreVoyageur=" + nombreVoyageur + "&nombreLit=" + nombreLit + "&prix=" + prix + "&code=" + code);
            Log.d("url", this.act.getResources().getString(R.string.url) + "/newHome.php?img=" + img + "&titre=" + titre + "&type=" + type + "&adresse=" + adresse + "&nombreVoyageur=" + nombreVoyageur + "&nombreLit=" + nombreLit + "&prix=" + prix + "&code=" + code);
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
        //this.act.onTaskCompleteNewHome(s);
    }
}
