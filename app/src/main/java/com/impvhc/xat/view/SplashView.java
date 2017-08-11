package com.impvhc.xat.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.ProgressBar;

import com.impvhc.xat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by victor on 8/7/17.
 */

public class SplashView extends ConstraintLayout {
    @BindView(R.id.splash_progressBar)
    ProgressBar splashProgressBar;

    public SplashView(Context context) {
        super(context);
        inflate(getContext(), R.layout.activity_splash, this);
        ButterKnife.bind(this);
    }
}
