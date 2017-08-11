package com.impvhc.xat.util;

/**
 * Created by victor on 8/6/17.
 */

public class TextUtil {
    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
