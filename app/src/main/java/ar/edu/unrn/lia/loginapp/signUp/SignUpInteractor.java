package ar.edu.unrn.lia.loginapp.signUp;

/**
 * Created by Germán on 24/1/2017.
 */

public interface SignUpInteractor {
    void doSignUp(String nombre, String apellido, String direccion, String email, int telefono, String password, String password2);
}
