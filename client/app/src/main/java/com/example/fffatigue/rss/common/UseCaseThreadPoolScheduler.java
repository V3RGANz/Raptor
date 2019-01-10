package com.example.fffatigue.rss.common;

import android.os.Handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseCaseThreadPoolScheduler implements  IUseCaseScheduler{
    private static final int POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 4;
    private static final int TIMEOUT = 30;

    private final Handler handler = new Handler();
    private final ThreadPoolExecutor threadPoolExecutor;

    UseCaseThreadPoolScheduler() {
        this.threadPoolExecutor =  new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(POOL_SIZE));
    }

    //region IUseCaseScheduler
    @Override
    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    @Override
    public <T extends UseCase.ResponseValues> void onResponse(final T response, final IUseCaseCallback<T> callback) {
        handler.post(() -> callback.onSuccess(response));
    }

    @Override
    public <T extends UseCase.ResponseValues> void onError(final IUseCaseCallback<T> callback) {
        handler.post(callback::onError);
    }
    //endregion
}
