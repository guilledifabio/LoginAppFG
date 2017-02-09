package ar.edu.unrn.lia.loginapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Operator on 28.08.2016.
 */
public class User {

    // private static  User userInstance = null;
    private String email;
    private String name;
    private String last_name;
    private String user_name;
    private String birthday;
    private int login_status;
    //  private int service;
    private String id;
    private String avatar;
    private String phone;
    SharedPreferences.Editor save_cash;


    private static User mInstance = null;

    public void setAvatarURL(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarURL() {
        return avatar;
    }


    public static User getInstance() {
        if (mInstance == null) {
            mInstance = new User();
        }
        return mInstance;
    }

    public void saveCash(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        save_cash = preferences.edit();
        //save_cash = context.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE).edit();
        save_cash.putString(Constants.USER_NAME, user_name);
        save_cash.putString(Constants.NAME, name);
        save_cash.putString(Constants.LAST_NAME, last_name);
        save_cash.putString(Constants.USER_EMAIL, email);
        save_cash.putString(Constants.USER_BDAY, birthday);
        save_cash.putString(Constants.USER_AVATAR, avatar);
        save_cash.putInt(Constants.LOGIN_STATUS, login_status);
        save_cash.apply();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLogin_status() {
        return login_status;
    }

    public void setLogin_status(int login_status) {
        this.login_status = login_status;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUsername(String user_name) {
        this.user_name = user_name;
    }
}

