package ir.hosseinrasti.app.jobjo.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ir.hosseinrasti.app.jobjo.utils.rx.AppSchedulerProvider;
import ir.hosseinrasti.app.jobjo.utils.rx.SchedulerProvider;

/**
 * Created by Hossein on 7/13/2018.
 */

@Module
public class ActivityModule {

    @Singleton
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Singleton
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }


}
