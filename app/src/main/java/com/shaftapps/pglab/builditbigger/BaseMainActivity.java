package com.shaftapps.pglab.builditbigger;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shaftapps.pglab.joketellinglib.JokeTellingActivity;

import org.json.JSONObject;

import java.security.SecureRandom;

/**
 * Created by Paulina on 06.01.16.
 */
public abstract class BaseMainActivity extends AppCompatActivity implements View.OnClickListener,
        JokeLoadingTask.Listener {
    private static final String JOKE_FETCH_IN_PROGRESS = "com.shaftapps.pglab.builditbigger.FetchInProgress";

    protected JokeLoadingTask jokeLoadingTask;
    private Button tellJokeButton;
    private ProgressBar progressBar;
    private int[] colors;
    private TypedArray patterns;
    private SecureRandom secureRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tellJokeButton = (Button) findViewById(R.id.tell_joke_button);
        tellJokeButton.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        secureRandom = new SecureRandom();

        colors = getResources().getIntArray(R.array.joke_background_colors);
        patterns = getResources().obtainTypedArray(R.array.joke_background_patterns);

        if (savedInstanceState != null && savedInstanceState.getBoolean(JOKE_FETCH_IN_PROGRESS))
            startLoadingJoke();
    }


    @Override
    public void jokeLoaded(JSONObject jokeObject) {
        showJoke(jokeObject);
    }

    protected void showJoke(JSONObject jokeObject) {
        jokeLoadingTask = null;
        JokeTellingActivity.openActivity(this, jokeObject, getRandomColor(), getRandomPatternId());
        stopLoading();
    }

    @Override
    public void jokeLoadingFailed() {
        jokeLoadingTask = null;
        stopLoading();
        Toast.makeText(this, R.string.joke_fetch_failed_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        startLoadingJoke();
    }

    protected void startLoadingJoke() {
        tellJokeButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        if (jokeLoadingTask == null) {
            jokeLoadingTask = new JokeLoadingTask(this);
            jokeLoadingTask.execute();
        }
    }

    private void stopLoading() {
        tellJokeButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private int getRandomColor() {
        int randomIndex = secureRandom.nextInt(colors.length - 1);
        return colors[randomIndex];
    }

    private int getRandomPatternId() {
        int randomIndex = secureRandom.nextInt(patterns.length() - 1);
        return patterns.getResourceId(randomIndex, -1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (jokeLoadingTask != null)
            outState.putBoolean(JOKE_FETCH_IN_PROGRESS, true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (jokeLoadingTask != null) {
            jokeLoadingTask.cancel(true);
            jokeLoadingTask = null;
        }
    }
}
