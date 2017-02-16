package ar.edu.unrn.lia.loginapp.login.google;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.login.events.GoogleEvent;
import ar.edu.unrn.lia.loginapp.model.User;

/**
 * Created by Germán on 5/2/2017.
 */

public class LoginRepositoryGoogleImp implements LoginRepositoryGoogle {
    private static final String TAG = "LoginRepositoryGoogle";

    public LoginRepositoryGoogleImp(){

    }

    @Override
    public void signIn(GoogleSignInAccount acct, Context context) {
        String email = acct.getEmail();

        //Comprobar que usuario esté en la BD y sinó agregarlo
        //Firebase
        //Fin comprobar BD

        String first_name = acct.getGivenName();
        String last_name = acct.getFamilyName();
//        String profilePicUrl = acct.getPhotoUrl().toString();
        String birthday = "";

        User user = User.getInstance();

        user.setBirthday(birthday);
        user.setEmail(email);
        user.setPhone("0000");
        user.setAvatarURL("");
        user.setLast_name(last_name);
        user.setName(first_name);
        String userName = new StringBuilder().append(last_name).append(" ").append(first_name).toString();
        user.setUsername(userName);

        user.saveCash(context);
        postEvent(GoogleEvent.onLoginSuccess, null, user);
    }

    private void postEvent(int type, String errorMessage, User user) {
        Log.i(TAG,"Metodo postEvent");
        GoogleEvent googleEvent = new GoogleEvent();
        googleEvent.setEventType(type);
        if (errorMessage != null) {
            googleEvent.setErrorMesage(errorMessage);
        }else{
            if (user != null){
                googleEvent.setUser(user);
            }
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(googleEvent);
    }
}
