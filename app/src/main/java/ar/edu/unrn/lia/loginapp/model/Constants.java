package ar.edu.unrn.lia.loginapp.model;

/**
 * Created by Operator on 28.08.2016.
 */
public class Constants {
     public final static String USER_INFO = "user_info" ;

    //login_status
    public static String LOGIN_STATUS = "login_status";
    public final static int LOGIN_IN = 1;
    public final static int LOGIN_OUT = 0;

   //login_status
    public final static int ONLINE = 1;
    public final static int OFFLINE = 0;
    public final static int IN_GALLERY = 3;

    //internet_status
    public static String SERVICE = "service";
    public final static int FB_SERVICE = 3;
    public final static int GOOGLE_SERVICE = 4;

    //user's profile fields
    public static String NAME = "name";
    public static String LAST_NAME="lastname";
    public static String USER_NAME = "user_name";
    public static String USER_BDAY = "birthday";
    public static String USER_EMAIL = "user_email";
    public static String USER_PHONE = "phone";
    public static String USER_AVATAR = "user_avatar";

    //Sign In With
    public static int SIGNIN_EMAIL = 0;
    public static int SIGNIN_FACEBOOK = 1;
    public static int SIGNIN_GOOGLE = 2;


}
