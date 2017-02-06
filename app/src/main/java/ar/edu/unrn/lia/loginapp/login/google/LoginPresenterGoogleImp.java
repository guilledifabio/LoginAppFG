package ar.edu.unrn.lia.loginapp.login.google;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.Subscribe;

import ar.edu.unrn.lia.loginapp.entities.Usuario;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.login.events.FacebookEvent;
import ar.edu.unrn.lia.loginapp.login.events.GoogleEvent;
import ar.edu.unrn.lia.loginapp.login.ui.LoginActivity;
import ar.edu.unrn.lia.loginapp.login.ui.LoginView;

/**
 * Created by Germán on 3/2/2017.
 */

public class LoginPresenterGoogleImp implements LoginPresenterGoogle, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "LoginPresenterGoogle";
    private LoginView loginView;
    private LoginInteractorGoogle loginInteractorGoogle;
    EventBus eventBus;
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_SIGN_IN_G = 2016;
    private GoogleApiClient mGoogleApiClient;

    public LoginPresenterGoogleImp(LoginView loginView) {
        this.loginView = loginView;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.loginInteractorGoogle = new LoginInteractorGoogleImp();
    }

    @Override
    public void createGoogleClient(LoginActivity loginActivity){
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        loginView.specifyGoogleSignIn(gso);
        mGoogleApiClient = new GoogleApiClient.Builder(loginActivity)
                .enableAutoManage(loginActivity , this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
    }

    @Override
    public void signIn(LoginActivity loginActivity) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        loginActivity.startActivityForResult(signInIntent, RC_SIGN_IN_G);
    }
    @Override
    public void onActivityResult(LoginActivity loginActivity,int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN_G) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result, loginActivity);
        }
    }

    private void handleSignInResult(GoogleSignInResult result, LoginActivity loginView) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            loginInteractorGoogle.doSignIn(result.getSignInAccount());
        } else {
            this.onLoginError("ERROR al conectar con GOOGLE");
        }
    }

    @Override
    public void onStop (){
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unregister(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    @Subscribe
    public void onEventMainThread(GoogleEvent event) {
        Log.i(TAG,"onEventMainThread");
        switch (event.getEventType()) {
            case GoogleEvent.onLoginError:
                Log.i(TAG,"Metodo onEventMainThread error");
                onLoginError(event.getErrorMesage());
                break;
            case GoogleEvent.onLoginSuccess:
                Log.i(TAG,"Metodo onEventMainThread success");
                onLoginSuccess(event.getUsuario());
                break;
        }
    }

    private void onLoginSuccess(Usuario usuario){
        Log.i(TAG, "EventBus onLoginSuccess Antes del NULL");
        if (loginView != null){
            Log.i(TAG, "EventBus onLoginSuccess");
            loginView.signInSuccessGoogle(usuario);
        }
    }

    private void onLoginError(String error){
        if (loginView != null){
            Log.i(TAG,"SIGNUP ERROR GOOGLE");
            loginView.signInErrorGoogle(error);
        }
    }
}
