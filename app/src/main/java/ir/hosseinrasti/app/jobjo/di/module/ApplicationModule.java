package ir.hosseinrasti.app.jobjo.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.utils.BasePresenter;
import ir.hosseinrasti.app.jobjo.utils.FileResolver;
import ir.hosseinrasti.app.jobjo.utils.Font;

/**
 * Created by Hossein on 5/17/2018.
 */

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule( Application application ) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    SessionPref provideSession() {
        return new SessionPref( provideContext() );
    }

    @Singleton
    @Provides
    Font provideFont() {
        return new Font( provideContext() );
    }

    @Singleton
    @Provides
    FileResolver provideFileResolver() {
        return new FileResolver( provideContext().getContentResolver() );
    }

}
