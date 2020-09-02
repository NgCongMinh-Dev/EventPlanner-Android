package com.htw.project.eventplanner.Rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public final class RestClientFactory {

    private static final int TIMEOUT_AFTER = 15;

    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_AFTER, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_AFTER, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_AFTER, TimeUnit.SECONDS)
                    .build();
        }
        return client;
    }


}
