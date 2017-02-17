package ar.edu.unrn.lia.loginapp.signIn;

import android.content.Context;

/**
 * Created by Germán on 18/1/2017.
 */

public interface SignInInteractor {
    void doSignIn(String email, String password, Context context);
}
