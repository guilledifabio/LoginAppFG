package ar.edu.unrn.lia.loginapp.login.events;

//import ar.edu.unrn.lia.loginapp.entities.User;

import ar.edu.unrn.lia.loginapp.model.User;

/**
 * Created by Germán on 6/2/2017.
 */

public class LoginEvent {
    public final static int onSuccessToRecoverSession = 1;
    public final static int onFailedToRecoverSession = 2;

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
