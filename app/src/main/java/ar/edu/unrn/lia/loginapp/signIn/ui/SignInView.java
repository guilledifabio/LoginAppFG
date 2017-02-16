package ar.edu.unrn.lia.loginapp.signIn.ui;

import android.content.Context;

import ar.edu.unrn.lia.loginapp.model.User;

/**
 * Created by Germán on 18/1/2017.
 */

public interface SignInView {

    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSignIn();
    void handleSignUp();

    void navigateToMainScreen();
    void loginError(String error);

    void signInSuccess();

    void navigateToSignUpScreen();

    Context getContext();
}
