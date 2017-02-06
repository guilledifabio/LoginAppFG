package ar.edu.unrn.lia.loginapp.login;

import ar.edu.unrn.lia.loginapp.login.events.LoginEvent;

/**
 * Created by Germán on 6/2/2017.
 */

public interface LoginPresenter {
    void checkForAuthenticatedUser();
    void onCreate();
    void onDestroy();
    void onEventMainThread(LoginEvent event);
}
