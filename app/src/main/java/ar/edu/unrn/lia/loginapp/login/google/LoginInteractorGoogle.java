package ar.edu.unrn.lia.loginapp.login.google;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Germán on 5/2/2017.
 */

public interface LoginInteractorGoogle {
    void doSignIn(GoogleSignInAccount acct, Context context);
}
