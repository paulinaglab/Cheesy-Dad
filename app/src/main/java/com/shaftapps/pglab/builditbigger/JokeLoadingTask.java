package com.shaftapps.pglab.builditbigger;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Paulina on 04.01.16.
 */
public class JokeLoadingTask extends AsyncTask<Void, Void, JSONObject> {

    public static final String HOST_IP = "192.168.0.12:8080";
    public static final String RANDOM_JOKE_URL = "http://" + HOST_IP + "/_ah/api/joker/v1/joke";

    private Listener listener;

    public JokeLoadingTask(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        BufferedReader reader = null;
        URLConnection connection;
        try {
            URL url = new URL(RANDOM_JOKE_URL);
            connection = url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder stringBuilder = new StringBuilder();
            String read;
            while ((read = reader.readLine()) != null) {
                stringBuilder.append(read);
            }

            return new JSONObject(stringBuilder.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (jsonObject == null)
            listener.jokeLoadingFailed();
        else
            listener.jokeLoaded(jsonObject);
    }

    public interface Listener {
        void jokeLoaded(JSONObject jokeObject);

        void jokeLoadingFailed();
    }
}
