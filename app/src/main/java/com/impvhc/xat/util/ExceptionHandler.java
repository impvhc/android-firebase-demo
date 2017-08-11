package com.impvhc.xat.util;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;

/**
 * Created by victor on 8/10/17.
 */

public class ExceptionHandler extends RuntimeException{

    String message;

    String code;

    int status;

    public ExceptionHandler() {
    }

    public static ExceptionHandler generateError(Throwable cause) {
        ExceptionHandler exceptionHandler;
        if(cause instanceof FirebaseAuthUserCollisionException){
            FirebaseAuthUserCollisionException error = (FirebaseAuthUserCollisionException) cause;
            exceptionHandler = new ExceptionHandler();
            exceptionHandler.setCode(error.getErrorCode());
            exceptionHandler.setMessage(error.getMessage());
        }else{
            // Non-Retrofit error
            exceptionHandler = new ExceptionHandler();
            String message = cause.getMessage() != null ? cause.getMessage() : cause.getClass().getSimpleName();
            exceptionHandler.message = message;
        }
        return exceptionHandler;
    }



    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
