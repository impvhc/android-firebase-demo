package com.impvhc.xat.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.widget.Button;

import com.impvhc.xat.R;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by victor on 8/7/17.
 */

public class HomeView extends ConstraintLayout {
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.logout)
    Button logout;


    public HomeView(Context context) {
        super(context);
        inflate(getContext(), R.layout.activity_home, this);
        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public Observable<Void> getObservableLogoutBtn() {
        return RxView.clicks(logout);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                return true;
            case R.id.navigation_dashboard:
                return true;
            case R.id.navigation_notifications:
                return true;
        }
        return false;
    };
}
