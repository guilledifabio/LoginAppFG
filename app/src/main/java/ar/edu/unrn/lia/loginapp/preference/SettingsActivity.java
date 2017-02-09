package ar.edu.unrn.lia.loginapp.preference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import ar.edu.unrn.lia.loginapp.R;
import ar.edu.unrn.lia.loginapp.inicio.MainActivity;


/**
 * Created by Germán on 30/11/2016.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public static class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


        private static final String TAG = SettingFragment.class.getSimpleName();

        SharedPreferences sharedPreferences;


        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

            PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);

            onSharedPreferenceChanged(sharedPreferences, getString(R.string.user_name_key));
            onSharedPreferenceChanged(sharedPreferences, getString(R.string.notification_key));
            onSharedPreferenceChanged(sharedPreferences, getString(R.string.notification_time_key));
//            onSharedPreferenceChanged(sharedPreferences, getString(R.string.provincia_key));
//            onSharedPreferenceChanged(sharedPreferences, getString(R.string.localidad_key));
            onSharedPreferenceChanged(sharedPreferences, getString(R.string.datos_birthday_key));
            onSharedPreferenceChanged(sharedPreferences, getString(R.string.datos_nombre_key));
            onSharedPreferenceChanged(sharedPreferences, getString(R.string.datos_apellido_key));
            onSharedPreferenceChanged(sharedPreferences, getString(R.string.datos_direccion_key));
            onSharedPreferenceChanged(sharedPreferences, getString(R.string.datos_email_key));
            onSharedPreferenceChanged(sharedPreferences, getString(R.string.datos_celular_key));

        }

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {

        }


        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            Preference preference = findPreference(key);
            if (preference instanceof EditTextPreference) {
                preference.setSummary(sharedPreferences.getString(key, ""));
            }
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[prefIndex]);

                    //Seteo valor de la siguiente ListPreference
                    final ListPreference list = (ListPreference) findPreference("localidad");
/*                    String provSeleccionado = (String) listPreference.getEntries()[prefIndex];
                    switch (provSeleccionado){
                        case "Provincia1":
                            setListPreferenceData1(list);
                            break;
                    }*/
                    setListPreferenceData(list, (String) listPreference.getEntries()[prefIndex]);

                    //cargarDatosEnBD();

                }
            }
        }

        protected static void setListPreferenceData(ListPreference lp, String p) {

            switch (p) {
                case "Provincia1":
                    lp.setEntries(R.array.Provincia1);
                    lp.setEntryValues(R.array.Provincia1);
                    break;
                case "Provincia2":
                    lp.setEntries(R.array.Provincia2);
                    lp.setEntryValues(R.array.Provincia2);
                    break;
                case "Provincia3":
                    lp.setEntries(R.array.Provincia3);
                    lp.setEntryValues(R.array.Provincia3);
                    break;
                case "Provincia4":
                    lp.setEntries(R.array.Provincia4);
                    lp.setEntryValues(R.array.Provincia4);
                    break;
                case "Provincia5":
                    lp.setEntries(R.array.Provincia5);
                    lp.setEntryValues(R.array.Provincia5);
                    break;
            }
        }
    }
}

