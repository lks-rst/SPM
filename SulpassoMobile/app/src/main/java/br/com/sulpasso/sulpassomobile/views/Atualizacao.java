package br.com.sulpasso.sulpassomobile.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.AtualizarSistema;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorConexao;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorEmpresa;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorHorarios;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorUsuario;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ClienteNovoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ConfiguradorDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipularArquivos;
import br.com.sulpasso.sulpassomobile.util.funcoes.SenhaLiberacao;
import br.com.sulpasso.sulpassomobile.util.funcoes.WebMail;

public class Atualizacao extends AppCompatActivity
{
    private AtualizarSistema controleAtualizacao;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private ProgressBar mProgressTwo;
    private int mProgressStatusTwo = 0;

    protected boolean ignore;
    protected String displayMessage;

    private float percentualAtualizacao = 25;
    private int usr;
    private String empresa;

    private boolean baixarAtualizacao = false;

    private SenhaLiberacao sl;
    private String chave;
    private String senha;

    TextView timerTextView;
    long startTime = 0;
    protected String[] msgAtualizacao;
    protected String[] msgAtualizacaoPost;
    protected int posicaoMensagens;
    protected Boolean show;
    protected String time;

    private String resultadoAtualizacao = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizacao);

        mProgress = (ProgressBar) findViewById(R.id.progress_bar_full);
        mProgressTwo = (ProgressBar) findViewById(R.id.progress_bar_pw);

        mProgressTwo.setMax(100);
        mProgress.setMax(100);

        msgAtualizacao = getResources().getStringArray(R.array.strAtualizacao);
        msgAtualizacaoPost = getResources().getStringArray(R.array.strAtualizacaoPost);

        if(instalacaoSistema())
        {
            solicitarDados();
        }

        //Função relativa ao timer
        timerTextView = (TextView) findViewById(R.id.timerTxt);
        timerTextView.setText(" ");
        show = false;

        /*
        Button b = (Button) findViewById(R.id.btnTimer);
        b.setText("start");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText("start");
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("stop");
                }
            }
        });
        */
    }

    /**

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        boolean result = true;

        switch( event.getKeyCode() )
        {
            case KeyEvent.KEYCODE_MENU:
                if (ignore) result = false;
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (ignore) result = false;
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (ignore) result = false;
                break;
            case KeyEvent.KEYCODE_BACK:
                if (ignore) result = false;
                break;
            default:
                result= super.dispatchKeyEvent(event);
                break;
        }

        return result;
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_atualiza, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Toast t;
        resetProgress();

        switch (item.getItemId())
        {
            case R.id.atualizar_vendas :
                this.controleAtualizacao = new AtualizarSistema(getApplicationContext());
                new Pedidos().execute();

                break;
            case R.id.atualizar_clientes :
                this.controleAtualizacao = new AtualizarSistema(getApplicationContext());
                new Clientes().execute();

                break;
            case R.id.atualizar_sistema :
                this.controleAtualizacao = new AtualizarSistema(getApplicationContext());

                if(this.verificarItensNovos(0) > 0)
                {
                    /*this.baixarAtualizacao = true;
                    new Pedidos().execute(); */

                    t = Toast.makeText(getApplicationContext(),
                            "ATENÇÃO!\nExistem pedidos abertos, utilize o menu referente a essa opção antes de atualizar o sistema.",
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                    t.show();

                }
                else if(this.verificarItensNovos(1) > 0)
                {
                    /*this.baixarAtualizacao = true;
                    new Clientes().execute();*/

                    t = Toast.makeText(getApplicationContext(),
                            "ATENÇÃO!\nExistem cadastros de clientes não enviados, utilize o menu referente a essa opção antes de atualizar o sistema.",
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                    t.show();
                }
                else { new Atualizar().execute(); }

                break;
            case R.id.atualizar_configuracao :
                if(this.verificarItensNovos(0) > 0)
                {
                    t = Toast.makeText(getApplicationContext(),
                            "ATENÇÃO!\nExistem pedidos abertos, utilize o menu referente a essa opção antes de reconfigurar o sistema.",
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                    t.show();
                }
                else if(this.verificarItensNovos(1) > 0)
                {
                    t = Toast.makeText(getApplicationContext(),
                            "ATENÇÃO!\nExistem cadastros de clientes não enviados, utilize o menu referente a essa opção antes de reconfigurar o sistema.",
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                    t.show();
                }
                else
                {
                    this.sl = new SenhaLiberacao();
                    this.chave = sl.getChave();
                    solicitarSenha(Integer.parseInt(this.chave));

                    /*
                    t = Toast.makeText(getApplicationContext(),
                            "CONFIGURAÇÃO ACESSIVEL APENAS MEDIANTE SENHA, DE ACORDO COM DISPONIBILIDADE DA EMPRESA",
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                    t.show();
                    */
                }

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Atualizar extends AsyncTask<Void, Void, Void>
    {
        Boolean sem_internet = false;

        private final String[] tabelas = {"DEVOLUCAO", "COMISSAO", "MOTIVOS", "PRE_PEDIDO",
            "PRE_PEDIDO_CD", "CLIENTES", "SALDO_FLEX", "TIPO_VENDA", "METAS", "CIDADE", "TIPOLOGIA",
            "CTAS_RECEBER", "VALIDADE", "PRODUTO", "MIX", "PROMOCOES", "GRAVOSOS", "MENSAGENS",
            "BANCO", "GRUPO", "NATUREZA", "", "KIT", "TOTALIZADORES", "", "TABELA_CLIENTE", "",
            "ATIVIDADE", "PRAZOS", "", "", "", "", "TABELA_PRECOS", "", "", "", "", "", "", "", "",
            "", "ESTOQUE", "CORTE", "STATUS", "DESC_GRUPO", "DESC_CAMP", "APLICACAO",
            "CADASTRO_EMPRESA", "OLHO_IMPOSTOS", "TRANSPORTADORA", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "RESTRICAO_CLIENTE", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "RESTRICAO_GRUPO", "", "", "", "", "",
            "", "", "", "", "MENSAGENS_MSG"};

        @Override
        protected void onPreExecute()
        {
            ignore = true;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            resultadoAtualizacao = "";
            if(instalacaoSistema())
            {
                percentualAtualizacao = 1;
                displayMessage = "Verificando arquivos de configuração";
                publishProgress();
                controleAtualizacao.atualizar(0);

                percentualAtualizacao = 25;
                displayMessage = "Verificando configurações de empresa e conexão";
                publishProgress();
                controleAtualizacao.atualizar(1);

                displayMessage = "Verificando configurações de vendedor, vendas e horarios";
                publishProgress();
                controleAtualizacao.atualizar(2);

                percentualAtualizacao = 50;
                displayMessage = "Atualizando configurações";
                publishProgress();
                controleAtualizacao.atualizar(7);
            }
            else
            {
                percentualAtualizacao = 0;

                posicaoMensagens = 7;
                displayMessage = msgAtualizacao[posicaoMensagens];
                timerHandler.removeCallbacks(timerRunnable);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
                publishProgress();

                if(controleAtualizacao.atualizar(3))
                {
                    timerHandler.removeCallbacks(timerRunnable);

                    percentualAtualizacao = 25;
                    posicaoMensagens = 8;
                    displayMessage = msgAtualizacao[posicaoMensagens];
                    publishProgress();

                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);

                    if(controleAtualizacao.atualizar(4))
                    {
                        timerHandler.removeCallbacks(timerRunnable);

                        posicaoMensagens = 9;
                        displayMessage = msgAtualizacao[posicaoMensagens];
                        publishProgress();
                        startTime = System.currentTimeMillis();
                        timerHandler.postDelayed(timerRunnable, 0);

                        if(controleAtualizacao.atualizar(5))
                        {
                            timerHandler.removeCallbacks(timerRunnable);

                            posicaoMensagens = 10;
                            displayMessage = msgAtualizacao[posicaoMensagens];
                            publishProgress();
                            startTime = System.currentTimeMillis();
                            timerHandler.postDelayed(timerRunnable, 0);
                            controleAtualizacao.setTabelas(true);

                            for(int i = 0; i < 100; i++)
                            {
                                posicaoMensagens = 11;
                                displayMessage = msgAtualizacao[posicaoMensagens] + " >>> " + tabelas[i];
                                publishProgress();
                                controleAtualizacao.atualizar(6);
                            }

                            posicaoMensagens = 12;
                            displayMessage = msgAtualizacao[posicaoMensagens];
                            publishProgress();

                            int count = 100000;
                            while(count > 0)
                                count--;

                            controleAtualizacao.setTabelas(false);
                            posicaoMensagens = 13;
                            displayMessage = msgAtualizacao[posicaoMensagens];
                            publishProgress();

                            count = 100000;
                            while(count > 0)
                                count--;

                            controleAtualizacao.setTabelas(true);
                            displayMessage = "";

                            count = 100000;
                            while(count > 0)
                                count--;

                            controleAtualizacao.setTabelas(false);
                            controleAtualizacao.finalizarTabelas();
                            posicaoMensagens = 14;
                            displayMessage = msgAtualizacao[posicaoMensagens];
                            publishProgress();

                            timerHandler.removeCallbacks(timerRunnable);
                        }
                        else
                        {
                            resultadoAtualizacao = "Erro ao carregar os dados!";
                            timerHandler.removeCallbacks(timerRunnable);

                            percentualAtualizacao = 100;
                            posicaoMensagens = 15;
                            displayMessage = msgAtualizacao[posicaoMensagens];
                            publishProgress();
                        }
                    }
                    else
                    {
                        resultadoAtualizacao = "Arquivo de dados incompleto!";
                        timerHandler.removeCallbacks(timerRunnable);

                        percentualAtualizacao = 100;
                        posicaoMensagens = 16;
                        displayMessage = msgAtualizacao[posicaoMensagens];
                        publishProgress();
                    }
                }
                else
                {
                    timerHandler.removeCallbacks(timerRunnable);
                    resultadoAtualizacao = "Você não possui conexão com a internet!";

                    sem_internet = true;
                    /*
                    percentualAtualizacao = 100;
                    displayMessage = "Erro de conexão com o servidor.\nPor favor comunique seu supervisor.";
                    publishProgress();
                    */
                }
            }
            controleAtualizacao.criarArquivoErros();

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
            atualizarLoadBar();
        }

        @Override
        protected void onPostExecute(Void result)
        {
            if(sem_internet)
            {
                confirmar_baixa_sem_internet( "Você não possui atualizações disponíveis ou o servidor não pode ser localizado.\nPodemos tentar executar um arquivo transferido manualmente?\nCaso tenha dúvidas entre em contato com seu supervisor.", "Download não executado.", 2);
            }
            else
            {
                controleAtualizacao.verificarErros();
                ignore = false;

                if(instalacaoSistema()) { finish(); }
            }
        }
    }

    private class AtualizarSemIternet extends AsyncTask<Void, Void, Void>
    {
        Boolean sem_internet = false;

        private final String[] tabelas = {"DEVOLUCAO", "COMISSAO", "MOTIVOS", "PRE_PEDIDO",
                "PRE_PEDIDO_CD", "CLIENTES", "SALDO_FLEX", "TIPO_VENDA", "METAS", "CIDADE", "TIPOLOGIA",
                "CTAS_RECEBER", "VALIDADE", "PRODUTO", "MIX", "PROMOCOES", "GRAVOSOS", "MENSAGENS",
                "BANCO", "GRUPO", "NATUREZA", "", "KIT", "TOTALIZADORES", "", "TABELA_CLIENTE", "",
                "ATIVIDADE", "PRAZOS", "", "", "", "", "TABELA_PRECOS", "", "", "", "", "", "", "",
                "", "", "ESTOQUE", "CORTE", "STATUS", "DESC_GRUPO", "DESC_CAMP", "APLICACAO",
                "CADASTRO_EMPRESA", "OLHO_IMPOSTOS", "TRANSPORTADORA", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "RESTRICAO_CLIENTE", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "RESTRICAO_GRUPO", "", "",
                "", "", "", "", "", "", "", "MENSAGENS_MSG"};

        @Override
        protected void onPreExecute()
        {
            ignore = true;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            percentualAtualizacao = 0;

            percentualAtualizacao = 25;
            displayMessage = "Verificando consistência dos dados.";
            publishProgress();

            if(controleAtualizacao.atualizar(4))
            {
                displayMessage = "Separando dados para atualização.";
                publishProgress();

                if(controleAtualizacao.atualizar(5))
                {
                    displayMessage = "Atualizando Base de dados.";
                    publishProgress();
                    controleAtualizacao.setTabelas(true);

                    for(int i = 0; i < 100; i++)
                    {
                        displayMessage = "Carregando >>> " + tabelas[i];
                        publishProgress();
                        controleAtualizacao.atualizar(6);
                    }

                    displayMessage = "Dados Carregados";
                    publishProgress();

                    int count = 100000;
                    while(count > 0)
                        count--;

                    controleAtualizacao.setTabelas(false);
                    displayMessage = "Finalizando processo de atualização dos dados";
                    publishProgress();

                    count = 100000;
                    while(count > 0)
                        count--;

                    controleAtualizacao.setTabelas(true);
                    displayMessage = "";
                    publishProgress();

                    count = 100000;
                    while(count > 0)
                        count--;

                    controleAtualizacao.setTabelas(false);
                    controleAtualizacao.finalizarTabelas();
                    displayMessage = "Atualização executada com sucesso";
                    publishProgress();
                }
                else
                {
                    percentualAtualizacao = 100;
                    displayMessage = "Arquivo de dados com erro.\nPor favor comunique seu supervisor.";
                    publishProgress();
                }
            }
            else
            {
                percentualAtualizacao = 100;
                displayMessage = "Arquivo de dados não pode ser verificado ou esta incompleto.\nPor favor comunique seu supervisor.";
                publishProgress();
            }

            controleAtualizacao.criarArquivoErros();

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
            atualizarLoadBar();
        }

        @Override
        protected void onPostExecute(Void result)
        {
            controleAtualizacao.verificarErros();
            ignore = false;

            if(instalacaoSistema()) { finish(); }
        }
    }

    private class Pedidos extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            ignore = true;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            int nrVendas;

            timerHandler.removeCallbacks(timerRunnable);
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);

            displayMessage = "Enviando atualização relatórios.";
            percentualAtualizacao = 0;
            publishProgress();

            String volta = create_email();

            displayMessage = "Retorno do envio de relatórios." + volta;
            percentualAtualizacao = 0;
            publishProgress();


            for (int i = 0; i < 10000; i++)
            {
                /*
                displayMessage = "Retorno do envio de relatórios." + volta + i;
                percentualAtualizacao = 0;
                publishProgress();
                */
            }

            displayMessage = "Verificando se há pedidos não enviados.";
            percentualAtualizacao = 0;
            publishProgress();

            nrVendas = verificarItensNovos(0);

            if(nrVendas > 0)
            {
                percentualAtualizacao = 20;

                timerHandler.removeCallbacks(timerRunnable);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);

                displayMessage = "Carregando dados de vendas.";
                publishProgress();
                if(controleAtualizacao.atualizar(9))
                {
                    displayMessage = "Criando arquivo de pedidos.";
                    publishProgress();

                    timerHandler.removeCallbacks(timerRunnable);
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);

                    if(controleAtualizacao.atualizar(10))
                    {
                        displayMessage = "Enviando arquivo de vendas.";
                        publishProgress();

                        timerHandler.removeCallbacks(timerRunnable);
                        startTime = System.currentTimeMillis();
                        timerHandler.postDelayed(timerRunnable, 0);

                        if(controleAtualizacao.atualizar(11))
                        {
                            displayMessage = "Atualizando vendas enviadas.";
                            publishProgress();

                            timerHandler.removeCallbacks(timerRunnable);
                            startTime = System.currentTimeMillis();
                            timerHandler.postDelayed(timerRunnable, 0);

                            if(controleAtualizacao.atualizar(12))
                            {
                                displayMessage = "Vendas transmitidas com sucesso.";
                                controleAtualizacao.criarArquivoErros();
                                publishProgress();

                                timerHandler.removeCallbacks(timerRunnable);
                            }
                            else
                            {
                                displayMessage = "Não foi possivel atualizar as vendas enviadas.\nPor favor comunique seu supervisor.";
                                percentualAtualizacao = 100;
                                controleAtualizacao.criarArquivoErros();
                                publishProgress();

                                timerHandler.removeCallbacks(timerRunnable);
                            }
                        }
                        else
                        {
                            displayMessage = "Não foi possivel se conectar com o servidor.\nPor favor tente mais tarde.";
                            percentualAtualizacao = 100;
                            controleAtualizacao.criarArquivoErros();
                            publishProgress();

                            controleAtualizacao.atualizar(21);

                            timerHandler.removeCallbacks(timerRunnable);
                        }
                    }
                    else
                    {
                        displayMessage = "Não foi possível criar o arquivo de vendas.\nPor favor tente mais tarde.";
                        percentualAtualizacao = 100;
                        controleAtualizacao.criarArquivoErros();
                        publishProgress();

                        controleAtualizacao.atualizar(21);

                        timerHandler.removeCallbacks(timerRunnable);
                    }
                }
                else
                {
                    displayMessage = "Ocorreu um erro ao buscar os dados das vendas.\nPor favor tente mais tarde.";
                    percentualAtualizacao = 100;
                    controleAtualizacao.criarArquivoErros();
                    publishProgress();

                    controleAtualizacao.atualizar(21);

                    timerHandler.removeCallbacks(timerRunnable);
                }
            }
            else
            {
                displayMessage = "Não existem pedidos para enviar.";
                percentualAtualizacao = 100;
                controleAtualizacao.criarArquivoErros();
                publishProgress();

                timerHandler.removeCallbacks(timerRunnable);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
            atualizarLoadBar();
        }

        @Override
        protected void onPostExecute(Void result)
        {
            controleAtualizacao.verificarErros();

            ignore = false;

            /*if(baixarAtualizacao)
            {
                if(verificarItensNovos(1) > 0)
                {
                    baixarAtualizacao = true;
                    new Clientes().execute();
                }
                else { new Atualizar().execute(); }
            }*/
        }
    }

    private class Clientes extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            ignore = true;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            int nrClientes;

            displayMessage = "Verificando se há clientes não enviados.";
            percentualAtualizacao = 0;
            publishProgress();

            timerHandler.removeCallbacks(timerRunnable);
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);

            nrClientes = verificarItensNovos(1);

            if(nrClientes > 0)
            {
                percentualAtualizacao = 20;

                displayMessage = "Buscando dados de clientes.";
                publishProgress();

                timerHandler.removeCallbacks(timerRunnable);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);

                if(controleAtualizacao.atualizar(13))
                {
                    displayMessage = "Criando arquivo de clientes.";
                    publishProgress();

                    timerHandler.removeCallbacks(timerRunnable);
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);

                    if(controleAtualizacao.atualizar(16))
                    {
                        displayMessage = "Enviando arquivo de clientes.";
                        publishProgress();

                        timerHandler.removeCallbacks(timerRunnable);
                        startTime = System.currentTimeMillis();
                        timerHandler.postDelayed(timerRunnable, 0);

                        if(controleAtualizacao.atualizar(14))
                        {
                            displayMessage = "Atualizando clientes enviadas.";
                            publishProgress();

                            timerHandler.removeCallbacks(timerRunnable);
                            startTime = System.currentTimeMillis();
                            timerHandler.postDelayed(timerRunnable, 0);

                            if(controleAtualizacao.atualizar(17))
                            {
                                displayMessage = "Clientes transmitidos com sucesso.";
                                controleAtualizacao.criarArquivoErros();
                                publishProgress();

                                timerHandler.removeCallbacks(timerRunnable);
                            }
                            else
                            {
                                displayMessage = "Não foi possivel atualizar os clientes novos enviadas.\nPor favor comunique seu supervisor.";
                                percentualAtualizacao = 100;
                                controleAtualizacao.criarArquivoErros();
                                publishProgress();

                                timerHandler.removeCallbacks(timerRunnable);
                            }
                        }
                        else
                        {
                            displayMessage = "Não foi possivel conectar-se com o servidor.\nPor favor tente mais tarde.";
                            percentualAtualizacao = 100;
                            controleAtualizacao.criarArquivoErros();
                            publishProgress();

                            timerHandler.removeCallbacks(timerRunnable);

                            controleAtualizacao.atualizar(18);
                        }
                    }
                    else
                    {
                        displayMessage = "Não foi possível criar o arquivo de clientes.\nPor favor tente mais tarde.";
                        percentualAtualizacao = 100;
                        controleAtualizacao.criarArquivoErros();
                        publishProgress();

                        timerHandler.removeCallbacks(timerRunnable);

                        controleAtualizacao.atualizar(18);
                    }
                }
                else
                {
                    displayMessage = "Ocorreu um erro ao buscar os dados dos clientes.\nPor favor tente mais tarde.";
                    percentualAtualizacao = 100;
                    controleAtualizacao.criarArquivoErros();
                    publishProgress();

                    timerHandler.removeCallbacks(timerRunnable);

                    controleAtualizacao.atualizar(18);
                }
            }
            else
            {
                displayMessage = "Não existem clentes novos cadastrados.";
                percentualAtualizacao = 100;
                controleAtualizacao.criarArquivoErros();
                publishProgress();

                timerHandler.removeCallbacks(timerRunnable);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
            atualizarLoadBar();
        }

        @Override
        protected void onPostExecute(Void result)
        {
            if(baixarAtualizacao) { new Atualizar().execute(); }
            else
            {
                controleAtualizacao.verificarErros();

                ignore = false;
            }
        }
    }

    private class Configurar extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            ignore = true;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            controleAtualizacao.setUsr(usr);
            controleAtualizacao.setEmpresa(empresa);
            percentualAtualizacao = 1;

            displayMessage = String.
                format("Buscando arquivos de configuração\nUsuario = %d\nEmpresa = %s", usr, empresa);
            publishProgress();

            timerHandler.removeCallbacks(timerRunnable);
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);

            if(controleAtualizacao.atualizar(20))
            {

                timerHandler.removeCallbacks(timerRunnable);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);

                percentualAtualizacao = 20;
                displayMessage = "Verificando arquivos de configuração";
                publishProgress();
                controleAtualizacao.atualizar(0);

                timerHandler.removeCallbacks(timerRunnable);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);

                displayMessage = "Verificando configurações de empresa e conexão";
                publishProgress();
                controleAtualizacao.atualizar(1);

                timerHandler.removeCallbacks(timerRunnable);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);

                displayMessage = "Verificando configurações de vendedor, vendas e horarios";
                publishProgress();
                controleAtualizacao.atualizar(2);

                timerHandler.removeCallbacks(timerRunnable);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);

                displayMessage = "Atualizando configurações";
                publishProgress();
                controleAtualizacao.atualizar(7);

                timerHandler.removeCallbacks(timerRunnable);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);

                displayMessage = "Atualizando concluída!\nPor favor reinicie o sistema";
                publishProgress();

                timerHandler.removeCallbacks(timerRunnable);
            }
            else
            {
                displayMessage = "ERRO AO REALIZAR O DOWNLOAD DOS ARQUIVOS";
                percentualAtualizacao = 100;
                publishProgress();

                timerHandler.removeCallbacks(timerRunnable);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
            atualizarLoadBar();
        }

        @Override
        protected void onPostExecute(Void result)
        {
            controleAtualizacao.verificarErros();
            ignore = false;


            if(instalacaoSistema()) { finish(); }
        }
    }

    private int verificarItensNovos(int tipo)
    {
        if(tipo == 0)
        {
            VendaDataAccess vda = new VendaDataAccess(getApplication());
            return vda.totalVendasAbertas();
        }
        else
        {
            ClienteNovoDataAccess cnda = new ClienteNovoDataAccess(getApplication());
            return cnda.totalClientesAbertos();
        }
    }

    private boolean instalacaoSistema()
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(getApplicationContext());
        return !cda.verificarConfiguracao();
    }

    protected void atualizarLoadBar()
    {
//        TODO: Verificar a exibição das informações da atualização
        if(this.controleAtualizacao.isTabelas())
        {
            mProgressStatusTwo += 1;
            mProgressTwo.setProgress(mProgressStatusTwo);
            ((TextView) findViewById(R.id.textProgressTwo)).setText(this.displayMessage);
        }
        else
        {
//            timerTextView.setText(timerTextView.getText() + msgAtualizacaoPost[posicaoMensagens] + time + "\n");
            mProgressStatus += percentualAtualizacao;
            mProgress.setProgress(mProgressStatus);
            ((TextView) findViewById(R.id.textProgressOne)).setText(this.displayMessage);
        }

        if(this.displayMessage.equalsIgnoreCase("Atualização executada com sucesso"))
        {
//            timerTextView.setText(timerTextView.getText() + msgAtualizacao[posicaoMensagens] + time + "\n");
            mProgressTwo.setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textProgressTwo)).setVisibility(View.GONE);
        }
    }

    private void solicitarDados()
    {
        String titulo = "CONFIGURAR";
        String mensagem = "Insira os dados de configuração do sistema";

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.setCancelable(false);

        // creating LinearLayout
        LinearLayout ln = new LinearLayout(this);
        // specifying vertical orientation
        ln.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        //LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // set LinearLayout as a root element of the screen
        //setContentView(linLayout, linLayoutParam);

        final EditText empresa = new EditText(getApplicationContext());
        empresa.setHint("DIGITE O NOME DA EMPRESA");
        empresa.setBackgroundResource(R.color.White);
        empresa.setTextColor(Color.BLACK);
        empresa.setHintTextColor(getResources().getColor(R.color.colorAccent));

        final EditText usuario = new EditText(getApplicationContext());
        usuario.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        usuario.setHint("DIGITE O CÓDIGO DO USUÁRIO");
        usuario.setBackgroundResource(R.color.White);
        usuario.setTextColor(Color.BLACK);
        usuario.setHintTextColor(getResources().getColor(R.color.colorAccent));

        ln.addView(empresa);
        ln.addView(usuario);

        alert.setView(ln);

        alert.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String emp = empresa.getText().toString().trim().toUpperCase();
                int usr;
                try
                {
                    usr = Integer.parseInt(usuario.getText().toString().trim());
                    configurarSistema(emp, usr);
                }
                catch (Exception e){ Toast.makeText(getApplicationContext(), "INSIRA O CÓDIGO DO USUÁRIO", Toast.LENGTH_LONG).show(); }
            }
        });

        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which){ /*JUST IGNORE THIS BUTTON IT IS HERE ONLY FOR BETTER VISUALIZATION*/ }
        });

        alert.show();
    }

    private void configurarSistema(String empresa, int usuario)
    {
        int version;

        try { version = Integer.valueOf(Build.VERSION.SDK); }
        catch (Exception ev){ version = 3; }

        File arquivo;

        if(version == 19)
        {
            arquivo = new File("/storage/emulated/0/MobileVenda");
        }
        else
        {
            arquivo = new File(Environment.getExternalStorageDirectory() + "/MobileVenda");
        }
//        arquivo = getExternalFilesDir("MobileVenda");

        try
        {
            if (arquivo.exists())
            {
                this.controleAtualizacao = new AtualizarSistema(getApplicationContext());
                if(usuario != -1)
                {
                    this.usr = usuario;
                    this.empresa = empresa;
                }
                else
                {
                    this.usr = this.controleAtualizacao.buscarUsuario();
                    this.empresa = this.controleAtualizacao.buscarEmpresa();
                }
                new Configurar().execute();
            }
            else
            {
                String s = "ATENÇÃO!\nNão há permissão para escrita de arquivos ou a pasta do sistema não foi criada corretamente.\nPor favor, verifique e reinicie o sistema.";

                Toast t = Toast.makeText(this, s, Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();
            }
        }
        catch (Exception e)
        {
            String s = "ATENÇÃO!\nNão há permissão para escrita de arquivos ou a pasta do sistema não foi criada corretamente.\nPor favor, verifique e reinicie o sistema.";

            Toast t = Toast.makeText(this, s, Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
            t.show();
        }
    }

    private void buscarDadosUsuario()
    {
        this.usr = this.controleAtualizacao.buscarUsuario();
        this.empresa = this.controleAtualizacao.buscarEmpresa();
    }

    public void confirmar_baixa_sem_internet(String mensagem, String titulo, final int baixado)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.setCancelable(false);

        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) { verificarArquivo(); }
        });

        alert.setNegativeButton("NAO", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) { terminar(); }
        });

        alert.show();
    }

    private void verificarArquivo(){ new AtualizarSemIternet().execute(); }

    private void terminar()
    {
        percentualAtualizacao = 100;
        //displayMessage = "Arquivo de dados não pode ser verificado ou esta incompleto.\nPor favor comunique seu supervisor.";
        displayMessage = resultadoAtualizacao;
        atualizarLoadBar();
    }

    private void solicitarSenha(final int chave)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Download Configurações");
        alert.setMessage("Para atualizar as configurações do tablet digite a senha.\nChave de acesso: " + chave);
        alert.setCancelable(false);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);

        alert.setView(input);

        alert.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                senha = input.getText().toString();
                Proseguir();
            }
        });
        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) { /*****/ }
        });
        alert.show();
    }

    private void resetProgress()
    {
        mProgress.setProgress(0);
        mProgressTwo.setProgress(0);

        mProgressTwo.setMax(100);
        mProgress.setMax(100);

        findViewById(R.id.textProgressOne).setVisibility(View.GONE);
        findViewById(R.id.textProgressOne).setVisibility(View.VISIBLE);
        findViewById(R.id.textProgressTwo).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.textProgressTwo)).setText("--");

        mProgressTwo.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.VISIBLE);
    }

    private void Proseguir()
    {
        boolean liberado;

        liberado = sl.verificaChave(senha);
/*
        if(senha_abertura.indexOf("@") == -1)
        {
            if (i == ATUALIZAR)
            {
                consulta_configuracao.open();
                cod_vendedor = consulta_configuracao.buscar_codVendedor();
                consulta_configuracao.close();
                liberado = nova_senha.VerificaChave(senha_abertura + LerArquivos.StrComEsquerda("" + cod_vendedor, "0", 4));
            }
            else { liberado = nova_senha.VerificaChave(senha_abertura); }
        }
        else
        {
            liberado = true;
            i = ATUALIZAR;
        }

        if (liberado) { solicitarDados(); }
        else { /***** / }
*/
        if (liberado) { configurarSistema("", -1); }
        else { /*****/ }
    }

//Fragmento para o contador de tempo aparecer na tela do dispositivo enquanto atualiza

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            time = String.format("%d:%02d", minutes, seconds);

            ((TextView) findViewById(R.id.textProgressOneTimer)).setText(time);

            timerHandler.postDelayed(this, 500);
        }
    };


    public String create_email()
    {
        String data_arquivos = "";

        ConfiguradorConexao conexao = new ConfiguradorConexao();
        ConfiguradorHorarios horarios = new ConfiguradorHorarios();
        ConfiguradorUsuario vendedor = new ConfiguradorUsuario();
        ConfiguradorEmpresa empresa = new ConfiguradorEmpresa();
        /***********************/
        boolean finish = false;
        long endTime;
        int email_enviado;
        String email_data;
        String data;
        Calendar today;

        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(getApplicationContext());
        ManipulacaoStrings ms = new ManipulacaoStrings();

        today = Calendar.getInstance();
        int hour;
        int minutes;
        int day;
        int month;
        int year;
        int hour_email;
        int minutes_email;
        String horaFinal;

        try { conexao = cda.getConexao(); }
        catch (Exception ex) { return "Erro ao buscar configurações de conexão";/*****/ }

        try { horarios = cda.getHorario(); }
        catch (Exception ex) { return "Erro ao buscar configurações de horarios";/*****/ }

        try { vendedor = cda.getUsuario(); }
        catch (Exception ex) { return "Erro ao buscar configurações de vendedor";/*****/ }

        try { empresa = cda.getEmpresa(); }
        catch (Exception ex) { return "Erro ao buscar configurações de empresa";/*****/ }

        day = today.get(Calendar.DAY_OF_MONTH);
        month = today.get(Calendar.MONTH);
        year = today.get(Calendar.YEAR);

        data = ms.comEsquerda(String.valueOf(day), "0", 2) + "/" +
                ms.comEsquerda(String.valueOf(month + 1), "0", 2) + "/" +
                ms.comEsquerda(String.valueOf(year), "0", 4);

        hour_email = Integer.parseInt(horarios.getFinalTarde().substring(0, 2));
        minutes_email = Integer.parseInt(horarios.getFinalTarde().substring(3));

        /***********************/
        ManipularArquivos anexos = new ManipularArquivos(getApplicationContext());
        String path = Environment.getExternalStorageDirectory() + "/MobileVenda";
        String name = "PlanoVisita.txt";
        String name1 = "ProdutoFoco.txt";
        String name2 = "ResumoDia.txt";
        String name3 = "Graficos.txt";

        String vistas = "PlanoVisita.txt";
        String foco = "ProdutoFoco.txt";
        String resumo = "ResumoDia.txt";
        String graficos = "Graficos.txt";

        try { vistas = anexos.plano_visitas(name, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { return "Erro ao criar arquivo de visitas";/*****/ }

        try { foco = anexos.produtos_foco(name1, data_arquivos, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { return "Erro ao criar arqivo de foco";/*****/ }

        try { resumo = anexos.resumo_dia(name2, data_arquivos, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { return "Erro ao criar arquivo de resumo";/*****/ }

        try { graficos = anexos.graficos(name3, data_arquivos, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { return "Erro ao criar arquivo de graficos";/*****/ }

        WebMail wm = new WebMail();
        String ret = "";

        try
        {
            ret += wm.postData3(vendedor.getCodigo(), empresa.getCodigo(), 1, vistas);
            ret += " -- ";
            ret += wm.postData3(vendedor.getCodigo(), empresa.getCodigo(), 2, foco);
            ret += " -- ";
            ret += wm.postData3(vendedor.getCodigo(), empresa.getCodigo(), 3, resumo);
            ret += " -- ";
            ret += wm.postData3(vendedor.getCodigo(), empresa.getCodigo(), 4, graficos);

            //ret = wm.sendMail(vendedor.getCodigo(), empresa.getCodigo());
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return ret;
    }
}