package com.impvhc.xat.inject.activty;

import com.impvhc.xat.activity.HomeActivity;
import com.impvhc.xat.activity.SignInActivity;
import com.impvhc.xat.activity.SignUpActivity;
import com.impvhc.xat.activity.SplashActivity;
import com.impvhc.xat.presenter.HomePresenter;
import com.impvhc.xat.presenter.SignInPresenter;
import com.impvhc.xat.presenter.SplashPresenter;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by victor on 8/4/17.
 */
@ActivityScope
@Subcomponent(modules = { ActivityModule.class })
public interface ActivityComponent {

    void inject(SignInActivity signInActivity);

    void inject(SignInPresenter signInPresenter);

    void inject(HomeActivity homeActivity);

    void inject(SplashActivity splashActivity);

    void inject(SplashPresenter splashPresenter);

    void inject(HomePresenter homePresenter);

    void inject(SignUpActivity signUpActivity);
}
