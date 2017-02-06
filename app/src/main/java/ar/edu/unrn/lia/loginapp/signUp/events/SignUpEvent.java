package ar.edu.unrn.lia.loginapp.signUp.events;

import ar.edu.unrn.lia.loginapp.entities.Usuario;

/**
 * Created by Germán on 1/2/2017.
 */

public class SignUpEvent {
    public final static int onSignUpError = 1;
    public final static int onSignUpSuccess = 2;

    private int eventType;
    private String errorMesage;
    private Usuario usuario;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
