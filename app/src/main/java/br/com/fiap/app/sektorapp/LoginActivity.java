package br.com.fiap.app.sektorapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import br.com.fiap.java.helpers.WebServiceHelper;
import br.com.fiap.java.helpers.WebservicesUrl;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private CheckBox cbConectado;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.login));
        setContentView(R.layout.activity_login);


        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Log.d("debugManeiro", sp.getString("conectado", "false"));
        if(sp.getString("conectado", "false").equals("true")) {
            Intent noticias = new Intent(LoginActivity.this, NoticiasActionBar.class);
            startActivity(noticias);
            finish();
        }


        email = (EditText) findViewById(R.id.edtEmail);
        senha = (EditText) findViewById(R.id.edtSenha);
        cbConectado = (CheckBox) findViewById(R.id.cbConectado);
    }

    public void login(View v) {
        AuthTask task = new AuthTask();
        task.execute(email.getText().toString(), senha.getText().toString());
    }

    public void criarConta(View v) {
        Intent cadastro = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(cadastro);
    }

    private class AuthTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(LoginActivity.this, getString(R.string.aguarde), getString(R.string.realizando_login));
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                JSONObject auth = new JSONObject();
                auth.put("email", params[0]);
                auth.put("senha", params[1]);

                return WebServiceHelper.postRequest(WebservicesUrl.Login.getUrl(), auth);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progress.dismiss();
            if(s != null) {
                try{
                    JSONObject res = new JSONObject(s);

                    //Falha no login
                    if (res.getString("success").equals("false")) {
                        new AlertDialog.Builder(LoginActivity.this).setTitle(getString(R.string.falha_login)).setMessage(getString(R.string.incorreto)).show();
                    } else {
                        SharedPreferences.Editor e= sp.edit();
                        if(cbConectado.isChecked()) {
                            e.putString("conectado", "true");
                        } else {
                            e.putString("conectado", "false");
                        }
                        e.apply();

                        Intent noticias = new Intent(LoginActivity.this, NoticiasActionBar.class);
                        startActivity(noticias);
                        finish();
                    }


                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
