package ar.edu.unrn.lia.loginapp.login.google;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import ar.edu.unrn.lia.loginapp.entities.Usuario;
import ar.edu.unrn.lia.loginapp.entities.Usuario_Table;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.login.events.FacebookEvent;
import ar.edu.unrn.lia.loginapp.login.events.GoogleEvent;

/**
 * Created by Germán on 5/2/2017.
 */

public class LoginRepositoryGoogleImp implements LoginRepositoryGoogle {
    private static final String TAG = "LoginRepositoryGoogle";

    public LoginRepositoryGoogleImp(){

    }

    @Override
    public void signIn(GoogleSignInAccount acct) {
        String email = acct.getEmail();
        String first_name = acct.getGivenName();
        String last_name = acct.getFamilyName();
        Usuario usuario = new Usuario(first_name, last_name, null, email, 0, null, true, 1);

        usuario.save();

        Log.i(TAG, "Emal en GraphRequest" + email);

        setearEstadoUsuario(email, 1);
        postEvent(GoogleEvent.onLoginSuccess, null, usuario);
    }

    private void setearEstadoUsuario(String email, int est){
        Usuario usuario = SQLite.select().from(Usuario.class).where(Usuario_Table.email.is(email)).querySingle();
        if (usuario != null){
            usuario.setSesion(est);
            usuario.save();
        }
    }

    private void postEvent(int type, String errorMessage, Usuario usuario) {
        Log.i(TAG,"Metodo postEvent");
        GoogleEvent loginEvent = new GoogleEvent();
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
