package ar.edu.unrn.lia.loginapp.signIn.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ar.edu.unrn.lia.loginapp.R;
import ar.edu.unrn.lia.loginapp.inicio.MainActivity;
import ar.edu.unrn.lia.loginapp.signIn.SignInPresenter;
import ar.edu.unrn.lia.loginapp.signIn.SignInPresenterImp;
import ar.edu.unrn.lia.loginapp.signUp.ui.SignUpActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity implements SignInView {

    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.btn_signin)
    AppCompatButton btnSignin;
    @BindView(R.id.link_signup)
    TextView linkSignup;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.linearLayout)
    LinearLayout container;

    private SignInPresenter signInPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        this.enableInputs();
        this.hideProgress();

        signInPresenter = new SignInPresenterImp(this);
        signInPresenter.onCreate();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_signin)
    @Override
    public void handleSignIn() {
        signInPresenter.validateLogin(inputEmail.getText().toString(), inputPassword.getText().toString());
    }

    @OnClick(R.id.link_signup)
    @Override
    public void handleSignUp() {
        this.navigateToSignUpScreen();
    }

    @Override
    public void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        inputPassword.setError(msgError);
    }

    @Override
    public void navigateToSignUpScreen() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @Override
    public void signInSuccess(){
        navigateToMainScreen();
    }
    private void setInputs(boolean enabled){
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        btnSignin.setEnabled(enabled);
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }
}
