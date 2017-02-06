package ar.edu.unrn.lia.loginapp.login.ui;

import android.content.Context;

import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import ar.edu.unrn.lia.loginapp.entities.Usuario;

/**
 * Created by Germán on 2/2/2017.
 */

public interface LoginView {
    void guardarEnPreferencias(Usuario u);
    void navigateToMainScreen();
    Context getContext();
    void signInSuccessFacebook(Usuario usuario);
    void signInErrorFacebook(String error);
    void signInSuccessGoogle(Usuario usuario);
    void signInErrorGoogle(String error);
    void specifyGoogleSignIn(GoogleSignInOptions gso);
    void enableInputs();
    void disableInputs();
}
