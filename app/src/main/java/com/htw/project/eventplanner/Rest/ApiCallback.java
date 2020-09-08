package com.htw.project.eventplanner.Rest;

public interface ApiCallback<T> {

    void onSuccess(T response);

    void onError(String message);

}
