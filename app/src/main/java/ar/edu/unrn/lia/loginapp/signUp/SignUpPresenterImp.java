package ar.edu.unrn.lia.loginapp.signUp;

import android.content.Context;

import org.greenrobot.eventbus.Subscribe;

import ar.edu.unrn.lia.loginapp.lib.EventBus;
import ar.edu.unrn.lia.loginapp.lib.GreenRobotEventBus;
import ar.edu.unrn.lia.loginapp.signUp.events.SignUpEvent;
import ar.edu.unrn.lia.loginapp.signUp.ui.SignUpView;

/**
 * Created by Germán on 24/1/2017.
 */

public class SignUpPresenterImp implements SignUpPresenter {
    private EventBus eventBus;
    private SignUpView signupView;
    private SignUpInteractor signupInteractor;

    public SignUpPresenterImp(SignUpView signupView){
        this.signupView = signupView;
        this.eventBus = GreenRobotEventBus.getInstance();
        signupInteractor = new SignUpInteractorImp();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        signupView = null;
        eventBus.unregister(this);
    }

    @Override
    public void registerNewUser(String name, String last_name, String address, String email, String birthday, String celphone, String password, String reenter_password){
        if (signupView != null){
            signupView.disableInputs();
            signupView.showProgress();

            signupInteractor.doSignUp(name, last_name, address, email, birthday, celphone, password, reenter_password, signupView.getContext());
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(SignUpEvent event) {
        switch (event.getEventType()) {
            case SignUpEvent.onSignUpError:
                onSignUpError(event.getErrorMesage());
                break;
            case SignUpEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
        }
    }

    private void onSignUpSuccess(){
        if (signupView != null){
            signupView.signUpSuccess();
        }
    }

    private void onSignUpError(String error){
        if (signupView != null){
            signupView.hideProgress();
            signupView.enableInputs();
            signupView.signUpError(error);
        }
    }
}
