package ar.edu.unrn.lia.loginapp.signIn;

import org.greenrobot.eventbus.Subscribe;

import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.model.User;
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

    @Override
    public void validateLogin(String email, String password) {
        if (signInView != null){
            signInView.disableInputs();
            signInView.showProgress();
        }
        signInIteractor.doSignIn(email, password, signInView.getContext());
    }

    private void onSignInSuccess(){
        if (signInView != null){
            signInView.signInSuccess();
        }
    }

    private void onSignInError(String error){
        if (signInView != null){
            signInView.hideProgress();
            signInView.enableInputs();
            signInView.loginError(error);
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
                onSignInSuccess();
                break;
        }
    }
}
