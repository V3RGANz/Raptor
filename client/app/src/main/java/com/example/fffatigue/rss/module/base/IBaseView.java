package com.example.fffatigue.rss.module.base;

public interface IBaseView<T extends  IBasePresenter> {
    void setPresenter( T presenter);
}
