package ar.edu.unrn.lia.loginapp.signUp;

import ar.edu.unrn.lia.loginapp.signUp.events.SignUpEvent;

/**
 * Created by Germán on 24/1/2017.
 */

public interface SignUpPresenter {
    void onCreate();
    void onDestroy();
    void registerNewUser(String nombre, String apellido, String direccion, String email, int telefono, String password, String password2);
    public void onEventMainThread(SignUpEvent event);
}
