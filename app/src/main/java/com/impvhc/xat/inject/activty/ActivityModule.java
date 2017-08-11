package com.impvhc.xat.inject.activty;

import android.content.Context;

import com.impvhc.xat.presenter.HomePresenter;
import com.impvhc.xat.presenter.SignInPresenter;
import com.impvhc.xat.presenter.SignUpPresenter;
import com.impvhc.xat.presenter.SplashPresenter;
import com.impvhc.xat.view.HomeView;
import com.impvhc.xat.view.SignInView;
import com.impvhc.xat.view.SignUpView;
import com.impvhc.xat.view.SplashView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victor on 8/4/17.
 */

@Module
public class ActivityModule {
    private final Context context;

    public ActivityModule(Context context) {
        this.context = context;
    }


    @Provides
    @ActivityScope
    public SplashView splashView(){
        return new SplashView(context);
    }

    @Provides
    @ActivityScope
    public SplashPresenter splashPresenter(SplashView view){
        return new SplashPresenter(view);
    }


    /*Start Sign In & Sing Up*/
    @Provides
    @ActivityScope
    public SignInView signInView(){
        return new SignInView(context);
    }

    @Provides
    @ActivityScope
    public SignInPresenter signInPresenter(SignInView view){
        return new SignInPresenter(view);
    }


    @Provides
    @ActivityScope
    public SignUpView signUpView(){
        return new SignUpView(context);
    }

    @Provides
    @ActivityScope
    public SignUpPresenter signUpPresenter(SignUpView view){
        return new SignUpPresenter(view);
    }
    /*End Sign In & Sing Up*/

    @Provides
    @ActivityScope
    public HomeView homeView(){
        return new HomeView(context);
    }

    @Provides
    @ActivityScope
    public HomePresenter homePresenter(HomeView view){
        return new HomePresenter(view);
    }



}
