package com.htw.project.eventplanner.Business;

import android.os.Handler;
import android.os.Looper;

import com.htw.project.eventplanner.BuildConfig;
import com.htw.project.eventplanner.Model.Event;
import com.htw.project.eventplanner.Model.GroupConversation;
import com.htw.project.eventplanner.Model.Task;
import com.htw.project.eventplanner.Model.TaskSection;
import com.htw.project.eventplanner.Model.User;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Rest.ApiCallback;
import com.squareup.moshi.JsonAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    callback.onError("Failed to create task.");
                    return;
                }

                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(null);
                    response.close();
                });
            }
        });
    }

    public void updateTask(Task task, ApiCallback callback) {
        JsonAdapter<Task> jsonAdapter = getAdapter(Task.class);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, jsonAdapter.toJson(task));

        Request request = buildPutRequest(BuildConfig.BACKEND_BASE_URL, "/tasks/" + task.getId(), requestBody);

        getApiClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onError("Failed to update task.");
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

    public void changeTaskStatus(Task.Status status, Task task, ApiCallback<Task> callback) {
        Task body = new Task();
        body.setStatus(status);

        JsonAdapter<Task> jsonAdapter = getAdapter(Task.class);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, jsonAdapter.toJson(body));

        Request request = buildPatchRequest(BuildConfig.BACKEND_BASE_URL, "/tasks/" + task.getId() + "?status=" + status, requestBody);

        getApiClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onError("Failed to update task status.");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onError("Failed to update task status.");
                    return;
                }

                String json = response.body().string();
                Task responseTask = jsonAdapter.fromJson(json);

                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(responseTask);
                    response.close();
                });
            }
        });
    }

    public void deleteTask(Task task, ApiCallback callback) {
        Request request = buildDeleteRequest(BuildConfig.BACKEND_BASE_URL, "/tasks/" + task.getId());

        getApiClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onError("Failed to delete task.");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onError("Failed to delete task.");
                    return;
                }

                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(null);
                    response.close();
                });
            }
        });
    }

    public List<TaskSection> getTasksSortedByStatus(Event event) {
        TaskSection pendingTaskSection = new TaskSection();
        pendingTaskSection.setSectionTitle(R.string.task_section_status_pending);

        TaskSection finishedTaskSection = new TaskSection();
        finishedTaskSection.setSectionTitle(R.string.task_section_status_finished);

        event.getTasks().forEach(task -> {
            Task.Status status = task.getStatus();

            switch (status) {
                case PENDING:
                    pendingTaskSection.addTask(task);
                    break;
                case FINISHED:
                    finishedTaskSection.addTask(task);
                    break;
            }
        });

        return Arrays.asList(pendingTaskSection, finishedTaskSection);
    }

    public List<TaskSection> getTasksSortedByAssignee(GroupConversation gc, Event event) {
        Map<Long, TaskSection> map = new HashMap<>();

        // create section for unassigned tasks
        Long placeholderForUnassignedTask = new Long(0);
        TaskSection unassignedTaskSection = new TaskSection();
        unassignedTaskSection.setSectionTitle(R.string.task_section_user_unassigned);
        map.put(placeholderForUnassignedTask, unassignedTaskSection);

        // register all participants of the group conversation
        gc.getParticipants().forEach(user -> map.put(user.getId(), createTaskSectionForUserArea(user)));

        event.getTasks().forEach(task -> {
            List<User> assignee = task.getAssignees();
            if (assignee == null || assignee.size() == 0) {
                // add to unassigned task section
                map.get(placeholderForUnassignedTask).addTask(task);
            } else {
                // add to corresponding user task section
                assignee.forEach(user -> {
                            TaskSection t = map.get(user.getId());
                            t.addTask(task);
                        }

                );
            }
        });

        return new ArrayList<>(map.values());
    }

    private TaskSection createTaskSectionForUserArea(User user) {
        TaskSection taskSection = new TaskSection();
        taskSection.setSectionTitle(user.getFullName());
        return taskSection;
    }

    public boolean isAssignee(Task task, User user) {
        for (User assignee : task.getAssignees()) {
            if (assignee.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

}
