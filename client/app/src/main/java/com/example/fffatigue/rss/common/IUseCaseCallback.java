package com.example.fffatigue.rss.common;

public interface IUseCaseCallback<T> {
    void onSuccess(T response);
    void onError();
}