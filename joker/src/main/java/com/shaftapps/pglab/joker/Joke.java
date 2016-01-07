package com.shaftapps.pglab.joker;

import org.json.JSONArray;

/**
 * Created by Paulina on 04.01.16.
 */
public class Joke {

    private final int id;
    private final JSONArray joke;

    public Joke(int id, JSONArray joke) {
        this.id = id;
        this.joke = joke;
    }

    public int getId() {
        return id;
    }

    public JSONArray getJoke() {
        return joke;
    }
}
