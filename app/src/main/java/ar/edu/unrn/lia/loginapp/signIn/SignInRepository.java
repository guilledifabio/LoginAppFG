package ar.edu.unrn.lia.loginapp.signIn;

/**
 * Created by Germán on 18/1/2017.
 */

public interface SignInRepository {
    void signIn(String email, String password);
//    void checkSession();
}
