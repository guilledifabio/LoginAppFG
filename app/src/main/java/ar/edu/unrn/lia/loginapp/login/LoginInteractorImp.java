package ar.edu.unrn.lia.loginapp.login;

import android.util.Log;

/**
 * Created by Germán on 6/2/2017.
 */

public class LoginInteractorImp implements LoginInteractor {
    private static final String TAG = "LoginInteractorImp";
    private LoginRepository loginRepository;

    public LoginInteractorImp(){
        loginRepository = new LoginRepositoryImp();
    }

    @Override
    public void checkSession() {
        Log.i(TAG, "CheckForAuth");
        loginRepository.checkSession();
    }
}
