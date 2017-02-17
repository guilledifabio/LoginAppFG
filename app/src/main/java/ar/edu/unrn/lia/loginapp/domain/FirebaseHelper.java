package ar.edu.unrn.lia.loginapp.domain;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ar.edu.unrn.lia.loginapp.model.User_Firebase;

/**
 * Created by Germán on 13/2/2017.
 */

public class FirebaseHelper {
    private DatabaseReference dataReference;
    private final static String USERS_PATH = "usuarios";

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper(){
        dataReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    public DatabaseReference getUserReference(String email){
        DatabaseReference userReference = null;
        if (email != null) {
            String emailKey = email.replace(".", "_");
            userReference = dataReference.getRoot().child(USERS_PATH).child(emailKey);
        }
        return userReference;
    }

    public void writeNewUser(String userId, String name, String last_name, String password, int signInWith) {
        Log.i("FirebaseHelper", ""+signInWith);
        User_Firebase user = new User_Firebase(name, last_name, password, signInWith);
        String emailKey = userId.replace(".", "_");
        dataReference.child(USERS_PATH).child(emailKey).setValue(user);
    }

}
