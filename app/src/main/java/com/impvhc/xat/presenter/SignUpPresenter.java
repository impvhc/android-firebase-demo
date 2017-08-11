package com.impvhc.xat.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.impvhc.xat.activity.BaseActivity;
import com.impvhc.xat.activity.HomeActivity;
import com.impvhc.xat.model.User;
import com.impvhc.xat.util.ExceptionHandler;
import com.impvhc.xat.util.TextUtil;
import com.impvhc.xat.view.SignUpView;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by victor on 8/10/17.
 */

public class SignUpPresenter extends BasePresenter<Void, SignUpView> {


    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private Observable<CharSequence> mEmailObservable;
    private Observable<CharSequence> mPasswordObservable;
    private Observable<CharSequence> mNicknameObservable;

    private FirebaseAuth mFirebaseAuth;

    public SignUpPresenter(SignUpView view) {
        super(view);
    }

    @Override
    public void onCreate() {
        mEmailObservable = view.getObservableEmail();
        mPasswordObservable = view.getObservablePassword();
        mNicknameObservable = view.getObservableNickname();

        mCompositeSubscription.add(getSubscriptionSignUpBtn());
        mCompositeSubscription.add(getSubscriptionEnableSignUpBtn());
        mCompositeSubscription.add(getSubscriptionEmailErrorHandler());
        mCompositeSubscription.add(getSubscriptionPasswordErrorHandler());
        mCompositeSubscription.add(getSubscriptionNicknameErrorHandler());

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.clear();
    }

    private Observable<Boolean> checkAccountEmailObservable() {
        return Observable.create(subscriber -> {
            mFirebaseAuth.fetchProvidersForEmail(view.getEmail()).addOnCompleteListener(task -> {
                if (!task.isSuccessful())
                    subscriber.onError(task.getException());
                subscriber.onNext(task.isSuccessful());
                subscriber.onCompleted();
            });
        });
    }


    private Observable<FirebaseUser> getCreateUserObservable() {
        return Observable.create((Observable.OnSubscribe<FirebaseUser>) subscriber -> mFirebaseAuth.createUserWithEmailAndPassword(view.getEmail(), view.getPassword())
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        subscriber.onError(task.getException());
                    }else{
                        subscriber.onNext(task.getResult().getUser());
                        subscriber.onCompleted();
                    }
                }))
                .flatMap(this::getDisplayNameObservable)
                .flatMap(this::getDatabaseObservable)
                .flatMap(this::getEmailVerificationObservable);
    }

    private Observable<FirebaseUser> getDisplayNameObservable(FirebaseUser user) {
        return Observable.create(subscriber -> {
            UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(view.getNickname())
                    .build();
            user.updateProfile(changeRequest).addOnCompleteListener(task -> {
                if (!task.isSuccessful())
                    subscriber.onError(task.getException());
                subscriber.onNext(user);
                subscriber.onCompleted();
            });
        });
    }

    private Observable<FirebaseUser> getDatabaseObservable(FirebaseUser user) {
        return Observable.create(subscriber -> {
            User userLocal = new User();
            userLocal.setDisplayName(view.getNickname());
            userLocal.setEmail(view.getEmail());
            userLocal.setOrganization_id("1");
            userLocal.setOnline(true);
            userLocal.setId(user.getUid());

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            databaseReference.child(user.getUid()).setValue(userLocal).addOnCompleteListener(task -> {
                if (!task.isSuccessful())
                    subscriber.onError(task.getException());
                subscriber.onNext(user);
                subscriber.onCompleted();
            });
        });
    }

    private Observable<FirebaseUser> getEmailVerificationObservable(FirebaseUser user) {
        return Observable.create(subscriber -> {
            user.sendEmailVerification().addOnCompleteListener(task -> {
                if (!task.isSuccessful())
                    subscriber.onError(task.getException());
                subscriber.onNext(user);
                subscriber.onCompleted();
            });
        });
    }

    private void createUser() {
        Subscription subscription = getCreateUserObservable()
                .subscribe(
                        aBoolean -> {
                            view.enableSignUpBtn(true);
                            view.showLoading(false);
                            BaseActivity.finish((Activity) view.getContext());
                        },
                        throwable -> {
                            view.enableSignUpBtn(true);
                            view.showLoading(false);
                            view.showMessage(ExceptionHandler.generateError(throwable).getMessage());
                        }
                );
        mCompositeSubscription.add(subscription);
    }

    private Subscription getSubscriptionSignUpBtn() {
        return view.getObservableSignUp()
                .subscribe(__ -> {
                    view.showLoading(true);
                    view.enableSignUpBtn(false);
                    createUser();
                });
    }

    private Subscription getSubscriptionEnableSignUpBtn() {
        return Observable.combineLatest(
                mEmailObservable,
                mPasswordObservable,
                mNicknameObservable,
                (email, password, nickname) -> TextUtil.isEmailValid(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(nickname))
                .distinctUntilChanged()
                .subscribe(valid -> view.enableSignUpBtn(valid));
    }

    private Subscription getSubscriptionEmailErrorHandler() {
        return mEmailObservable
                .map(inputText -> (inputText.length() == 0) || TextUtil.isEmailValid(view.getEmail()))
                .distinctUntilChanged()
                .subscribe(isValid -> view.errorEmailHandle(isValid));
    }

    private Subscription getSubscriptionPasswordErrorHandler() {
        return mPasswordObservable
                .map(inputText -> (inputText.length() == 0 || inputText.length() >= 6))
                .distinctUntilChanged()
                .subscribe(isValid -> view.errorPasswordHandle(isValid));
    }

    private Subscription getSubscriptionNicknameErrorHandler() {
        return mNicknameObservable
                .map(inputText -> (inputText.length() >= 0))
                .distinctUntilChanged()
                .subscribe(isValid -> view.errorPasswordHandle(isValid));
    }
}
