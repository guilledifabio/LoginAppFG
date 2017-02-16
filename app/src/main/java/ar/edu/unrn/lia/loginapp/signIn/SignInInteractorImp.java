package ar.edu.unrn.lia.loginapp.signIn;

import android.content.Context;
import android.util.Log;

/**
 * Created by Germán on 18/1/2017.
 */

public class SignInInteractorImp implements SignInInteractor {

    private static final String TAG = "InteractorImp";
    private SignInRepository signInRepository;

    public SignInInteractorImp(){
        signInRepository = new SignInRepositoryImp();
    }

    @Override
    public void doSignIn(String email, String password, Context context) {
        signInRepository.signIn(email, password, context);
    }

}
