package org.example.chandan.intex_app_new;

/**
 * Created by Chandan on 6/28/2016.
 */
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mmDevice;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    boolean doubleBackToExitPressedOnce = false;

    boolean valid=true, pairsysflag=true;

    int data_block = 100;
    String sys_text="", password_text="", rme_text="";

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.btn_close) Button _closebutton;
    @InjectView(R.id.checkBox) CheckBox _rememberme;
    @InjectView(R.id.textView) TextView _pairSystem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        _closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        _pairSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOpenBluetoothSettings = new Intent();
                intentOpenBluetoothSettings.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intentOpenBluetoothSettings);
            }
        });

        /*_rememberme.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_rememberme.isChecked()==true)
                {
                    try{
                        File sdcard = Environment.getExternalStorageDirectory();
                        File directory = new File(sdcard.getAbsolutePath() + "/testdata");
                        File file = new File(directory, "RMe.txt");
                        FileOutputStream fou = new FileOutputStream(file);
                        OutputStreamWriter osw = new OutputStreamWriter(fou);
                        try {
                            osw.write("YES");
                            osw.flush();
                            osw.close();
                        } catch (IOException e) {

                            e.printStackTrace();
                            Log.e(TAG, "Bluetooth not Connected");
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    try{
                        File sdcard = Environment.getExternalStorageDirectory();
                        File directory = new File(sdcard.getAbsolutePath() + "/testdata");
                        File file = new File(directory, "RMe.txt");
                        FileOutputStream fou = new FileOutputStream(file);
                        OutputStreamWriter osw = new OutputStreamWriter(fou);
                        try {
                            osw.write("NO");
                            osw.flush();
                            osw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));*/
    }

    public void text_entry()
    {
        if(rme_text.equals("YES")){
            _emailText.setText(sys_text);
            _passwordText.setText(password_text);
            _rememberme.setChecked(true);
        }

        else {
            return;
        }
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        /*
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        */

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        MainActivity.setname(email);

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        //progressDialog.dismiss();
                    }
                }, 300);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit...", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(false);

        finish();
    }

    public void onLoginFailed() {
        String email = _emailText.getText().toString();

        if (email.isEmpty() || !(email.length()==11) /*|| !(first.equals("E01")) || !(first.equals("E03"))*/ ) {
            _emailText.setError("Invalid System Number");
        }
        else if(pairsysflag==false)
        {
            _emailText.setError("Pair the System first");
        }
        else {
            Toast.makeText(getBaseContext(),"Enter correct System number",Toast.LENGTH_LONG).show();
            _emailText.setError(null);
        }

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        //boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        //String first = email.substring(0,2);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if ((pairedDevices.size() > 0)) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals(email)) {
                    valid = true;
                    pairsysflag = true;
                    break;
                }
                else {
                    valid = false;
                    pairsysflag=false;
                }
            }
        }

        if (email.isEmpty() || !(email.length()==11) /*|| !(first.equals("E01")) || !(first.equals("E03"))*/ ) {
            _emailText.setError("Invalid System Number");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        return valid;
    }


}
