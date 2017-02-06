package ar.edu.unrn.lia.loginapp.login.facebook;


import android.util.Log;

/**
 * Created by Germán on 4/2/2017.
 */

public class LoginInteractorFacebookImp implements LoginInteractorFacebook {

    private static final String TAG = "InteractorImp";
    private LoginRepositoryFacebookImp loginRepositoryFacebook;

    public LoginInteractorFacebookImp(){
        loginRepositoryFacebook = new LoginRepositoryFacebookImp();
    }

    @Override
    public void doSignIn() {
        Log.i(TAG,"Metodo doSignIn LoginInteractorFacebookImp");
        loginRepositoryFacebook.signIn();
    }

}
