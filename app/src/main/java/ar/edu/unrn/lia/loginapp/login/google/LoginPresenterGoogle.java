package ar.edu.unrn.lia.loginapp.login.google;

import android.content.Intent;

import ar.edu.unrn.lia.loginapp.login.events.GoogleEvent;
import ar.edu.unrn.lia.loginapp.login.ui.LoginActivity;

/**
 * Created by Germán on 3/2/2017.
 */

public interface LoginPresenterGoogle {
    void createGoogleClient (LoginActivity loginView);
    void onCreate();
    void onStart();
    void signIn(LoginActivity loginView);
    void onActivityResult (LoginActivity loginView,int requestCode, int resultCode, Intent data);
    void onStop ();
    void onDestroy();
    void onEventMainThread(GoogleEvent event);
}
