package com.impvhc.xat.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.impvhc.xat.AppSharedPreferences;
import com.impvhc.xat.XatApplication;
import com.impvhc.xat.activity.BaseActivity;
import com.impvhc.xat.activity.SignInActivity;
import com.impvhc.xat.inject.activty.ActivityModule;
import com.impvhc.xat.util.ViewUtil;
import com.impvhc.xat.view.HomeView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by victor on 8/7/17.
 */

public class HomePresenter extends BasePresenter<Void,HomeView> {

    @Inject
    AppSharedPreferences appSharedPreferences;

    private FirebaseAuth mFirebaseAuth;

    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public HomePresenter(HomeView view) {
        super(view);
    }

    @Override
    public void onCreate() {
        XatApplication.get().getComponent().plus(new ActivityModule(view.getContext())).inject(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCompositeSubscription.add(getSubscriptionNextBtn());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.clear();
    }

    private Subscription getSubscriptionNextBtn(){
        return view.getObservableLogoutBtn()
                .subscribe(__ -> {
                    mFirebaseAuth.signOut();
                    SignInActivity.start(view.getContext(),null);
                    BaseActivity.finish((Activity) view.getContext());
                });
    }
}
