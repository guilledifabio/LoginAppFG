package ar.edu.unrn.lia.loginapp.login.facebook;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;

import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.login.events.FacebookEvent;
import ar.edu.unrn.lia.loginapp.login.ui.LoginView;
import ar.edu.unrn.lia.loginapp.model.User;

/**
 * Created by Germán on 2/2/2017.
 */

public class LoginPresenterFacebookImp implements LoginPresenterFacebook{
    private static final String TAG = "LoginPresenterFacebook";
    EventBus eventBus;
    private LoginView loginView;
    private LoginInteractorFacebook loginInteractorFacebook;
    private CallbackManager callbackManager;
    private static final int RC_SIGN_IN_FB = 1916;
    private List<String> permissionNeeds = Arrays.asList("public_profile",
            "email",  "user_photos", "user_birthday");

    public LoginPresenterFacebookImp(LoginView loginView){
        this.loginView = loginView;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.loginInteractorFacebook = new LoginInteractorFacebookImp();
    }

    @Override
    public void logIn (){
        Log.i(TAG, "logIn");
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions((Activity) loginView,
                permissionNeeds);
        Log.i(TAG, LoginManager.getInstance().toString());
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i(TAG, loginResult.getAccessToken().getCurrentAccessToken().getToken().toString());
                Log.i(TAG, loginResult.getRecentlyGrantedPermissions().toString());
                Log.i(TAG, "Metodo logIn onSuccess LoginPresenterFacebookImp");
                loginInteractorFacebook.doSignIn(loginView.getContext());
            }

            @Override
            public void onCancel() {
                // App code
                Log.i(TAG, "CallBack onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i(TAG, "CallBack onError");
            }
        });
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data) {
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unregister(this);
    }

    @Override
    @Subscribe
    public void onEventMainThread(FacebookEvent event) {
        Log.i(TAG,"onEventMainThread");
        switch (event.getEventType()) {
            case FacebookEvent.onLoginError:
                Log.i(TAG,"Metodo onEventMainThread error");
                onLoginError(event.getErrorMesage());
                break;
            case FacebookEvent.onLoginSuccess:
                Log.i(TAG,"Metodo onEventMainThread success");
                onLoginSuccess(event.getUsuario());
                break;
        }
    }

    private void onLoginSuccess(User user){
        Log.i(TAG, "EventBus onLoginSuccess Antes del NULL");
        if (loginView != null){
            Log.i(TAG, "EventBus onLoginSuccess");
            loginView.navigateToMainScreen();
           // loginView.signInSuccessFacebook(user);
        }
    }

    private void onLoginError(String error){
        if (loginView != null){
            Log.i(TAG,"SIGNUP ERROR FACEBOOK");

            loginView.signInErrorFacebook(error);
        }
    }
}
