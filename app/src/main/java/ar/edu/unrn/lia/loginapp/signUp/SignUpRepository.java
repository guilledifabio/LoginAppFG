package ar.edu.unrn.lia.loginapp.signUp;

import ar.edu.unrn.lia.loginapp.entities.Usuario;

/**
 * Created by Germán on 24/1/2017.
 */

public interface SignUpRepository {

    void signUp(String nombre, String apellido, String direccion, String email, int telefono, String password, String password2);
}
