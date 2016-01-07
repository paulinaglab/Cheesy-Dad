package com.shaftapps.pglab.builditbigger;

import android.test.AndroidTestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

public class JokeTaskTest extends AndroidTestCase {

    @Override
    protected void runTest() throws Throwable {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new JokeLoadingTask(new JokeLoadingTask.Listener() {
            @Override
            public void jokeLoaded(JSONObject jokeObject) {
                try {
                    assertTrue(jokeObject.getJSONArray("joke").length() > 0);
                } catch (JSONException e) {
                }
                countDownLatch.countDown();
            }

            @Override
            public void jokeLoadingFailed() {
                countDownLatch.countDown();
            }
        }).execute();

        countDownLatch.await();
    }

}