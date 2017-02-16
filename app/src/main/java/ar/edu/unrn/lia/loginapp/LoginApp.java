package ar.edu.unrn.lia.loginapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;



/**
 * Created by Germán on 18/1/2017.
 */

public class LoginApp extends Application {
    static final String TAG = LoginApp.class.getSimpleName();
    private SharedPreferences sharedPref;

    public void onCreate() {
        super.onCreate();

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    /**
     * Configuración general de la aplicación
     */
    public String username(){
        return sharedPref.getString("user_name_key", "");
    }

    public boolean isActiveNotification(){
        return sharedPref.getBoolean("notification_key", true);
    }

    /**
     * Configuración de datos personales
     */
    public String nombre(){
        return sharedPref.getString("datos_nombre_key", "");
    }

    public String apellido(){
        return sharedPref.getString("datos_apellido_key", "");
    }

    public String direccion(){
        return sharedPref.getString("datos_dirección_key", "");
    }

    public String email(){
        return sharedPref.getString("datos_email_key", "");
    }

    public String celular(){
        return sharedPref.getString("datos_celular_key", "");
    }


}
