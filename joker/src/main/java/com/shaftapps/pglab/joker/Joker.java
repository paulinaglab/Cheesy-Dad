package com.shaftapps.pglab.joker;

public class Joker {

    private JokesProvider jokesProvider;

    public Joker() {
        jokesProvider = new SimpleJokesProvider();
    }

    public Joke getRandomJoke() {
        return jokesProvider.getRandomJoke();
    }

    public Joke getJoke(int id) {
        return jokesProvider.getJokeById(id);
    }

    public int getJokesCount() {
        return jokesProvider.getJokesCount();
    }

}
