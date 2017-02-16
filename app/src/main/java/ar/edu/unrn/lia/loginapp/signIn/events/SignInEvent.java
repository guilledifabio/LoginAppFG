package ar.edu.unrn.lia.loginapp.signIn.events;

import ar.edu.unrn.lia.loginapp.model.User;

/**
 * Created by Germán on 1/2/2017.
 */

public class SignInEvent {
    public final static int onSignInError = 0;
    public final static int onSignInSuccess = 1;
    public final static int onSuccessToRecoverSession = 2;
    public final static int onFailedToRecoverSession = 3;


    private int eventType;
    private String errorMesage;
    private User user;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMesage() {
        return errorMesage;
    }

    public void setErrorMesage(String errorMesage) {
        this.errorMesage = errorMesage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
