package br.com.fiap.app.sektorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.fiap.java.helpers.WebServiceHelper;
import br.com.fiap.java.helpers.WebservicesUrl;

public class NoticiasActionBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout noticiasLayout;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoticiasTask noticiasTask = new NoticiasTask();
        noticiasTask.execute();
        setContentView(R.layout.activity_noticias_action_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        noticiasLayout = (LinearLayout) findViewById(R.id.noticiasLayout);
        setTitle(getString(R.string.noticias));
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
        getMenuInflater().inflate(R.menu.noticias_action_bar, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.d("debugManeiro","clicou");

        if (id == R.id.nav_send) {
            Log.d("debugManeiro","clicou23");
            SharedPreferences.Editor e= sp.edit();
            e.putString("conectado", "false");
            e.apply();

            Intent login = new Intent(NoticiasActionBar.this, LoginActivity.class);
            startActivity(login);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class NoticiasTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return WebServiceHelper.getRequest(WebservicesUrl.Noticias.getUrl());
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null) {
                try {
                    //JSONObject json = new JSONObject(s);
                    JSONArray jsonArray = new JSONArray(s);

                    for(int x=0; x < jsonArray.length(); x++) {
                        JSONObject json = (JSONObject) jsonArray.getJSONObject(x);
                        Log.d("debugManeiro", json.getString("titulo"));


                        TextView titulo = new TextView(NoticiasActionBar.this);
                        titulo.setText(json.getString("titulo"));
                        titulo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        titulo.setTextSize(28);
                        titulo.setTypeface(Typeface.DEFAULT_BOLD);
                        titulo.setTextColor(getColor(R.color.colorAccent));
                        noticiasLayout.addView(titulo);

                        TextView data = new TextView(NoticiasActionBar.this);
                        data.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        String dataTexto = getText(R.string.data) + " " + json.getString("data");
                        data.setText(dataTexto);
                        data.setTextSize(12);
                        data.setTypeface(Typeface.DEFAULT_BOLD);
                        noticiasLayout.addView(data);

                        TextView autor = new TextView(NoticiasActionBar.this);
                        autor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        String autorTexto = getText(R.string.autor) + " " +  json.getString("autor");
                        autor.setText(autorTexto);
                        autor.setTextSize(12);
                        autor.setTypeface(Typeface.DEFAULT_BOLD);
                        noticiasLayout.addView(autor);

                        TextView conteudo = new TextView(NoticiasActionBar.this);
                        LinearLayout.LayoutParams paramsConteudo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        paramsConteudo.topMargin = 10;
                        paramsConteudo.bottomMargin = 10;
                        conteudo.setText(json.getString("conteudo"));
                        noticiasLayout.addView(conteudo, paramsConteudo);

                        View hr = new View(NoticiasActionBar.this);
                        LinearLayout.LayoutParams paramsHr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3);
                        paramsHr.topMargin = 50;
                        paramsHr.bottomMargin = 50;
                        hr.setBackgroundColor(getColor(R.color.colorPrimary));
                        noticiasLayout.addView(hr, paramsHr);






                    }

                }catch (Exception e) {

                }
            }
        }
    }
}
