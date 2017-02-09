package ar.edu.unrn.lia.loginapp.signIn;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import ar.edu.unrn.lia.loginapp.entities.User;
import ar.edu.unrn.lia.loginapp.entities.User_Table;
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
            postEvent(SignInEvent.onSignInError, "User y/o Contraseña Incorrecta");
        }
    }
/*
    @Override
    public void checkSession() {
        User usuario = SQLite.select().from(User.class).where(Usuario_Table.sesion.is(1)).querySingle();
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
        User user = SQLite.select().from(User.class).where(User_Table.email.is(email), User_Table.contraseña.is(password)).querySingle();
        if (user !=null){
            esta = true;
        }
        return esta;
    }

    private void setearEstadoUsuario(String email, int est){
        User user = SQLite.select().from(User.class).where(User_Table.email.is(email)).querySingle();
        if (user != null){
            user.setSesion(est);
            user.save();
        }
    }

    private void postEvent(int type, User user){
        postEvent(type, null, user);
    }

    private void postEvent(int type, String error) {
        postEvent(type, error, null);
    }

    private void postEvent(int type, String errorMessage, User user) {
        SignInEvent signInEvent = new SignInEvent();
        signInEvent.setEventType(type);
        if (errorMessage != null) {
            signInEvent.setErrorMesage(errorMessage);
        }else{
            if (user != null){
                signInEvent.setUser(user);
            }
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(signInEvent);
    }

    private User getUsuario(String email, String password){
        User user = SQLite.select().from(User.class).where(User_Table.email.is(email)).querySingle();
        return user;
    }
}
