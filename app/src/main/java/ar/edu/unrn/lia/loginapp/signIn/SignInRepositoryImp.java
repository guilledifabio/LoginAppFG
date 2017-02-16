package ar.edu.unrn.lia.loginapp.signIn;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ar.edu.unrn.lia.loginapp.domain.FirebaseHelper;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.model.User;
import ar.edu.unrn.lia.loginapp.model.User_Firebase;
import ar.edu.unrn.lia.loginapp.signIn.events.SignInEvent;

/**
 * Created by Germán on 18/1/2017.
 */

public class SignInRepositoryImp implements SignInRepository {
    private static final String TAG = "RepositoryImp";
    private FirebaseHelper helper;
    private DatabaseReference userReference;

    public SignInRepositoryImp(){
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void signIn(final String email, final String password, final Context context) {
        userReference = helper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onDataChange");
                /*
                Log.i(TAG, dataSnapshot.getKey().toString());
                Log.i(TAG, dataSnapshot.toString());
                Log.i(TAG, String.valueOf(dataSnapshot.getChildrenCount()));
                Log.i(TAG, String.valueOf(dataSnapshot.child("contraseña").getValue().toString()));
*/
                User_Firebase user_firebase = dataSnapshot.getValue(User_Firebase.class);

                if (user_firebase == null) {
                    Log.i(TAG, "User " + email + " is unexpectedly null");
                    postEvent(SignInEvent.onSignInError, "User " + email + " is unexpectedly null");
                }else{
                    if (password.equals(dataSnapshot.child("contraseña").getValue().toString())){
                        User user = User.getInstance();
                        user.setAvatarURL("");
                        user.setBirthday("");
                        user.setEmail(email);
                        user.setAvatarURL("");
                        user.setLast_name(dataSnapshot.child("apellido").getValue().toString());
                        user.setName(dataSnapshot.child("nombre").getValue().toString());

                        user.setUsername("");

                        user.saveCash(context);
                        postEvent(SignInEvent.onSignInSuccess);
                    }else{
                        postEvent(SignInEvent.onSignInError, "Contraseña Incorrecta");
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.i(TAG, "onCancelled");
                postEvent(SignInEvent.onSignInError);
            }
        });
    }

    private void postEvent(int type){
        postEvent(type, null);
    }

    private void postEvent(int type, String errorMessage) {
        SignInEvent signInEvent = new SignInEvent();
        signInEvent.setEventType(type);
        if (errorMessage != null) {
            signInEvent.setErrorMesage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(signInEvent);
    }

}
