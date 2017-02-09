package ar.edu.unrn.lia.loginapp.login.events;

import ar.edu.unrn.lia.loginapp.model.User;

/**
 * Created by Germán on 4/2/2017.
 */

public class FacebookEvent {
    public final static int onLoginError = 1;
    public final static int onLoginSuccess = 2;

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

    public User getUsuario() {
        return user;
    }

    public void setUsuario(User user) {
        this.user = user;
    }
}
