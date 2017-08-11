package com.impvhc.xat.inject.application;

import com.impvhc.xat.api.client.SignInClient;
import com.impvhc.xat.api.service.SignInService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by victor on 8/7/17.
 */

@Module
public class ApiModule {
    @AppScope
    @Provides
    public SignInService provideSignInService(SignInClient signInClient) {
        return new SignInService(signInClient);
    }

    @AppScope
    @Provides
    public SignInClient provideSignInClient(Retrofit retrofit) {
        return retrofit.create(SignInClient.class);
    }
}
