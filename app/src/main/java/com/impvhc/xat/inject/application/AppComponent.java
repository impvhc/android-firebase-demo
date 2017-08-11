package com.impvhc.xat.inject.application;

import com.impvhc.xat.inject.activty.ActivityComponent;
import com.impvhc.xat.inject.activty.ActivityModule;

import dagger.Component;

/**
 * Created by victor on 8/5/17.
 */

@AppScope
@Component(modules = { AppModule.class, NetworkModule.class, ApiModule.class})
public interface AppComponent {
    ActivityComponent plus(ActivityModule activityModule);
}
