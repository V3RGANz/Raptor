package com.example.fffatigue.rss.common;

public class UseCaseCallbackWrapper<T extends UseCase.ResponseValues>
        implements IUseCaseCallback<T> {
    private final IUseCaseCallback<T> callback;
    private final UseCaseHandler useCaseHandler;

    UseCaseCallbackWrapper(IUseCaseCallback<T> callback, UseCaseHandler useCaseHandler) {
        this.callback = callback;
        this.useCaseHandler = useCaseHandler;
    }

    //region IUseCaseCallback
    @Override
    public void onSuccess(T response) {
        useCaseHandler.notifyResponse(response, callback);
    }

    @Override
    public void onError() {
        useCaseHandler.notifyError(callback);
    }
//endregion
}