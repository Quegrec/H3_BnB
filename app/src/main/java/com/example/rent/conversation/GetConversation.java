package com.example.rent.conversation;

import android.os.AsyncTask;
import android.util.Log;

import com.example.rent.R;
import com.example.rent.message.MessageActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetConversation extends AsyncTask<String,String,String> {

    private ConversationActivity act;
    private int id, send_to;

    public GetConversation(ConversationActivity aa, int id, int send_to){
        this.act = aa;
        this.id = id;
        this.send_to = send_to;
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
            url = new URL(this.act.getResources().getString(R.string.url) + "/getConversation.php?from=" + id + "&to=" + send_to);
            Log.d("url", this.act.getResources().getString(R.string.url) + "/getConversation.php?from=" + id + "&to=" + send_to);

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
        this.act.onTaskCompleteConversation(s);
    }
}