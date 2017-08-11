package com.impvhc.xat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.impvhc.xat.R;
import com.impvhc.xat.XatApplication;
import com.impvhc.xat.inject.activty.ActivityComponent;
import com.impvhc.xat.inject.activty.ActivityModule;

/**
 * Created by victor on 8/6/17.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getComponent(Activity activity){
        return XatApplication.get()
                .getComponent()
                .plus(new ActivityModule(activity));
    }

    public static void finish(Activity activity){
        activity.finish();
    }

    public static void transitionLeft(Activity activity){
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void transitionRight(Activity activity){
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        transitionRight(this);
    }
}
