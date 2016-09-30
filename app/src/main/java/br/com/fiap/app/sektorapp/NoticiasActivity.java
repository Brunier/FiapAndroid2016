package br.com.fiap.app.sektorapp;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import br.com.fiap.java.helpers.WebServiceHelper;
import br.com.fiap.java.helpers.WebservicesUrl;

public class NoticiasActivity extends AppCompatActivity {
    LinearLayout noticiasLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoticiasTask noticiasTask = new NoticiasTask();
        noticiasTask.execute();
        setTitle(getString(R.string.noticias));
        setContentView(R.layout.activity_noticias);
        noticiasLayout = (LinearLayout) findViewById(R.id.noticiasLayout);



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


                        TextView titulo = new TextView(NoticiasActivity.this);
                        titulo.setText(json.getString("titulo"));
                        titulo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        titulo.setTextSize(28);
                        titulo.setTypeface(Typeface.DEFAULT_BOLD);
                        titulo.setTextColor(getColor(R.color.colorAccent));
                        noticiasLayout.addView(titulo);

                        TextView data = new TextView(NoticiasActivity.this);
                        data.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        String dataTexto = getText(R.string.data) + " " + json.getString("data");
                        data.setText(dataTexto);
                        data.setTextSize(12);
                        data.setTypeface(Typeface.DEFAULT_BOLD);
                        noticiasLayout.addView(data);

                        TextView autor = new TextView(NoticiasActivity.this);
                        autor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        String autorTexto = getText(R.string.autor) + " " +  json.getString("autor");
                        autor.setText(autorTexto);
                        autor.setTextSize(12);
                        autor.setTypeface(Typeface.DEFAULT_BOLD);
                        noticiasLayout.addView(autor);

                        TextView conteudo = new TextView(NoticiasActivity.this);
                        LinearLayout.LayoutParams paramsConteudo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        paramsConteudo.topMargin = 10;
                        paramsConteudo.bottomMargin = 10;
                        conteudo.setText(json.getString("conteudo"));
                        noticiasLayout.addView(conteudo, paramsConteudo);

                        View hr = new View(NoticiasActivity.this);
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
