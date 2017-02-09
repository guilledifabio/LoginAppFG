package ar.edu.unrn.lia.loginapp.login.google;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import ar.edu.unrn.lia.loginapp.login.facebook.LoginInteractorFacebookImp;

/**
 * Created by Germán on 5/2/2017.
 */

public class LoginInteractorGoogleImp implements LoginInteractorGoogle {
    private LoginRepositoryGoogle loginRepositoryGoogle;

    public LoginInteractorGoogleImp(){
        this.loginRepositoryGoogle = new LoginRepositoryGoogleImp();
    }

    @Override
    public void doSignIn(GoogleSignInAccount acct, Context context) {
        loginRepositoryGoogle.signIn(acct, context);
    }
}
