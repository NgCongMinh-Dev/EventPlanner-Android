package com.htw.project.eventplanner.Business;

import android.os.Handler;
import android.os.Looper;

import com.htw.project.eventplanner.BuildConfig;
import com.htw.project.eventplanner.Model.Event;
import com.htw.project.eventplanner.Model.Task;
import com.htw.project.eventplanner.Rest.ApiCallback;
import com.squareup.moshi.JsonAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskBusiness extends ApiBusiness {

    public void createTask(Task task, Event event, ApiCallback callback) {
        JsonAdapter<Task> jsonAdapter = getAdapter(Task.class);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, jsonAdapter.toJson(task));

        Request request = buildPostRequest(BuildConfig.BACKEND_BASE_URL, "/tasks?event=" + event.getId(), requestBody);

        getApiClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onError("Failed to create task.");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // TODO error
                    return;
                }

                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(null);
                });

                response.close();
            }
        });
    }

}
