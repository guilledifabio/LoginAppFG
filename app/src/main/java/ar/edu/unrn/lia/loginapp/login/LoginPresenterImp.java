package ar.edu.unrn.lia.loginapp.login;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.login.events.LoginEvent;
import ar.edu.unrn.lia.loginapp.login.ui.LoginView;

/**
 * Created by Germán on 6/2/2017.
 */

public class LoginPresenterImp implements LoginPresenter {
    EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;
    private static final String TAG = "LoginPresenterImp";

    public LoginPresenterImp(LoginView loginView){
        this.loginView = loginView;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.loginInteractor = new LoginInteractorImp();
    }

    @Override
    public void checkForAuthenticatedUser() {
        if (loginView != null){
            Log.i(TAG, "CheckForAuth -- Presenter");
            loginView.disableInputs();
            loginInteractor.checkSession();
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
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()) {
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
            case LoginEvent.onSuccessToRecoverSession:
                onSuccessToRecoverSession();
                break;
        }
    }

    private void onSuccessToRecoverSession(){
        Log.i(TAG,"onSuccessToRecoverSessio");
        loginView.navigateToMainScreen();
    }

    private void onFailedToRecoverSession(){
        if (loginView != null) {
            Log.i(TAG,"onFailedToRecoverSession");
            loginView.enableInputs();
        }
    }


}
