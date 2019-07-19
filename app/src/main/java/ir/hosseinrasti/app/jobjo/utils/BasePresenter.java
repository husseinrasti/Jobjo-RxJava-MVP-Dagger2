package ir.hosseinrasti.app.jobjo.utils;

/**
 * Created by Hossein on 7/13/2018.
 */

public interface BasePresenter<T> {

    void takeView(T view);
    void dropView();
}
