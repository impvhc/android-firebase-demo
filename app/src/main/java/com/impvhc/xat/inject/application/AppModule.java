package com.impvhc.xat.inject.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.impvhc.xat.AppSharedPreferences;

import dagger.Module;
import dagger.Provides;


/**
 * Created by victor on 8/5/17.
 */

@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @AppScope
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @AppScope
    AppSharedPreferences providesSharedPreferences(Application application) {
        return new AppSharedPreferences(application);
    }

}
