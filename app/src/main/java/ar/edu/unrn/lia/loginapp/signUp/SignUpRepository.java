package ar.edu.unrn.lia.loginapp.signUp;

import android.content.Context;

/**
 * Created by Germán on 24/1/2017.
 */

public interface SignUpRepository {

    void signUp(final String name, final String last_name, final String address, final String email, final String birthday, final String celphone, final String password, String reenter_password, final Context context) ;
}
