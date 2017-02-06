package ar.edu.unrn.lia.loginapp.login;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import ar.edu.unrn.lia.loginapp.entities.Usuario;
import ar.edu.unrn.lia.loginapp.entities.Usuario_Table;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.login.events.LoginEvent;

/**
 * Created by Germán on 6/2/2017.
 */

public class LoginRepositoryImp implements LoginRepository {
    private static final String TAG = "LoginRepositoryImp";

    public LoginRepositoryImp(){
    }

    @Override
    public void checkSession() {
        Usuario usuario = SQLite.select().from(Usuario.class).where(Usuario_Table.sesion.is(1)).querySingle();
        Log.i(TAG, "CheckForAuth ");
        if (usuario != null){
            postEvent(LoginEvent.onSuccessToRecoverSession, usuario);
            Log.i(TAG, "checkSesionSuccess");
        }else{
            postEvent(LoginEvent.onFailedToRecoverSession, "No hay usuario logueado");
            Log.i(TAG, "checkSesionFailed");
        }
    }

    private void postEvent(int type, Usuario usuario){
        postEvent(type, null, usuario);
    }

    private void postEvent(int type, String msg) {
        postEvent(type, msg, null);
    }

    private void postEvent(int type, String errorMessage, Usuario usuario) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMesage(errorMessage);
        }else{
            if (usuario != null){
                loginEvent.setUsuario(usuario);
            }
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }
}
