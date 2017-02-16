package ar.edu.unrn.lia.loginapp.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import ar.edu.unrn.lia.loginapp.R;
import ar.edu.unrn.lia.loginapp.inicio.MainActivity;
import ar.edu.unrn.lia.loginapp.login.LoginPresenter;
import ar.edu.unrn.lia.loginapp.login.LoginPresenterImp;
import ar.edu.unrn.lia.loginapp.login.facebook.LoginPresenterFacebook;
import ar.edu.unrn.lia.loginapp.login.facebook.LoginPresenterFacebookImp;
import ar.edu.unrn.lia.loginapp.login.google.LoginPresenterGoogle;
import ar.edu.unrn.lia.loginapp.login.google.LoginPresenterGoogleImp;
import ar.edu.unrn.lia.loginapp.signIn.ui.SignInActivity;
import ar.edu.unrn.lia.loginapp.signUp.ui.SignUpActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.btnIdentificate)
    Button btnIdentificate;
    @BindView(R.id.btnRegistrate)
    Button btnRegistrate;
    @BindView(R.id.btnLoginFacebook)
    LoginButton btnLoginFacebook;
    @BindView(R.id.btnLoginGoogle)
    SignInButton btnLoginGoogle;

    private LoginPresenter loginPresenter;
    private LoginPresenterFacebook loginPresenterFacebook;
    private LoginPresenterGoogle loginPresenterGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Log.i(TAG, "onCreate");

        //Facebook ---------------------------------------------------------
        loginPresenterFacebook = new LoginPresenterFacebookImp(this);
        loginPresenterFacebook.onCreate();
        btnLoginFacebook.setReadPermissions("email", "public_profile", "user_birthday", "user_photos");
        //Fin Facebook -----------------------------------------------------

        //Google -----------------------------------------------------------
        btnLoginGoogle.setSize(SignInButton.SIZE_STANDARD);

        loginPresenterGoogle = new LoginPresenterGoogleImp(this);
        loginPresenterGoogle.onCreate();
        loginPresenterGoogle.createGoogleClient(this);
        //Fin Google -------------------------------------------------------

        //Chequear por usuario ya logueado ---------------------------------
        loginPresenter = new LoginPresenterImp(this);
        loginPresenter.onCreate();
        loginPresenter.checkForAuthenticatedUser();
        //Fin chequear por usuario ya logueado -----------------------------
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenterGoogle.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy Login");
        super.onDestroy();

        loginPresenterFacebook.onDestroy();
        loginPresenterGoogle.onDestroy();
        loginPresenter.onDestroy();

    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);

        loginPresenterFacebook.onActivityResult(requestCode, responseCode, intent);
        loginPresenterGoogle.onActivityResult(LoginActivity.this, requestCode, responseCode, intent);
    }


    @Override
    public void navigateToMainScreen() {
        Log.i(TAG, "navigateToMainScreen");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }


    @Override
    public void signInErrorFacebook(String error) {
        Snackbar.make(container, "Error sign in Facebook "+error.toString(), Snackbar.LENGTH_LONG);
    }

    /*
        @Override
        public void signInSuccessGoogle(User user) {
            guardarEnPreferencias(user);
            navigateToMainScreen();
        }
    */
    @Override
    public void signInErrorGoogle(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_LONG);
    }

    @Override
    public void specifyGoogleSignIn(GoogleSignInOptions gso) {
        btnLoginGoogle.setScopes(gso.getScopeArray());
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    private void setInputs(boolean enabled) {
        btnLoginFacebook.setEnabled(enabled);
        btnLoginGoogle.setEnabled(enabled);
        btnIdentificate.setEnabled(enabled);
        btnRegistrate.setEnabled(enabled);
    }

    @OnClick({R.id.btnIdentificate, R.id.btnRegistrate, R.id.btnLoginFacebook, R.id.btnLoginGoogle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnIdentificate:
                startActivity(new Intent(this, SignInActivity.class));
                break;
            case R.id.btnRegistrate:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.btnLoginFacebook:
                loginPresenterFacebook.logIn();
                break;
            case R.id.btnLoginGoogle:
                loginPresenterGoogle.signIn(LoginActivity.this);
                break;
        }
    }
}
