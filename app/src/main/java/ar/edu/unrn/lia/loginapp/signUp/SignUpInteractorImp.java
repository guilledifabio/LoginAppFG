package ar.edu.unrn.lia.loginapp.signUp;

import ar.edu.unrn.lia.loginapp.entities.Usuario;

/**
 * Created by Germán on 24/1/2017.
 */

public class SignUpInteractorImp implements SignUpInteractor {
    private SignUpRepository signupRepository;

    public SignUpInteractorImp(){
        signupRepository = new SignUpRepositoryImp();
    }

    @Override
    public void doSignUp(String nombre, String apellido, String direccion, String email, int telefono, String password, String password2) {
        signupRepository.signUp(nombre, apellido, direccion, email, telefono, password, password2);
    }

}
