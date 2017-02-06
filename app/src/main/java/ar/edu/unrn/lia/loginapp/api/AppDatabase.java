package ar.edu.unrn.lia.loginapp.api;

/**
 * Created by Germán on 30/11/2016.
 */
import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "curso2016"; // we will add the .db extension

    public static final int VERSION = 1;
}