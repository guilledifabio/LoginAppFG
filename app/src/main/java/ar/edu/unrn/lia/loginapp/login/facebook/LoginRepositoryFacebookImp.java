package ar.edu.unrn.lia.loginapp.login.facebook;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

//import ar.edu.unrn.lia.loginapp.entities.Usuario_Table;
import ar.edu.unrn.lia.loginapp.domain.FirebaseHelper;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.login.events.FacebookEvent;
import ar.edu.unrn.lia.loginapp.model.Constants;
import ar.edu.unrn.lia.loginapp.model.User;
import ar.edu.unrn.lia.loginapp.model.User_Firebase;
import ar.edu.unrn.lia.loginapp.signIn.events.SignInEvent;
import ar.edu.unrn.lia.loginapp.signUp.events.SignUpEvent;

/**
 * Created by Germán on 4/2/2017.
 */

public class LoginRepositoryFacebookImp implements LoginRepositoryFacebook {
    private static final String TAG = "LoginRepositoryFacebook";
    private FirebaseHelper helper;
    private DatabaseReference userReference;

    public LoginRepositoryFacebookImp() {
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void signIn(final Context context) {
        Log.i(TAG, "Metodo signIn LoginRepositoryFacebbokImp");

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            Log.i(TAG, object.getString("id"));
                            final String first_name = object.getString("first_name");
                            final String last_name = object.getString("last_name");
                            final String email = object.getString("email");
                            String birthday = object.getString("birthday");
                            String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");

                            //Comprobar que esté en la BD y sinó agregarlo
                            userReference = helper.getUserReference(email);
                            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Log.i(TAG, "onDataChange");
                                    User_Firebase user_firebase = dataSnapshot.getValue(User_Firebase.class);

                                    if (user_firebase == null) { //Si no existe usuario en BD
                                        helper.writeNewUser(email, first_name, last_name, "", Constants.SIGNIN_FACEBOOK);
                                    }else{
                                        Log.i(TAG, "User " + email + " is not null");
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError firebaseError) {
                                    Log.i(TAG, "onCancelled");
                                    postEvent(FacebookEvent.onLoginError, firebaseError.getMessage().toString());
                                }
                            });
                            //Fin comprobar BD

                            User user = User.getInstance();
                            user.setAvatarURL(profilePicUrl);
                            user.setBirthday(birthday);
                            user.setEmail(email);
                            user.setAvatarURL(profilePicUrl);
                            user.setLast_name(last_name);
                            user.setName(first_name);
                            user.setUsername(last_name+"_"+first_name);

                            user.saveCash(context);

                            postEvent(FacebookEvent.onLoginSuccess);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, e.getLocalizedMessage());
                            postEvent(FacebookEvent.onLoginError, e.getLocalizedMessage());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name,birthday,picture.type(large),last_name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void postEvent(int type){
        postEvent(type, null);
    }

    private void postEvent(int type, String errorMessage) {
        Log.i(TAG, "Metodo postEvent");
        FacebookEvent loginEvent = new FacebookEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMesage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }
}