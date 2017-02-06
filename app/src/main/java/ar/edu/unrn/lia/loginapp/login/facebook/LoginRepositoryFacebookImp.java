package ar.edu.unrn.lia.loginapp.login.facebook;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONException;
import org.json.JSONObject;

import ar.edu.unrn.lia.loginapp.entities.Usuario;
import ar.edu.unrn.lia.loginapp.entities.Usuario_Table;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.login.events.FacebookEvent;

/**
 * Created by Germán on 4/2/2017.
 */

public class LoginRepositoryFacebookImp implements LoginRepositoryFacebook {
    private static final String TAG = "LoginRepositoryFacebook";

    public LoginRepositoryFacebookImp(){
    }

    @Override
    public void signIn(){
        Log.i(TAG,"Metodo signIn LoginRepositoryFacebbokImp");

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

                            Usuario usuario = new Usuario(first_name, last_name, null, email, 0, null, true, 1);
                            usuario.save();

                            Log.i(TAG, "Emal en GraphRequest" + email);

                            setearEstadoUsuario(email, 1);
                            postEvent(FacebookEvent.onLoginSuccess, null, usuario);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name, last_name,email");
        request.setParameters(parameters);
        request.executeAsync();
        //fin obtener email
    }

    private void setearEstadoUsuario(String email, int est){
        Usuario usuario = SQLite.select().from(Usuario.class).where(Usuario_Table.email.is(email)).querySingle();
        if (usuario != null){
            usuario.setSesion(est);
            usuario.save();
        }
    }

    private void postEvent(int type, String errorMessage, Usuario usuario) {
        Log.i(TAG,"Metodo postEvent");
        FacebookEvent loginEvent = new FacebookEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMesage(errorMessage);
        }else{
            if (usuario != null){
                loginEvent.setUsuario(usuario);
            }
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }
}