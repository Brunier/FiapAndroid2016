package br.com.fiap.app.sektorapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import br.com.fiap.java.helpers.WebServiceHelper;
import br.com.fiap.java.helpers.WebservicesUrl;

public class CadastroActivity extends AppCompatActivity {
    JSONObject json = new JSONObject();

    //RadioGroup
    RadioGroup rgPessoa;
    RadioGroup rgCadastro;

    //Groups Relative
    RelativeLayout rlFisica;
    RelativeLayout rlJuridica;
    RelativeLayout rlCliente;
    RelativeLayout rlInvestidor;

    //EditTexts
    EditText edtLogradouro;
    EditText edtComplemento;
    EditText edtBairro;
    EditText edtCidade;
    EditText edtEstado;
    EditText edtCep;
    EditText edtEmail;
    EditText edtSenha;
    EditText edtConfirmaSenha;
    EditText edtTelefone;
    EditText edtNumero;

    //Fisica
    EditText edtNome;
    EditText edtCpf;
    EditText edtRg;

    //Juridica
    EditText edtRazaoSocial;
    EditText edtCnpj;
    EditText edtInscricaoEstadual;

    //Cliente
    EditText edtOcupacao;
    EditText edtBanco;
    EditText edtAgencia;
    EditText edtConta;
    Spinner spEscolaridade;
    Spinner spEstadoCivil;

    //Investidor
    EditText edtRepresentanteLegal;
    EditText edtDataNascimento;
    EditText edtRamoAtuacao;
    EditText edtQualificao;
    EditText edtTitularConta;
    EditText edtContaCustodia;

    //Escolhas do cadastro
    String strPessoa = "Fisica";
    String strCadastro = "Cliente";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.cadastro);
        setContentView(R.layout.activity_cadastro);

        //Setando RadioGroup
        rgPessoa = (RadioGroup) findViewById(R.id.rgTipoPessoa);
        rgCadastro = (RadioGroup) findViewById(R.id.rgTipoCadastro);

        rlCliente = (RelativeLayout) findViewById(R.id.rlCliente);
        rlJuridica = (RelativeLayout) findViewById(R.id.rlJuridica);
        rlFisica = (RelativeLayout) findViewById(R.id.rlFisica);
        rlInvestidor = (RelativeLayout) findViewById(R.id.rlInvestidor);

        //Setando EditTexts
        edtLogradouro = (EditText) findViewById(R.id.edtLogradouro);
        edtComplemento = (EditText) findViewById(R.id.edtComplemento);
        edtBairro = (EditText) findViewById(R.id.edtBairro);
        edtCidade = (EditText) findViewById(R.id.edtCidade);
        edtEstado = (EditText) findViewById(R.id.edtEstado);
        edtNumero = (EditText) findViewById(R.id.edtNumero);
        edtCep = (EditText) findViewById(R.id.edtCep);
        edtDataNascimento = (EditText) findViewById(R.id.edtDataNascimento);
        edtEmail = (EditText) findViewById(R.id.edtEmailCadastro);
        edtSenha = (EditText) findViewById(R.id.edtSenhaCadastro);
        edtConfirmaSenha = (EditText) findViewById(R.id.edtConfirmaSenha);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);

        //Fisica
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtCpf = (EditText) findViewById(R.id.edtCpf);
        edtRg = (EditText) findViewById(R.id.edtRg);

        //Juridica
        edtRazaoSocial = (EditText) findViewById(R.id.edtRazaoSocial);
        edtCnpj = (EditText) findViewById(R.id.edtCnpj);
        edtInscricaoEstadual = (EditText) findViewById(R.id.edtInscricaoEstadual);

        //Cliente
        edtOcupacao = (EditText) findViewById(R.id.edtOcupacao);
        edtBanco = (EditText) findViewById(R.id.edtBanco);
        edtAgencia = (EditText) findViewById(R.id.edtAgencia);
        edtConta = (EditText) findViewById(R.id.edtConta);
        spEscolaridade = (Spinner) findViewById(R.id.spEscolaridade);
        spEstadoCivil = (Spinner) findViewById(R.id.spEstadoCivil);

        //Investidor
        edtRepresentanteLegal = (EditText) findViewById(R.id.edtRepresentanteLegal);
        edtDataNascimento = (EditText) findViewById(R.id.edtDataNascimento);
        edtRamoAtuacao = (EditText) findViewById(R.id.edtRamoAtuacao);
        edtQualificao = (EditText) findViewById(R.id.edtQualificao);
        edtTitularConta = (EditText) findViewById(R.id.edtTitularConta);
        edtContaCustodia = (EditText) findViewById(R.id.edtContaCustodia);


        //Selecionando o formulário
        rgPessoa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rButton = (RadioButton) findViewById(checkedId);
                if(rButton.getText().toString().equals("Fisica")) {
                    rlFisica.setVisibility(View.VISIBLE);
                    rlJuridica.setVisibility(View.INVISIBLE);
                    strPessoa = "Fisica";
                } else {
                    rlFisica.setVisibility(View.INVISIBLE);
                    rlJuridica.setVisibility(View.VISIBLE);
                    strPessoa = "Juridica";
                }
            }
        });

        rgCadastro.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rButton = (RadioButton) findViewById(checkedId);
                if(rButton.getText().toString().equals("Cliente")) {
                    rlCliente.setVisibility(View.VISIBLE);
                    rlInvestidor.setVisibility(View.INVISIBLE);
                    strCadastro = "Cliente";
                } else {
                    rlCliente.setVisibility(View.INVISIBLE);
                    rlInvestidor.setVisibility(View.VISIBLE);
                    strCadastro = "Investidor";
                }
            }
        });

        //Pesquisa de CEP
        if(edtCep != null) {
            edtCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        BuscarCepTask buscarCepTask = new BuscarCepTask();
                        buscarCepTask.execute(edtCep.getText().toString());
                    }
                }
            });
        }

        //Mostrar dialogDataNascimento
        if(edtDataNascimento != null) {
            edtDataNascimento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        mostrarDataNascimento();
                    }
                }
            });
        }
    }

    public void cadastrarConta(View v) {
        boolean valid = true;
        ArrayList<String> messages = new ArrayList<>();

        //Validar Email
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            valid = false;
            messages.add(getString(R.string.email_invalido));
        }

        //Validar Senha
        if(!edtSenha.getText().toString().equals(edtConfirmaSenha.getText().toString())) {
            valid = false;
            messages.add(getString(R.string.senha_invalida));
        } else {
            //Validar a quantidade minima de 6 caracteres
            if(edtSenha.getText().toString().length() < 6) {
                valid = false;
                messages.add(getString(R.string.senha_invalida_caracteres));
            }
        }

        //Mostra as mensagens de erro
        StringBuilder messagesError = new StringBuilder();
        if(!valid) {
            for(int x = 0; x < messages.size(); x++) {
                if(x == 0) {
                    messagesError.append(messages.get(x));
                } else {
                    messagesError.append("\n"+messages.get(x));
                }
            }

            Toast.makeText(this, messagesError, Toast.LENGTH_SHORT).show();
        } else {
            //Montando JSON
            try {
                json.put("tipoPessoa", strPessoa);
                json.put("tipoCadastro", strCadastro);
                json.put("email", edtEmail.getText().toString());
                json.put("senha", edtSenha.getText().toString());
                json.put("telefone", edtTelefone.getText().toString());
                json.put("cep", edtCep.getText().toString());
                json.put("logradouro", edtLogradouro.getText().toString());
                json.put("numero", edtNumero.getText().toString());
                json.put("complemento", edtComplemento.getText().toString());
                json.put("bairro", edtBairro.getText().toString());
                json.put("cidade", edtCidade.getText().toString());
                json.put("estado", edtEstado.getText().toString());

                if(strPessoa.equals("Fisica")) {
                    json.put("nome", edtNome.getText().toString());
                    json.put("cpf", edtCpf.getText().toString());
                    json.put("rg", edtRg.getText().toString());
                } else {
                    json.put("razaoSocial", edtRazaoSocial.getText().toString());
                    json.put("cnpj", edtCnpj.getText().toString());
                    json.put("inscricaoEstadual", edtInscricaoEstadual.getText().toString());
                }

                if(strCadastro.equals("Cliente")) {
                    json.put("ocupacao", edtOcupacao.getText().toString());
                    json.put("banco", edtBanco.getText().toString());
                    json.put("agencia", edtAgencia.getText().toString());
                    json.put("conta", edtConta.getText().toString());
                    json.put("escolaridade", spEscolaridade.getSelectedItem().toString());
                    json.put("estadoCivil", spEstadoCivil.getSelectedItem().toString());
                } else {
                    json.put("representanteLegal", edtRepresentanteLegal.getText().toString());
                    json.put("dataFundacao", edtDataNascimento.getText().toString());
                    json.put("ramoAtuacao", edtRamoAtuacao.getText().toString());
                    json.put("qualificao", edtQualificao.getText().toString());
                    json.put("titularConta", edtTitularConta.getText().toString());
                    json.put("contaCustodia", edtContaCustodia.getText().toString());
                }

                CadastrarContaTask cadastrarTask = new CadastrarContaTask();
                cadastrarTask.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void mostrarDataNascimento() {
        DatePickerDialog picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String data = view.getDayOfMonth() +"/" + (view.getMonth() + 1) + "/" + view.getYear();
                edtDataNascimento.setText(data);
            }
        }, 2016, 0, 1);

        picker.show();
    }

    private class CadastrarContaTask extends  AsyncTask<String, Void, String> {
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(CadastroActivity.this, getString(R.string.aguarde), getString(R.string.realizando_cadastro));
        }

        @Override
        protected String doInBackground(String... params) {
            return WebServiceHelper.postRequest(WebservicesUrl.Cadastrar.getUrl(), json);
        }

        @Override
        protected void onPostExecute(String s) {
            progress.dismiss();
            if(s != null) {
                try{
                    JSONObject res = new JSONObject(s);

                    //Falha no login
                    if (res.getString("success").equals("false")) {
                        new AlertDialog.Builder(CadastroActivity.this).setTitle(getString(R.string.falha_cadastrar)).setMessage(getString(R.string.falha_servidor)).show();
                    } else {
                        Intent noticias = new Intent(CadastroActivity.this, NoticiasActionBar.class);
                        startActivity(noticias);
                        finish();
                    }


                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class BuscarCepTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return WebServiceHelper.getRequest("http://viacep.com.br/ws/"+params[0]+"/json");
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null) {
              try {
                  JSONObject json = new JSONObject(s);

                  String logradouro = json.getString("logradouro");
                  String complemento = json.getString("complemento");
                  String bairro = json.getString("bairro");
                  String cidade = json.getString("localidade");
                  String estado = json.getString("uf");

                  edtLogradouro.setText(logradouro);
                  edtBairro.setText(bairro);
                  edtComplemento.setText(complemento);
                  edtCidade.setText(cidade);
                  edtEstado.setText(estado);

              } catch (Exception e) {
                  e.printStackTrace();
              }
            }
        }
    }
}
