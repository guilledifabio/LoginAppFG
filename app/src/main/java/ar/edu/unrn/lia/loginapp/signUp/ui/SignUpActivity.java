package ar.edu.unrn.lia.loginapp.signUp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ar.edu.unrn.lia.loginapp.R;
import ar.edu.unrn.lia.loginapp.entities.Usuario;
import ar.edu.unrn.lia.loginapp.inicio.MainActivity;
import ar.edu.unrn.lia.loginapp.signIn.ui.SignInActivity;
import ar.edu.unrn.lia.loginapp.signUp.SignUpPresenter;
import ar.edu.unrn.lia.loginapp.signUp.SignUpPresenterImp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements SignUpView{
    private static final String TAG = "SignUpActivity";
    @BindView(R.id.input_lastname)
    EditText _lastnameText;
    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.input_address)
    EditText _addressText;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_mobile)
    EditText _mobileText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup)
    AppCompatButton _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;

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
    public void signUpSuccess(Usuario usuario) {
        guardarEnPreferencias(usuario);
    }

    @Override
    public void signUpError(String error) {
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToSignInScreen(){
        startActivity(new Intent(this,SignInActivity.class));
    }

    private void setInputs(boolean enabled){
        _nameText.setEnabled(enabled);
        _lastnameText.setEnabled(enabled);
        _addressText.setEnabled(enabled);
        _emailText.setEnabled(enabled);
        _mobileText.setEnabled(enabled);
        _passwordText.setEnabled(enabled);
        _reEnterPasswordText.setEnabled(enabled);
        _signupButton.setEnabled(enabled);
    }

    @OnClick(R.id.btn_signup)
    @Override
    public void handleSignUp() {
        int tipoError[] = checkInputs();

        if (tipoError[0] == 0 && tipoError[1] == 0 && tipoError[2] == 0 && tipoError[3] == 0 &&
                tipoError[4] == 0 && tipoError[5] == 0 && tipoError[6] == 0 && tipoError[7] == 0) {
            signupPresenter.registerNewUser(_nameText.getText().toString(), _lastnameText.getText().toString(),
                    _addressText.getText().toString(), _emailText.getText().toString(), Integer.parseInt(_mobileText.getText().toString()),
                    _passwordText.getText().toString(), _reEnterPasswordText.getText().toString());

        }else{
            onSignupFailed(tipoError);
        }
    }

    @OnClick(R.id.link_login)
    @Override
    public void handleSignIn() {
        this.navigateToSignInScreen();
    }

    private int[] checkInputs(){
        int[] tipoError = {0, 0, 0, 0, 0, 0, 0, 0};
        String name = _nameText.getText().toString();
        String lastname = _lastnameText.getText().toString();
        String direccion = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String numCel = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String password2 = _reEnterPasswordText.getText().toString();

        if (name.isEmpty()) {
            _nameText.setError("Ingrese un nombre válido");
            tipoError[0] = 1;
        } else {
            _nameText.setError(null);
        }

        if (lastname.isEmpty()) {
            _lastnameText.setError("Ingrese un apellido válido");
            tipoError[1] = 1;
        } else {
            _lastnameText.setError(null);
        }

        if (direccion.isEmpty()) {
            _addressText.setError("Ingrese una dirección válida");
            tipoError[2] = 1;
        } else {
            _addressText.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Ingrese un email válido");
            tipoError[3] = 1;
        } else {
            _emailText.setError(null);
        }

        if (numCel.isEmpty() || !Patterns.PHONE.matcher(numCel).matches()) {
            _mobileText.setError("Ingrese un número válido");
            tipoError[4] = 1;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("entre 4 y 10 caracteres alfanuméricos");
            tipoError[5] = 1;
        } else {
            _passwordText.setError(null);
        }

        if (password2.isEmpty() || password2.length() < 4 || password2.length() > 10) {
            _reEnterPasswordText.setError("entre 4 y 10 caracteres alfanuméricos");
            tipoError[6] = 1;
        } else {
            _reEnterPasswordText.setError(null);
        }

        if (!password.equals(password2)) {
            _passwordText.setError("No hay coincidencia");
            _reEnterPasswordText.setError("No hay coincidencia");
            tipoError[7] = 1;
        } else {
            _passwordText.setError(null);
            _reEnterPasswordText.setError(null);
        }

        return tipoError;
    }

    private void onSignupFailed(int[] tipoError) {
        Toast.makeText(getBaseContext(), "Falló Sign up", Toast.LENGTH_LONG).show();
        showErrors(tipoError);
        setInputs(true);
    }

    private void showErrors(int[] tipoError) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        if (tipoError[7] == 1) {
            if (tipoError[5] != 1 && tipoError[6] != 1) {
                _passwordText.setText("");
                _passwordText.setError(_passwordText.getError());
                _passwordText.startAnimation(shake);

                _reEnterPasswordText.setText("");
                _passwordText.setError(_reEnterPasswordText.getError());
                _reEnterPasswordText.startAnimation(shake);
            }
        }
        if (tipoError[6] == 1) {
            _reEnterPasswordText.setText("");
            _passwordText.setError(_reEnterPasswordText.getError());
            _reEnterPasswordText.startAnimation(shake);
        }
        if (tipoError[5] == 1) {
            _passwordText.setText("");
            _passwordText.setError(_passwordText.getError());
            _passwordText.startAnimation(shake);
        }
        if (tipoError[4] == 1) {
            _mobileText.setText("");
            _mobileText.setError(_mobileText.getError());
            _mobileText.startAnimation(shake);
        }
        if (tipoError[3] == 1) {
            _emailText.setText("");
            _emailText.setError(_emailText.getError());
            _emailText.startAnimation(shake);
        }
        if (tipoError[2] == 1) {
            _addressText.setText("");
            _addressText.setError(_addressText.getError());
            _addressText.startAnimation(shake);
        }
        if (tipoError[1] == 1) {
            _lastnameText.setText("");
            _lastnameText.setError(_lastnameText.getError());
            _lastnameText.startAnimation(shake);
        }
        if (tipoError[0] == 1) {
            _nameText.setText("");
            _nameText.setError(_nameText.getError());
            _nameText.startAnimation(shake);
        }
    }

    private void guardarEnPreferencias(Usuario u) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.datos_nombre_key), u.getNombre());
        editor.putString(getString(R.string.datos_apellido_key), u.getApellido());
        editor.putString(getString(R.string.datos_direccion_key), u.getDireccion());
        editor.putString(getString(R.string.datos_email_key), u.getEmail());
        editor.putString(getString(R.string.datos_celular_key), String.valueOf(u.getCelular()));
        editor.apply();
    }
}
