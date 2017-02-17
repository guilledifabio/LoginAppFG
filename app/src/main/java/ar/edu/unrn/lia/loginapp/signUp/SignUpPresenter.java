package ar.edu.unrn.lia.loginapp.signUp;

import ar.edu.unrn.lia.loginapp.signUp.events.SignUpEvent;

/**
 * Created by Germán on 24/1/2017.
 */

public interface SignUpPresenter {
    void onCreate();
    void onDestroy();
    void registerNewUser(String name, String last_name, String address, String email, String birthday, String celphone, String password, String reenter_password);
    public void onEventMainThread(SignUpEvent event);
}
