package ar.edu.unrn.lia.loginapp.signUp;

import android.content.Context;

/**
 * Created by Germán on 24/1/2017.
 */

public interface SignUpInteractor {
    void doSignUp(String name, String last_name, String address, String email, String birthday, String celphone, String password, String reenter_password, Context context);
}
