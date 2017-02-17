package ar.edu.unrn.lia.loginapp.login.google;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ar.edu.unrn.lia.loginapp.domain.FirebaseHelper;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.login.events.GoogleEvent;
import ar.edu.unrn.lia.loginapp.model.Constants;
import ar.edu.unrn.lia.loginapp.model.User;
import ar.edu.unrn.lia.loginapp.model.User_Firebase;

/**
 * Created by Germán on 5/2/2017.
 */

public class LoginRepositoryGoogleImp implements LoginRepositoryGoogle {
    private static final String TAG = "LoginRepositoryGoogle";
    private FirebaseHelper helper;
    private DatabaseReference userReference;

    public LoginRepositoryGoogleImp(){
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void signIn(GoogleSignInAccount acct, final Context context) {
        final String email = acct.getEmail();
        final String first_name = acct.getGivenName();
        final String last_name = acct.getFamilyName();
        final String birthday = "";

        //Comprobar que usuario esté en la BD y sinó agregarlo
        userReference = helper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User_Firebase user_firebase = dataSnapshot.getValue(User_Firebase.class);
                if (user_firebase == null) { //No existe usuario con Email = email en BD
                    Log.i(TAG, "user_firebase == null");
                    helper.writeNewUser(email, first_name, last_name, "", Constants.SIGNIN_GOOGLE);
                }else{
                    Log.i(TAG, "User " + email + " is not null");
                }

                //Usuario para agregar a preferencias
                User user = User.getInstance();
                user.setBirthday(birthday);
                user.setEmail(email);
                user.setPhone("0000");
                user.setAvatarURL("");
                user.setLast_name(last_name);
                user.setName(first_name);
                user.setUsername(last_name+"_"+first_name);
                user.saveCash(context);

                postEvent(GoogleEvent.onLoginSuccess);
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.i(TAG, "onCancelled"+firebaseError.toString());
                postEvent(GoogleEvent.onLoginError, firebaseError.toString());
            }
        });
        //Fin comprobar BD
    }

    private void postEvent(int type){
        postEvent(type, null);
    }

    private void postEvent(int type, String errorMessage) {
        Log.i(TAG,"Metodo postEvent");
        GoogleEvent googleEvent = new GoogleEvent();
        googleEvent.setEventType(type);
        if (errorMessage != null) {
            googleEvent.setErrorMesage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(googleEvent);
    }
}
