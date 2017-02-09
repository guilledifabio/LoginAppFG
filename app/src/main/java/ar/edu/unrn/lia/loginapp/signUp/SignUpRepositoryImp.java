package ar.edu.unrn.lia.loginapp.signUp;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import ar.edu.unrn.lia.loginapp.entities.User;
import ar.edu.unrn.lia.loginapp.entities.User_Table;
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
            User user = crearUsuario(nombre, apellido,direccion, email, telefono, password);
            setearEstadoUsuario(email, 1);

            postEvent(SignUpEvent.onSignUpSuccess, user);
        }else{
            postEvent(SignUpEvent.onSignUpError,"Ya existe usuario");
        }
    }

    private boolean existeUsuario(String email, String password){
        boolean esta = false;
        User user = SQLite.select().from(User.class).where(User_Table.email.is(email), User_Table.contraseña.is(password)).querySingle();
        if (user !=null){
            esta = true;
        }
        return esta;
    }

    private User crearUsuario(String nombre, String apellido, String direccion, String email, int telefono, String password) {
        User user = new User(nombre, apellido,direccion, email, telefono, password, true, 1);
        user.save();
        return user;
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
        SignUpEvent signUpEvent = new SignUpEvent();
        signUpEvent.setEventType(type);
        if (errorMessage != null) {
            signUpEvent.setErrorMesage(errorMessage);
        }else{
            if (user != null){
                signUpEvent.setUser(user);
            }
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(signUpEvent);
    }

}
