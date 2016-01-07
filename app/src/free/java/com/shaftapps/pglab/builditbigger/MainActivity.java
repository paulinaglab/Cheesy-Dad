package com.shaftapps.pglab.builditbigger;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Paulina on 06.01.16.
 */
public class MainActivity extends BaseMainActivity {

    private static final String TEST_DEVICE_ID = "B483718751690A12112FB014ED0B6783";
    private static final String JOKE_KEY = "joke";
    private InterstitialAd interstitialAd;
    private JSONObject jokeObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                showJoke(jokeObject);
            }
        });

        String jokeString;
        if (savedInstanceState != null && (jokeString = savedInstanceState.getString(JOKE_KEY)) != null) {
            try {
                jokeObject = new JSONObject(jokeString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE_ID)
                .build();

        interstitialAd.loadAd(adRequest);
    }

    @Override
    public void onClick(View v) {
        startLoadingJoke();
    }

    @Override
    public void jokeLoaded(JSONObject jokeObject) {
        jokeLoadingTask = null;
        this.jokeObject = jokeObject;
        if (interstitialAd.isLoaded())
            interstitialAd.show();
        else
            showJoke(jokeObject);
    }

    protected void showJoke(JSONObject jsonObject) {
        super.showJoke(jokeObject);
        jokeObject = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (jokeObject != null)
            outState.putString(JOKE_KEY, jokeObject.toString());
    }
}
