package com.impvhc.xat.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.impvhc.xat.AppSharedPreferences;
import com.impvhc.xat.XatApplication;
import com.impvhc.xat.activity.BaseActivity;
import com.impvhc.xat.activity.HomeActivity;
import com.impvhc.xat.activity.SignUpActivity;
import com.impvhc.xat.api.service.SignInService;
import com.impvhc.xat.inject.activty.ActivityModule;
import com.impvhc.xat.model.User;
import com.impvhc.xat.util.ExceptionHandler;
import com.impvhc.xat.util.TextUtil;
import com.impvhc.xat.util.ViewUtil;
import com.impvhc.xat.view.SignInView;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by victor on 8/6/17.
 */

public class SignInPresenter extends BasePresenter<Void,SignInView> {

    @Inject
    AppSharedPreferences appSharedPreferences;

    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private Observable<CharSequence> mEmailObservable;
    private Observable<CharSequence> mPasswordObservable;

    private FirebaseAuth mFirebaseAuth;

    public SignInPresenter(SignInView view) {
        super(view);
    }

    @Override
    public void onCreate() {
        XatApplication.get().getComponent().plus(new ActivityModule(view.getContext())).inject(this);
        mFirebaseAuth = FirebaseAuth.getInstance();

        mEmailObservable = view.getObservableEmail();
        mPasswordObservable = view.getObservablePassword();

        mCompositeSubscription.add(getSubscriptionNextBtn());
        mCompositeSubscription.add(getSubscriptionSignUpBtn());
        mCompositeSubscription.add(getSubscriptionEnableSignInBtn());
        mCompositeSubscription.add(getSubscriptionEmailErrorHandler());
        mCompositeSubscription.add(getSubscriptionPasswordErrorHandler());

        setEmail();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.clear();
    }

    private void setEmail(){
        if(mFirebaseAuth.getCurrentUser()!=null)
            view.setEmail(mFirebaseAuth.getCurrentUser().getEmail());
    }

    private Subscription getSubscriptionNextBtn(){
        return view.getObservableNextBtn()
                .doOnNext(__ -> view.showLoading(true))
                .subscribe(__ -> {
                    view.enableSignInBtn(false);
                    ViewUtil.hideKeyboard(view.getContext(),view.getWindowToken());
                    mCompositeSubscription.add(getSignInSubscription());
                });
    }

    private Subscription getSubscriptionSignUpBtn(){
        return view.getObservableSignUp()
                .subscribe(__ -> {
                    SignUpActivity.start(view.getContext(),null);
                });
    }

    private Subscription getSubscriptionEnableSignInBtn(){
        return Observable.combineLatest(
                mEmailObservable,
                mPasswordObservable,
                (email,password) -> TextUtil.isEmailValid(email) && !TextUtils.isEmpty(password))
                .distinctUntilChanged()
                .subscribe(valid -> view.enableSignInBtn(valid));
    }

    private Subscription getSubscriptionEmailErrorHandler(){
        return mEmailObservable
                .map(inputText -> (inputText.length() == 0) || TextUtil.isEmailValid(view.getEmail()))
                .distinctUntilChanged()
                .subscribe(isValid -> view.errorEmailHandle(isValid));
    }
    private Subscription getSubscriptionPasswordErrorHandler(){
        return mPasswordObservable
                .map(inputText -> (inputText.length() >= 0))
                .distinctUntilChanged()
                .subscribe(isValid -> view.errorPasswordHandle(isValid));
    }

    private Subscription getSignInSubscription(){
        return getSignInObservable()
                .subscribe(
                        aBoolean -> {
                            HomeActivity.start(view.getContext(),null);
                            BaseActivity.finish((Activity) view.getContext());},
                        throwable -> view.showMessage(ExceptionHandler.generateError(throwable).getMessage()));
    }

    private Observable<Boolean> getSignInObservable(){
        return Observable.create(subscriber -> {
            mFirebaseAuth.signInWithEmailAndPassword(view.getEmail(),view.getPassword()).addOnCompleteListener(task -> {
                view.enableSignInBtn(true);
                view.showLoading(false);
                if(!task.isSuccessful()){
                    subscriber.onError(task.getException());
                }else{
                    FirebaseUser user = task.getResult().getUser();
                    if(!user.isEmailVerified()){
                        ExceptionHandler exceptionHandler = new ExceptionHandler();
                        exceptionHandler.setMessage("Email not verified");
                        exceptionHandler.setCode("2");
                        subscriber.onError(exceptionHandler);
                    }else{
                        subscriber.onNext(task.isSuccessful());
                        subscriber.onCompleted();
                    }
                }
            });
        });
    }

}
