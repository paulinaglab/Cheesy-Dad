package com.shaftapps.pglab.joker;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.security.SecureRandom;

/**
 * Created by Paulina on 04.01.16.
 */
public class SimpleJokesProvider implements JokesProvider {

    private static final String JOKES_FILE_NAME = "jokes.json";

    private JSONArray jokesArray;
    private SecureRandom secureRandom;

    @Override
    public Joke getRandomJoke() {
        loadJokesArrayIfNeeded();

        int jokeIndex = getJokeIndex();
        return new Joke(jokeIndex + 1, jokesArray.getJSONArray(jokeIndex));
    }


    @Override
    public int getJokesCount() {
        loadJokesArrayIfNeeded();
        return jokesArray.length();
    }

    @Override
    public Joke getJokeById(int id) {
        loadJokesArrayIfNeeded();
        return new Joke(id, jokesArray.getJSONArray(id - 1));
    }

    private int getJokeIndex() {
        if (secureRandom == null) {
            secureRandom = new SecureRandom();
        }
        return secureRandom.nextInt(jokesArray.length());
    }

    private void loadJokesArrayIfNeeded() {
        try {
            if (jokesArray == null) {
                String jokesString = IOUtils.toString(
                        getClass().getClassLoader().getResourceAsStream(JOKES_FILE_NAME));
                jokesArray = new JSONArray(jokesString);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot load jokes file.");
        }
    }
}
