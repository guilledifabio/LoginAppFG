package ar.edu.unrn.lia.loginapp.signUp;

import android.content.Context;

/**
 * Created by Germán on 24/1/2017.
 */

public interface SignUpInteractor {
    void doSignUp(String nombre, String apellido, String direccion, String email, String telefono, String password, String password2, Context context);
}
