package ar.edu.unrn.lia.loginapp.login.facebook;

import android.content.Intent;

import ar.edu.unrn.lia.loginapp.login.events.FacebookEvent;

/**
 * Created by Germán on 2/2/2017.
 */

public interface LoginPresenterFacebook {
    void onActivityResult (int requestCode, int resultCode, Intent data);
    void onCreate();
    void onDestroy();
    void logIn();
    void onEventMainThread(FacebookEvent event);
}
