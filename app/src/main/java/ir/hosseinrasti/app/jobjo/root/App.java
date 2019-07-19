package ir.hosseinrasti.app.jobjo.root;

import android.app.Application;

import ir.hosseinrasti.app.jobjo.di.component.DaggerApplicationComponent;
import ir.hosseinrasti.app.jobjo.di.module.ActivityModule;
import ir.hosseinrasti.app.jobjo.di.module.AdapterModule;
import ir.hosseinrasti.app.jobjo.di.module.ApiModule;
import ir.hosseinrasti.app.jobjo.di.module.ApplicationModule;
import ir.hosseinrasti.app.jobjo.di.module.FragmentModule;
import ir.hosseinrasti.app.jobjo.di.module.PresenterModule;
import ir.hosseinrasti.app.jobjo.di.module.DataSourceModule;
import ir.hosseinrasti.app.jobjo.di.component.ApplicationComponent;

/**
 * Created by Hossein on 5/17/2018.
 */

public class App extends Application {

    private static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( this ) )
                .activityModule( new ActivityModule() )
                .presenterModule( new PresenterModule() )
                .fragmentModule( new FragmentModule() )
                .adapterModule( new AdapterModule() )
                .apiModule( new ApiModule() )
                .dataSourceModule( new DataSourceModule() )
                .build();
    }

    public static ApplicationComponent getComponent() {
        return component;
    }
}
