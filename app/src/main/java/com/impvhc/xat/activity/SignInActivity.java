package com.impvhc.xat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.impvhc.xat.presenter.SignInPresenter;
import com.impvhc.xat.view.SignInView;

import javax.inject.Inject;

public class SignInActivity extends BaseActivity {

    public static void start(Context context, Bundle bundle){
        Intent intent = new Intent(context,SignInActivity.class);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        BaseActivity.transitionLeft((Activity) context);
    }

    @Inject
    SignInView view;

    @Inject
    SignInPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(this).inject(this);
        setContentView(view);
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
