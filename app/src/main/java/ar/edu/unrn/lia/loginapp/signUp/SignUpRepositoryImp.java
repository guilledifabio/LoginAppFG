package ar.edu.unrn.lia.loginapp.signUp;

import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import ar.edu.unrn.lia.loginapp.R;
import ar.edu.unrn.lia.loginapp.entities.Usuario;
import ar.edu.unrn.lia.loginapp.entities.Usuario_Table;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.signUp.events.SignUpEvent;

/**
 * Created by Germán on 24/1/2017.
 */

public class SignUpRepositoryImp implements SignUpRepository {
    @Override
    public void signUp(String nombre, String apellido, String direccion, String email, int telefono, String password, String password2) {
        if (!existeUsuario(email,password)){
            Usuario usuario = crearUsuario(nombre, apellido,direccion, email, telefono, password);
            setearEstadoUsuario(email, 1);

            postEvent(SignUpEvent.onSignUpSuccess, usuario);
        }else{
            postEvent(SignUpEvent.onSignUpError,"Ya existe usuario");
        }
    }

    private boolean existeUsuario(String email, String password){
        boolean esta = false;
        Usuario usuario = SQLite.select().from(Usuario.class).where(Usuario_Table.email.is(email), Usuario_Table.contraseña.is(password)).querySingle();
        if (usuario !=null){
            esta = true;
        }
        return esta;
    }

    private Usuario crearUsuario(String nombre, String apellido, String direccion, String email, int telefono, String password) {
        Usuario usuario = new Usuario(nombre, apellido,direccion, email, telefono, password, true, 1);
        usuario.save();
        return usuario;
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
        SignUpEvent signUpEvent = new SignUpEvent();
        signUpEvent.setEventType(type);
        if (errorMessage != null) {
            signUpEvent.setErrorMesage(errorMessage);
        }else{
            if (usuario != null){
                signUpEvent.setUsuario(usuario);
            }
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(signUpEvent);
    }

}
