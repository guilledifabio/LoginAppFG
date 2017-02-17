package ar.edu.unrn.lia.loginapp.model;

/**
 * Created by Germán on 13/2/2017.
 */
import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User_Firebase {

    public String nombre;
    public String apellido;
    public String contraseña;
    public int signInWith;

    public User_Firebase() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User_Firebase(String name, String last_name, String password, int signInWith) {
        this.nombre = name;
        this.apellido = last_name;
        this.contraseña = password;
        this.signInWith = signInWith;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getSignInWith() {
        return signInWith;
    }

    public void setSignInWith(int signInWith) {
        this.signInWith = signInWith;
    }
}
// [END blog_user_class]
