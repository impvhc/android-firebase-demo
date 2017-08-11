package com.impvhc.xat.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import com.impvhc.xat.R;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by victor on 8/10/17.
 */

public class SignUpView extends CoordinatorLayout {

    @BindView(R.id.nickname_et)
    EditText nicknameEt;
    @BindView(R.id.nickname_til)
    TextInputLayout nicknameTil;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.email_til)
    TextInputLayout emailTil;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.password_til)
    TextInputLayout passwordTil;
    @BindView(R.id.sign_up_btn)
    Button signUpBtn;

    private final ProgressDialog progressDialog = new ProgressDialog(getContext());

    public SignUpView(Context context) {
        super(context);
        inflate(getContext(), R.layout.activity_sign_up, this);
        ButterKnife.bind(this);
        progressDialog.setMessage("Validating User");
    }

    public Observable<Void> getObservableSignUp() {
        return RxView.clicks(signUpBtn);
    }

    public Observable<CharSequence> getObservableNickname() {
        return RxTextView.textChanges(nicknameEt);
    }

    public Observable<CharSequence> getObservableEmail() {
        return RxTextView.textChanges(emailEt);
    }

    public Observable<CharSequence> getObservablePassword() {
        return RxTextView.textChanges(passwordEt);
    }

    public void errorNicknameHandle(boolean status) {
        nicknameTil.setError("Invalid Email");
        nicknameTil.setErrorEnabled(!status);
    }

    public void errorEmailHandle(boolean status) {
        emailTil.setError("Invalid Email");
        emailTil.setErrorEnabled(!status);
    }

    public void errorPasswordHandle(boolean status) {
        passwordTil.setError("6 character minimum");
        passwordTil.setErrorEnabled(!status);
    }

    public void enableSignUpBtn(boolean value) {
        signUpBtn.setEnabled(value);
    }

    public String getEmail() {
        return emailEt.getText().toString();
    }

    public String getPassword() {
        return passwordEt.getText().toString();
    }

    public String getNickname() {
        return nicknameEt.getText().toString();
    }

    public void showLoading(boolean loading) {
        if (loading) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }
    public void showMessage(String message){
        Snackbar.make(this,message,Snackbar.LENGTH_LONG).show();
    }
}
