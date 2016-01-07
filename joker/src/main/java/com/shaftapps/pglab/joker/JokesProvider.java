package com.shaftapps.pglab.joker;

/**
 * Created by Paulina on 04.01.16.
 */
public interface JokesProvider {
    Joke getRandomJoke();

    int getJokesCount();

    Joke getJokeById(int id);
}
