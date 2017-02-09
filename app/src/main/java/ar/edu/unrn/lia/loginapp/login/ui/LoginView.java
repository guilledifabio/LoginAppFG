package ar.edu.unrn.lia.loginapp.login.ui;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import ar.edu.unrn.lia.loginapp.model.User;


/**
 * Created by Germán on 2/2/2017.
 */

public interface LoginView {
    //void guardarEnPreferencias(User user);

    void navigateToMainScreen();

    Context getContext();

    void signInErrorFacebook(String error);

    //void signInSuccessGoogle(User user);

    void signInErrorGoogle(String error);

    void specifyGoogleSignIn(GoogleSignInOptions gso);

    void enableInputs();

    void disableInputs();
}
