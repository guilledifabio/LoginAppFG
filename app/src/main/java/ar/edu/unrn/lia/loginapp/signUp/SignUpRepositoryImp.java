package ar.edu.unrn.lia.loginapp.signUp;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ar.edu.unrn.lia.loginapp.domain.FirebaseHelper;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.model.Constants;
import ar.edu.unrn.lia.loginapp.model.User;
import ar.edu.unrn.lia.loginapp.model.User_Firebase;
import ar.edu.unrn.lia.loginapp.signUp.events.SignUpEvent;

/**
 * Created by Germán on 24/1/2017.
 */

public class SignUpRepositoryImp implements SignUpRepository {
    private static final String TAG = "SignUpRepositoryImp";
    private FirebaseHelper helper;
    private DatabaseReference userReference;

    public SignUpRepositoryImp(){
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void signUp(final String name, final String last_name, String address, final String email, final String birthday, final String celphone, final String password, String reenter_password, final Context context) {
        userReference = helper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                User_Firebase user_firebase = dataSnapshot.getValue(User_Firebase.class);
                if (user_firebase == null) { //No existe usuario con Email = email en BD
                    Log.i(TAG, "user_firebase == null");

                    User user = User.getInstance();
                    user.setLast_name(last_name);
                    user.setName(name);
                    user.setAvatarURL("");
                    user.setBirthday(birthday);
                    user.setEmail(email);
                    user.setPhone(celphone);
                    user.setUsername(name+"_"+last_name);
                    user.saveCash(context);
                    helper.writeNewUser(email, name, last_name, password, Constants.SIGNIN_EMAIL);
                    postEvent(SignUpEvent.onSignUpSuccess);
                }else{
                    Log.i(TAG, "User " + email + " is unexpectedly not null");
                    postEvent(SignUpEvent.onSignUpError, "User " + email + " is unexpectedly not null");
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.i(TAG, "onCancelled"+firebaseError.toString());
                postEvent(SignUpEvent.onSignUpError);
            }
        });
    }

    private void postEvent(int type){
        postEvent(type, null);
    }

    private void postEvent(int type, String errorMessage) {
        SignUpEvent signUpEvent = new SignUpEvent();
        signUpEvent.setEventType(type);
        if (errorMessage != null) {
            signUpEvent.setErrorMesage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(signUpEvent);
    }


}
