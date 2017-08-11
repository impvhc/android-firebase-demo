package com.impvhc.xat.presenter;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.impvhc.xat.AppSharedPreferences;
import com.impvhc.xat.XatApplication;
import com.impvhc.xat.activity.BaseActivity;
import com.impvhc.xat.activity.HomeActivity;
import com.impvhc.xat.activity.SignInActivity;
import com.impvhc.xat.inject.activty.ActivityModule;
import com.impvhc.xat.view.SplashView;

import javax.inject.Inject;

/**
 * Created by victor on 8/7/17.
 */

public class SplashPresenter extends BasePresenter<Void,SplashView> {

    @Inject
    AppSharedPreferences appSharedPreferences;

    private FirebaseAuth mFirebaseAuth;

    public SplashPresenter(SplashView view) {
        super(view);
    }

    @Override
    public void onCreate() {
        XatApplication.get().getComponent().plus(new ActivityModule(view.getContext())).inject(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        checkFlow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void checkFlow(){
        new Handler().postDelayed(() -> {
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            if(user != null && user.isEmailVerified())
                HomeActivity.start(view.getContext(),null);
            else
                SignInActivity.start(view.getContext(),null);
            BaseActivity.finish((Activity) view.getContext());
        },1500);
    }
}
