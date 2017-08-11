package com.impvhc.xat;

import android.app.Application;

import com.impvhc.xat.inject.application.AppComponent;
import com.impvhc.xat.inject.application.AppModule;
import com.impvhc.xat.inject.application.DaggerAppComponent;
import com.impvhc.xat.inject.application.NetworkModule;

/**
 * Created by victor on 8/9/17.
 */

public class XatApplication extends Application {
    private static XatApplication mAppContext;

    AppComponent mComponent;

    public static XatApplication get() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = (XatApplication) getApplicationContext();

        // Dagger%COMPONENT_NAME%
        mComponent = DaggerAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .networkModule(new NetworkModule("http://192.168.1.135:1337/parse/"))
                .build();

        // If a Dagger 2 component does not have any constructor arguments for any of its modules,
        // then we can use .create() as a shortcut instead:
    }

    public AppComponent getComponent() {
        return mComponent;
    }
}
