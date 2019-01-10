package com.example.fffatigue.rss.common;

public abstract class UseCase<RequestType extends UseCase.RequestValues,
        ResponseType extends  UseCase.ResponseValues> {
    //region Private entities
    private RequestType requestValues;
    private IUseCaseCallback<ResponseType> useCaseCallback;
    //endregion

    //region Should be override
    protected abstract void executeUseCase( RequestType requestValues);
    //endregion

    //region Getters
    public RequestType getRequestValues() {
        return requestValues;
    }

    protected IUseCaseCallback<ResponseType> getUseCaseCallback() {
        return useCaseCallback;
    }
    //endregion

    //region Setters
    void setRequestValues(RequestType requestValues) {
        this.requestValues = requestValues;
    }

    void setUseCaseCallback(IUseCaseCallback<ResponseType> useCaseCallback) {
        this.useCaseCallback = useCaseCallback;
    }
    //endregion

    void run(){
        executeUseCase(requestValues);
    }
    //region Internal types for Request and Response values
    public interface RequestValues{}
    public interface ResponseValues{}
    //endregion
}
