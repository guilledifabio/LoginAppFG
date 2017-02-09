package ar.edu.unrn.lia.loginapp.signIn;

import org.greenrobot.eventbus.Subscribe;

import ar.edu.unrn.lia.loginapp.entities.User;
import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.signIn.events.SignInEvent;
import ar.edu.unrn.lia.loginapp.signIn.ui.SignInView;

/**
 * Created by Germán on 18/1/2017.
 */

public class SignInPresenterImp implements SignInPresenter {
    EventBus eventBus;
    private SignInView signInView;
    private SignInInteractor signInIteractor;
    private static final String TAG = "PresenterImp";

    public SignInPresenterImp(SignInView loginView){
        this.signInView = loginView;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.signInIteractor = new SignInInteractorImp();
    }
/*
    @Override
    public void checkForAuthenticatedUser() {

        if (signInView != null){
            Log.i(TAG, "CheckForAuth -- Presenter");
            signInView.disableInputs();
            signInView.showProgress();
            signInIteractor.checkSession();
        }
    }*/

    @Override
    public void validateLogin(String email, String password) {
        if (signInView != null){
            signInView.disableInputs();
            signInView.showProgress();
        }
        signInIteractor.doSignIn(email, password);
    }

    private void onSignInSuccess(User user){
        if (signInView != null){
            signInView.signInSuccess(user);
        }
    }

    private void onSignInError(String error){
        if (signInView != null){
            signInView.hideProgress();
            signInView.enableInputs();
            signInView.loginError(error);
        }
    }

    private void onSuccessToRecoverSessio(){
        signInView.navigateToMainScreen();
    }

    private void onFailedToRecoverSession(){
        if (signInView != null) {
            signInView.hideProgress();
            signInView.enableInputs();
        }
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        signInView = null;
        eventBus.unregister(this);
    }

    @Override
    @Subscribe
    public void onEventMainThread(SignInEvent event) {
        switch (event.getEventType()) {
            case SignInEvent.onSignInError:
                onSignInError(event.getErrorMesage());
                break;
            case SignInEvent.onSignInSuccess:
                onSignInSuccess(event.getUser());
                break;
            case SignInEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
            case SignInEvent.onSuccessToRecoverSession:
                onSuccessToRecoverSessio();
                break;
        }
    }
}
