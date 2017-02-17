package ar.edu.unrn.lia.loginapp.signIn;

import ar.edu.unrn.lia.loginapp.signIn.events.SignInEvent;

/**
 * Created by Germán on 18/1/2017.
 */

public interface SignInPresenter {
    void onCreate();
    void onDestroy();
    void validateLogin(String email, String password);
    public void onEventMainThread(SignInEvent event);
}
