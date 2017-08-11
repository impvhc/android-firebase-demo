package com.impvhc.xat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.impvhc.xat.R;
import com.impvhc.xat.presenter.SignUpPresenter;
import com.impvhc.xat.view.SignUpView;

import javax.inject.Inject;

public class SignUpActivity extends BaseActivity {

    public static void start(Context context, Bundle bundle){
        Intent intent = new Intent(context,SignUpActivity.class);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        BaseActivity.transitionLeft((Activity) context);
    }

    @Inject
    SignUpPresenter presenter;

    @Inject
    SignUpView view;

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
