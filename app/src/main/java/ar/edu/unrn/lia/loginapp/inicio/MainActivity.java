package ar.edu.unrn.lia.loginapp.inicio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import ar.edu.unrn.lia.loginapp.R;
//import ar.edu.unrn.lia.loginapp.entities.Usuario_Table;
//import ar.edu.unrn.lia.loginapp.pref_headers.DatosPersonalesActivity;
import ar.edu.unrn.lia.loginapp.map.MapsActivity;
import ar.edu.unrn.lia.loginapp.model.User;
import ar.edu.unrn.lia.loginapp.preference.SettingsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private User user;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        user = User.getInstance();
        email = user.getEmail();
        if (email != null){
            Log.i(TAG, email);
        }else{
            Log.i(TAG, "Email NULL");
        }

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //Seteo fragment de Inicio
        Fragment fragment = new InicioFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getBaseContext(), "Configuración", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_search) {
            Toast.makeText(getBaseContext(), "Buscar", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        boolean fragmentTransaction = false;
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_inicio:
                fragment = new InicioFragment();
                fragmentTransaction = true;
                break;
            case R.id.nav_notificaciones:
                //  fragment = new NotificacionesFragment();
                //  fragmentTransaction = true;
                break;
            case R.id.nav_favoritos:
                //  fragment = new FavoritosFragment();
                //  fragmentTransaction = true;
                break;
            case R.id.nav_categoria:
                //  fragment = new CategoríaFragment();
                //  fragmentTransaction = true;
                break;
            case R.id.nav_map:
                Log.d("MainActivity", "Boton Mapa");
                startActivity(new Intent(this, MapsActivity.class));
                break;
            case R.id.nav_ajustes:
                Log.d("MainActivity", "Boton Ajustes");
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_salir:
                //Salir de Facebook
                disconnectFromFacebook();
                //Fin salir de Facebook
                disconnect();
                finishAffinity();
                System.exit(0);

                /*
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                */
        }

        if (fragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            menuItem.setChecked(true);
            getSupportActionBar().setTitle(menuItem.getTitle());
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onDestroy(){
        Log.i(TAG,"onDestroy MAIN");
        super.onDestroy();
    }

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            Log.i(TAG,"accesTocken = NULL");
        }else{
            Log.i(TAG,"accesTocken != NULL");
            LoginManager.getInstance().logOut();
        }
    }

    public void disconnect(){
        user.setEmail(null);
    }
}
