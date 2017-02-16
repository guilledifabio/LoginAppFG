package ar.edu.unrn.lia.loginapp.login;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raizlabs.android.dbflow.sql.language.SQLite;

//import ar.edu.unrn.lia.loginapp.entities.User;
//import ar.edu.unrn.lia.loginapp.entities.Usuario_Table;
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

    DatabaseReference dbPrediccion;
    private ValueEventListener eventListener;

    public LoginRepositoryImp() {
        //helper = FirebaseHelper.getInstance();
        //dataReference = helper.getDataReference();
    }

    @Override
    public void checkSession() {
/*
        //Firebase
        dbPrediccion =
                FirebaseDatabase.getInstance().getReference()
                        .child("prediccion-hoy");

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onDataChange");
                Log.i(TAG, dataSnapshot.child("cielo").getValue().toString());
                Log.i(TAG, dataSnapshot.child("temperatura").getValue().toString());
                Log.i(TAG, dataSnapshot.child("humedad").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Error!", databaseError.toException());
            }
        };
        dbPrediccion.addValueEventListener(eventListener);
        //Fin Firebase
*/
        User user = User.getInstance();
        Log.i(TAG, "CheckForAuth ");
        Log.i(TAG, "email --> "+user.getEmail() );
        if (user.getEmail() != null) {
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
