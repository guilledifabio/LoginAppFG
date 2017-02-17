package ar.edu.unrn.lia.loginapp.login;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import ar.edu.unrn.lia.loginapp.domain.FirebaseHelper;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.login.events.LoginEvent;
import ar.edu.unrn.lia.loginapp.model.Constants;
import ar.edu.unrn.lia.loginapp.model.User;

/**
 * Created by Germán on 6/2/2017.
 */

public class LoginRepositoryImp implements LoginRepository {
    private static final String TAG = "LoginRepositoryImp";

    public LoginRepositoryImp() {
    }

    @Override
    public void checkSession() {
        User user = User.getInstance();
        Log.i(TAG, "CheckForAuth ");
        Log.i(TAG, "email --> "+user.getEmail() );
        if (user.getEmail() != null) {
            Log.i(TAG, "checkSesionSuccess");
            postEvent(LoginEvent.onSuccessToRecoverSession, user);
        } else {
            Log.i(TAG, "checkSesionFailed");
            postEvent(LoginEvent.onFailedToRecoverSession, "No hay user logueado");
        }
    }

    private void postEvent(int type, User user) {
        postEvent(type, null, user);
    }

    private void postEvent(int type, String msg) {
        postEvent(type, msg, null);
    }

    private void postEvent(int type, String errorMessage, User user) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMesage(errorMessage);
        } else {
            if (user != null) {
                loginEvent.setUser(user);
            }
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }
}
