package ar.edu.unrn.lia.loginapp.login;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

//import ar.edu.unrn.lia.loginapp.entities.User;
//import ar.edu.unrn.lia.loginapp.entities.Usuario_Table;
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
        // User user = SQLite.select().from(User.class).where(Usuario_Table.sesion.is(1)).querySingle();
        User user = User.getInstance();
     //   user.readCash(this); Leer datos de preferencias
        Log.i(TAG, "CheckForAuth ");
        if (user.getLogin_status()== Constants.ONLINE) {
            postEvent(LoginEvent.onSuccessToRecoverSession, user);
            Log.i(TAG, "checkSesionSuccess");
        } else {
            postEvent(LoginEvent.onFailedToRecoverSession, "No hay user logueado");
            Log.i(TAG, "checkSesionFailed");
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
