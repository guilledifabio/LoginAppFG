package ar.edu.unrn.lia.loginapp.signUp;

import android.content.Context;

/**
 * Created by Germán on 24/1/2017.
 */

public class SignUpInteractorImp implements SignUpInteractor {
    private SignUpRepository signupRepository;

    public SignUpInteractorImp(){
        signupRepository = new SignUpRepositoryImp();
    }

    @Override
    public void doSignUp(String nombre, String apellido, String direccion, String email, String telefono, String password, String password2, Context context) {
        signupRepository.signUp(nombre, apellido, direccion, email, telefono, password, password2, context);
    }

}
