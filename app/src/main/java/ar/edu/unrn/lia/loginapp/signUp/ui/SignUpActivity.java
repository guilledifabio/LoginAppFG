package ar.edu.unrn.lia.loginapp.signUp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ar.edu.unrn.lia.loginapp.R;
import ar.edu.unrn.lia.loginapp.inicio.MainActivity;
import ar.edu.unrn.lia.loginapp.signIn.ui.SignInActivity;
import ar.edu.unrn.lia.loginapp.signUp.SignUpPresenter;
import ar.edu.unrn.lia.loginapp.signUp.SignUpPresenterImp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements SignUpView {
    private static final String TAG = "SignUpActivity";
    @BindView(R.id.input_lastname)
    EditText inputLastName;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_address)
    EditText inputAddress;
    @BindView(R.id.input_birthday)
    EditText inputBirthday;
    @BindView(R.id.input_mobile)
    EditText inputCelphone;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_reEnterPassword)
    EditText inputReenterPassword;
    @BindView(R.id.btn_signup)
    AppCompatButton btnSignup;
    @BindView(R.id.link_login)
    TextView signinLink;

    private SignUpPresenter signupPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        Log.i(TAG, "onCreate");

        progressDialog = new ProgressDialog(SignUpActivity.this, R.style.AppTheme_Dark_Dialog);
        signupPresenter = new SignUpPresenterImp(this);
        signupPresenter.onCreate();
        this.enableInputs();
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
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void signUpSuccess() {
        this.navigateToMainScreen();
    }

    @Override
    public void signUpError(String error) {
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToSignInScreen() {
        startActivity(new Intent(this, SignInActivity.class));
    }

    private void setInputs(boolean enabled) {
        inputName.setEnabled(enabled);
        inputLastName.setEnabled(enabled);
        inputEmail.setEnabled(enabled);
        inputAddress.setEnabled(enabled);
        inputBirthday.setEnabled(enabled);
        inputCelphone.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        inputReenterPassword.setEnabled(enabled);
        btnSignup.setEnabled(enabled);
    }

    @OnClick(R.id.btn_signup)
    @Override
    public void handleSignUp() {
        int errorType[] = checkInputs();

        if (errorType[0] == 0 && errorType[1] == 0 && errorType[2] == 0 && errorType[3] == 0 &&
                errorType[4] == 0 && errorType[5] == 0 && errorType[6] == 0 && errorType[7] == 0 && errorType[8] == 0) {
            signupPresenter.registerNewUser(inputName.getText().toString(), inputLastName.getText().toString(),
                    inputAddress.getText().toString(), inputEmail.getText().toString(), inputBirthday.getText().toString(), inputCelphone.getText().toString(),
                    inputPassword.getText().toString(), inputReenterPassword.getText().toString());

        } else {
            onSignupFailed(errorType);
        }
    }

    @OnClick(R.id.link_login)
    @Override
    public void handleSignIn() {
        this.navigateToSignInScreen();
    }

    private int[] checkInputs() {
        int[] errorType = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        String name = inputName.getText().toString();
        String last_name = inputLastName.getText().toString();
        String address = inputAddress.getText().toString();
        String email = inputEmail.getText().toString();
        String celphone = inputCelphone.getText().toString();
        String birthday = inputBirthday.getText().toString();
        String password = inputPassword.getText().toString();
        String reenter_password = inputReenterPassword.getText().toString();

        if (name.isEmpty()) {
            inputName.setError("Ingrese un nombre válido");
            errorType[0] = 1;
        } else {
            inputName.setError(null);
        }

        if (last_name.isEmpty()) {
            inputLastName.setError("Ingrese un apellido válido");
            errorType[1] = 1;
        } else {
            inputLastName.setError(null);
        }

        if (address.isEmpty()) {
            inputAddress.setError("Ingrese una dirección válida");
            errorType[2] = 1;
        } else {
            inputAddress.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Ingrese un email válido");
            errorType[3] = 1;
        } else {
            inputEmail.setError(null);
        }

        if (celphone.isEmpty() || !Patterns.PHONE.matcher(celphone).matches()) {
            inputCelphone.setError("Ingrese un número válido");
            errorType[4] = 1;
        } else {
            inputCelphone.setError(null);
        }

        if (birthday.isEmpty()) {
            inputBirthday.setError("Ingrese una fecha válida");
            errorType[5] = 1;
        } else {
            inputBirthday.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            inputPassword.setError("entre 4 y 10 caracteres alfanuméricos");
            errorType[6] = 1;
        } else {
            inputPassword.setError(null);
        }

        if (reenter_password.isEmpty() || reenter_password.length() < 4 || reenter_password.length() > 10) {
            inputReenterPassword.setError("entre 4 y 10 caracteres alfanuméricos");
            errorType[7] = 1;
        } else {
            inputReenterPassword.setError(null);
        }

        if (!password.equals(reenter_password)) {
            inputPassword.setError("No hay coincidencia");
            inputReenterPassword.setError("No hay coincidencia");
            errorType[8] = 1;
        } else {
            inputPassword.setError(null);
            inputReenterPassword.setError(null);
        }

        return errorType;
    }

    private void onSignupFailed(int[] errorType) {
        Toast.makeText(getBaseContext(), "Falló Sign up", Toast.LENGTH_LONG).show();
        showErrors(errorType);
        setInputs(true);
    }

    private void showErrors(int[] errorType) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        if (errorType[7] == 1) {
            if (errorType[5] != 1 && errorType[6] != 1) {
                inputPassword.setText("");
                inputPassword.setError(inputPassword.getError());
                inputPassword.startAnimation(shake);

                inputReenterPassword.setText("");
                inputPassword.setError(inputReenterPassword.getError());
                inputReenterPassword.startAnimation(shake);
            }
        }
        if (errorType[6] == 1) {
            inputReenterPassword.setText("");
            inputPassword.setError(inputReenterPassword.getError());
            inputReenterPassword.startAnimation(shake);
        }
        if (errorType[5] == 1) {
            inputPassword.setText("");
            inputPassword.setError(inputPassword.getError());
            inputPassword.startAnimation(shake);
        }
        if (errorType[4] == 1) {
            inputCelphone.setText("");
            inputCelphone.setError(inputCelphone.getError());
            inputCelphone.startAnimation(shake);
        }
        if (errorType[3] == 1) {
            inputEmail.setText("");
            inputEmail.setError(inputEmail.getError());
            inputEmail.startAnimation(shake);
        }
        if (errorType[2] == 1) {
            inputAddress.setText("");
            inputAddress.setError(inputAddress.getError());
            inputAddress.startAnimation(shake);
        }
        if (errorType[1] == 1) {
            inputLastName.setText("");
            inputLastName.setError(inputLastName.getError());
            inputLastName.startAnimation(shake);
        }
        if (errorType[0] == 1) {
            inputName.setText("");
            inputName.setError(inputName.getError());
            inputName.startAnimation(shake);
        }
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }
}
