package com.example.fffatigue.rss.common;

public class UseCaseHandler {
    private static UseCaseHandler useCaseHandler;
    private final IUseCaseScheduler useCaseScheduler;

    //region Singleton initialization
    public static  UseCaseHandler getInstance(){
        if (null == useCaseHandler){
            useCaseHandler =new UseCaseHandler( new UseCaseThreadPoolScheduler());
        }
        return useCaseHandler;
    }
    private  UseCaseHandler(IUseCaseScheduler useCaseScheduler){
        this.useCaseScheduler = useCaseScheduler;
    }
    //endregion

    public <T extends UseCase.RequestValues, R extends  UseCase.ResponseValues> void execute (
            final UseCase<T, R> useCase, T requestValues, IUseCaseCallback<R> callback){

        useCase.setRequestValues(requestValues);
        useCase.setUseCaseCallback( new UseCaseCallbackWrapper<>(callback, this));
        useCaseScheduler.execute(useCase::run);
    }

    <T extends  UseCase.ResponseValues> void notifyResponse(T response, IUseCaseCallback<T> callback){
        useCaseScheduler.onResponse(response, callback);
    }
    <T extends  UseCase.ResponseValues> void notifyError(IUseCaseCallback<T> callback){
        useCaseScheduler.onError(callback);
    }
}
