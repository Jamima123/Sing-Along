package com.project1.musicapp.singalong;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

/**
 * Created by jamim on 11/27/2017.
 */

public class GetWeatherData extends AsyncTask<String, Void, String> {


    private TextView textView;
    private Context context;
    private String weather;

    public GetWeatherData(Context context, TextView textView, String weather ) {
        this.context = context;
        this.weather = weather;
        this.textView = textView;

    }

    @Override
    public void onPreExecute()
    {

    }


    @Override

    public String doInBackground(String... strings) {

        weather = "UNDEFINED";
        //String weather1="Und";

        try {
            URL url = new URL(strings[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder = new StringBuilder();


            String inputString;

            while ((inputString = bufferedReader.readLine()) != null) {

                builder.append(inputString);
            }


            JSONObject topLevel = new JSONObject(builder.toString());

            JSONArray jsonarray = topLevel.getJSONArray("weather");
            JSONObject obj = jsonarray.getJSONObject(0);
            weather = obj.getString("description");


            //weather = String.valueOf(main.getDouble("temp"));//if temp needed


            urlConnection.disconnect();


        } catch (IOException | JSONException e) {

            e.printStackTrace();
        }

        return weather;


    }


    public void onPostExecute(String temp) {
        if (temp.equals("Haze")
                || temp.equals("Clear sky")
                ||temp.equals("light rain"));
           // temp = "Haze";

        temp = "Sunny"; //comment this line

        Musicplayer parent = (Musicplayer) context;

        parent.weather = temp;




        textView.setText("Current Weather:" + temp);




        parent.data = new TreeMap<String, SongAction>();
        SongInfo.collectSongs(parent.data);




        parent.setSong(false);
        parent.setupListeners();

        //Toast.makeText(context, weather, Toast.LENGTH_SHORT).show();




    }


}

