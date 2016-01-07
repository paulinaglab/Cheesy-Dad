/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.shaftapps.pglab.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.shaftapps.pglab.joker.Joke;
import com.shaftapps.pglab.joker.Joker;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "joker",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.pglab.shaftapps.com",
                ownerName = "backend.builditbigger.pglab.shaftapps.com",
                packagePath = ""
        )
)
public class JokesEndpoint {

    private final Joker joker;

    public JokesEndpoint() {
        joker = new Joker();
    }

    @ApiMethod(name = "joke", httpMethod = ApiMethod.HttpMethod.GET)
    public Joke getJoke() {
        return joker.getRandomJoke();
    }

    @ApiMethod(name = "jokes", path = "jokes/{id}", httpMethod = ApiMethod.HttpMethod.GET)
    public Joke getJokeById(@Named("id") int id) {
        return joker.getJoke(id);
    }

    @ApiMethod(name = "count", path = "jokes/count", httpMethod = ApiMethod.HttpMethod.GET)
    public JokesCount getJokesCount() {
        return new JokesCount(joker.getJokesCount());
    }

}
