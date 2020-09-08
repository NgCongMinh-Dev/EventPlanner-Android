package com.htw.project.eventplanner.Business;

import com.htw.project.eventplanner.Rest.MoshiFactory;
import com.htw.project.eventplanner.Rest.RestClientFactory;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class ApiBusiness {

    protected final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    protected enum HttpMethod {
        POST, PUT, DELETE, GET;
    }

    protected OkHttpClient getApiClient() {
        return RestClientFactory.getClient();
    }

    private Request buildRequest(String server, String url, RequestBody body, HttpMethod method) {
        Request.Builder builder = new Request.Builder()
                .url(server + url);

        switch (method) {
            case POST:
                builder.post(body);
                break;
            case PUT:
                builder.put(body);
                break;
            case DELETE:
                builder.delete();
                break;
            case GET:
                builder.get();
                break;
        }

        return builder.build();
    }

    protected Request buildGetRequest(String server, String url) {
        return buildRequest(server, url, null, HttpMethod.GET);
    }

    protected Request buildPostRequest(String server, String url, RequestBody body) {
        return buildRequest(server, url, body, HttpMethod.POST);
    }

    protected Request buildPutRequest(String server, String url, RequestBody body) {
        return buildRequest(server, url, body, HttpMethod.PUT);
    }

    protected Request buildDeleteRequest(String server, String url) {
        return buildRequest(server, url, null, HttpMethod.DELETE);
    }

    protected <ENTITY> JsonAdapter<ENTITY> getAdapter(Class<ENTITY> clazz) {
        Moshi moshi = MoshiFactory.create();
        return moshi.adapter(clazz);
    }

}
