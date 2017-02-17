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
    public void doSignUp(String name, String last_name, String address, String email, String birthday, String celphone, String password, String reenter_password, Context context) {
        signupRepository.signUp(name, last_name, address, email, birthday, celphone, password, reenter_password, context);
    }

}
