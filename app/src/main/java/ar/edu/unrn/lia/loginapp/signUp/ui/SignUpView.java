package ar.edu.unrn.lia.loginapp.signUp.ui;

import ar.edu.unrn.lia.loginapp.entities.Usuario;

/**
 * Created by Germán on 24/1/2017.
 */

public interface SignUpView {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSignIn();
    void handleSignUp();

    void navigateToMainScreen();
    void signUpSuccess(Usuario usuario);
    void signUpError(String error);
    void navigateToSignInScreen();
}
