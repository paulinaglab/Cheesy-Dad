package com.shaftapps.pglab.joketellinglib;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Paulina on 04.01.16.
 */
public class JokeTellingActivity extends AppCompatActivity {

    public static final String JOKE_EXTRA = "com.shaftapps.pglab.joketellinglib.JokeExtra";
    private static final String JOKE_KEY = "joke";
    private static final String JOKE_BACKGROUND_COLOR_KEY = "joke_background_color";
    private static final String JOKE_BACKGROUND_PATTERN_KEY = "joke_background_pattern";


    public static void openActivity(Context context, JSONObject jokeExtra, @ColorInt int background, int patternId) {
        Intent intent = new Intent(context, JokeTellingActivity.class);
        intent.putExtra(JOKE_EXTRA, jokeExtra.toString());
        intent.putExtra(JOKE_BACKGROUND_COLOR_KEY, background);
        intent.putExtra(JOKE_BACKGROUND_PATTERN_KEY, patternId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_joke_telling);

        // Loading background appearance.
        int bgColor = getIntent().getIntExtra(
                JOKE_BACKGROUND_COLOR_KEY,
                getResources().getColor(R.color.default_background));
        findViewById(R.id.parent_layout).setBackgroundColor(bgColor);

        int patternId = getIntent().getIntExtra(JOKE_BACKGROUND_PATTERN_KEY, -1);
        if (patternId != -1) {
            Drawable bgPattern = getResources().getDrawable(patternId);
            findViewById(R.id.joke_pattern_view).setBackgroundDrawable(bgPattern);
        }

        // Loading joke.
        String[] joke = getJoke();

        LinearLayout jokesLayout = (LinearLayout) findViewById(R.id.joke_chat_list_layout);

        for (String text : joke) {
            TextView textView = (TextView) View.inflate(this, R.layout.joke_line_text_view, null);;
            textView.setText(text);
            jokesLayout.addView(textView);
        }
    }

    private String[] getJoke() {
        try {
            JSONArray jokeArray = new JSONObject(getIntent().getStringExtra(JOKE_EXTRA))
                    .getJSONArray(JOKE_KEY);
            String[] joke = new String[jokeArray.length()];
            for (int i = 0; i < jokeArray.length(); i++) {
                joke[i] = jokeArray.getString(i);
            }
            return joke;
        } catch (JSONException e) {
            throw new RuntimeException("Wrong joke format!");
        }
    }
}
