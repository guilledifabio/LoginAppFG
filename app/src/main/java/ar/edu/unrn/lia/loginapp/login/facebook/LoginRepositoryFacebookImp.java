package ar.edu.unrn.lia.loginapp.login.facebook;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

//import ar.edu.unrn.lia.loginapp.entities.Usuario_Table;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.login.events.FacebookEvent;
import ar.edu.unrn.lia.loginapp.model.User;

/**
 * Created by Germán on 4/2/2017.
 */

public class LoginRepositoryFacebookImp implements LoginRepositoryFacebook {
    private static final String TAG = "LoginRepositoryFacebook";

    public LoginRepositoryFacebookImp() {
    }

    @Override
    public void signIn(final Context context) {
        Log.i(TAG, "Metodo signIn LoginRepositoryFacebbokImp");

        //Obtener email
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            Log.i(TAG, object.getString("id"));
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String birthday = object.getString("birthday");
                            String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");

                            //Comprobar que esté en la BD y sinó agregarlo
                            //Firebase
                            //Fin comprobar BD

                            // User usuario = new User(first_name, last_name, null, email, 0, null, true, 1);
                            //usuario.save();
                            User user = User.getInstance();
                            user.setAvatarURL(profilePicUrl);
                            user.setBirthday(birthday);
                            user.setEmail(email);
                            Log.i(TAG, "Email en GraphRequest" + email);

                            user.setAvatarURL(profilePicUrl);
                            user.setLast_name(last_name);
                            user.setName(first_name);
                            String userName = new StringBuilder().append(last_name).append(" ").append(first_name).toString();
                            user.setUsername(userName);

                            user.saveCash(context);

                            postEvent(FacebookEvent.onLoginSuccess, null, user);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, e.getLocalizedMessage());
                            postEvent(FacebookEvent.onLoginError, e.getLocalizedMessage(), null);
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name,birthday,picture.type(large),last_name,email");
        request.setParameters(parameters);
        request.executeAsync();
        //fin obtener email
    }

    private void postEvent(int type, String errorMessage, ar.edu.unrn.lia.loginapp.model.User user) {
        Log.i(TAG, "Metodo postEvent");
        FacebookEvent loginEvent = new FacebookEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMesage(errorMessage);
        } else {
            if (user != null) {
                loginEvent.setUsuario(user);
            }
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }
}