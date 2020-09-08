package com.htw.project.eventplanner.Business;

import android.os.Handler;
import android.os.Looper;

import com.htw.project.eventplanner.BuildConfig;
import com.htw.project.eventplanner.Model.Event;
import com.htw.project.eventplanner.Model.GroupConversation;
import com.htw.project.eventplanner.Model.User;
import com.htw.project.eventplanner.Rest.ApiCallback;
import com.squareup.moshi.JsonAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GroupConversationBusiness extends ApiBusiness {

    public void getGroupConversation(int id, ApiCallback<GroupConversation> callback) {
        JsonAdapter<GroupConversation> jsonAdapter = getAdapter(GroupConversation.class);

        Request request = buildGetRequest(BuildConfig.BACKEND_BASE_URL, "/cg/" + id);

        getApiClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onError("Failed to fetch group conversation.");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // TODO error
                    return;
                }

                GroupConversation groupConversation = jsonAdapter.fromJson(response.body().string());

                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(groupConversation);
                });

                response.close();
            }
        });
    }

    public void joinRoom(String username, String firstName, String lastName, ApiCallback callback) {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        JsonAdapter<User> jsonAdapter = getAdapter(User.class);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, jsonAdapter.toJson(user));

        Request request = buildPostRequest(BuildConfig.BACKEND_BASE_URL, "/cg/" + BuildConfig.GROUP_CONVERSATION_ID + "/users", requestBody);

        getApiClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onError("Failed to join room.");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // TODO error
                    return;
                }

                UserInfoProvider.setCurrentUser(user);

                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(null);
                });

                response.close();
            }
        });
    }

    public void getEvent(Long groupConversationId, ApiCallback<Event> callback) {
        JsonAdapter<Event> jsonAdapter = getAdapter(Event.class);

        Request request = buildGetRequest(BuildConfig.BACKEND_BASE_URL, "/cg/" + groupConversationId + "/events");

        getApiClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onError("Failed to fetch event.");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // TODO error
                    return;
                }

                Event event = jsonAdapter.fromJson(response.body().string());

                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(event);
                });

                response.close();
            }
        });
    }

}
