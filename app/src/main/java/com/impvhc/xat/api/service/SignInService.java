package com.impvhc.xat.api.service;

import com.impvhc.xat.api.client.SignInClient;
import com.impvhc.xat.model.User;

import rx.Observable;

/**
 * Created by victor on 8/7/17.
 */

public class SignInService {
    private SignInClient mSignInClient;

    public SignInService(SignInClient mSignInClient) {
        this.mSignInClient = mSignInClient;
    }

    public Observable<User> signIn (String username, String password){
        return mSignInClient.signIn(username,password);
    }
}
