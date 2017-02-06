package ar.edu.unrn.lia.loginapp.signIn;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import ar.edu.unrn.lia.loginapp.entities.Usuario;
import ar.edu.unrn.lia.loginapp.entities.Usuario_Table;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.signIn.events.SignInEvent;

/**
 * Created by Germán on 18/1/2017.
 */

public class SignInRepositoryImp implements SignInRepository {
    private static final String TAG = "RepositoryImp";
    public SignInRepositoryImp(){

    }

    @Override
    public void signIn(String email, String password) {
        if (existeUsuario(email,password)){
            setearEstadoUsuario(email, 1);
            postEvent(SignInEvent.onSignInSuccess, getUsuario(email, password));
        }else{
            postEvent(SignInEvent.onSignInError, "Usuario y/o Contraseña Incorrecta");
        }
    }
/*
    @Override
    public void checkSession() {
        Usuario usuario = SQLite.select().from(Usuario.class).where(Usuario_Table.sesion.is(1)).querySingle();
        Log.i(TAG, "CheckForAuth ");
        if (usuario != null){
            postEvent(SignInEvent.onSuccessToRecoverSession);
            Log.i(TAG, "checkSesionSuccess");
        }else{
            postEvent(SignInEvent.onFailedToRecoverSession);
            Log.i(TAG, "checkSesionFailed");
        }
    }*/

    private boolean existeUsuario(String email, String password){
        boolean esta = false;
        Usuario usuario = SQLite.select().from(Usuario.class).where(Usuario_Table.email.is(email), Usuario_Table.contraseña.is(password)).querySingle();
        if (usuario !=null){
            esta = true;
        }
        return esta;
    }

    private void setearEstadoUsuario(String email, int est){
        Usuario usuario = SQLite.select().from(Usuario.class).where(Usuario_Table.email.is(email)).querySingle();
        if (usuario != null){
            usuario.setSesion(est);
            usuario.save();
        }
    }

    private void postEvent(int type, Usuario usuario){
        postEvent(type, null, usuario);
    }

    private void postEvent(int type, String error) {
        postEvent(type, error, null);
    }

    private void postEvent(int type, String errorMessage, Usuario usuario) {
        SignInEvent signInEvent = new SignInEvent();
        signInEvent.setEventType(type);
        if (errorMessage != null) {
            signInEvent.setErrorMesage(errorMessage);
        }else{
            if (usuario != null){
                signInEvent.setUsuario(usuario);
            }
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(signInEvent);
    }

    private Usuario getUsuario(String email, String password){
        Usuario usuario = SQLite.select().from(Usuario.class).where(Usuario_Table.email.is(email)).querySingle();
        return usuario;
    }
}