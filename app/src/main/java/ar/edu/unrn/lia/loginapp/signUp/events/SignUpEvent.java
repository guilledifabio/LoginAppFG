package ar.edu.unrn.lia.loginapp.signUp.events;

import ar.edu.unrn.lia.loginapp.entities.User;

/**
 * Created by Germán on 1/2/2017.
 */

public class SignUpEvent {
    public final static int onSignUpError = 1;
    public final static int onSignUpSuccess = 2;

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
