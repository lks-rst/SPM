package br.com.sulpassomobile.sulpasso.sulpassomobile.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.controle.AtualizarSistema;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ConfiguradorDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizacao);

        mProgress = (ProgressBar) findViewById(R.id.progress_bar_full);
        mProgressTwo = (ProgressBar) findViewById(R.id.progress_bar_pw);

        mProgressTwo.setMax(100);
        mProgress.setMax(100);

        if(instalacaoSistema())
        {
            solicitarDados();
        }
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
        switch (item.getItemId())
        {
            case R.id.atualizar_vendas :
                this.controleAtualizacao = new AtualizarSistema(getApplicationContext());
                new Pedidos().execute();
                break;
            case R.id.atualizar_sistema :
                this.controleAtualizacao = new AtualizarSistema(getApplicationContext());
                new Atualizar().execute();
                break;
            case R.id.atualizar_clientes :
                break;
            case R.id.atualizar_configuracao :
                this.controleAtualizacao = new AtualizarSistema(getApplicationContext());
                this.buscarDadosUsuario();
                new Configurar().execute();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Atualizar extends AsyncTask<Void, Void, Void>
    {
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

                displayMessage = "Conectando com o servidor.";
                publishProgress();

                if(controleAtualizacao.atualizar(3))
                {
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
                }
                else
                {
                    percentualAtualizacao = 100;
                    displayMessage = "Erro de conexão com o servidor.\nPor favor comunique seu supervisor.";
                    publishProgress();
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

            displayMessage = "Verificando se há pedidos não enviados.";
            percentualAtualizacao = 0;
            publishProgress();

            nrVendas = verificarItensNovos(0);

            if(nrVendas > 0)
            {
                percentualAtualizacao = 20;

                displayMessage = "Buscando dados de vendas.";
                publishProgress();
                if(controleAtualizacao.atualizar(9))
                {
                    displayMessage = "Criando arquivo de pedidos.";
                    publishProgress();
                    if(controleAtualizacao.atualizar(10))
                    {
                        displayMessage = "Enviando arquivo de vendas.";
                        publishProgress();
                        if(controleAtualizacao.atualizar(11))
                        {
                            displayMessage = "Atualizando vendas enviadas.";
                            publishProgress();
                            if(controleAtualizacao.atualizar(12))
                            {
                                displayMessage = "Vendas transmitidas com sucesso.";
                                controleAtualizacao.criarArquivoErros();
                                publishProgress();
                            }
                            else
                            {
                                displayMessage = "Não foi possivel atualizar as vendas enviadas.\nPor favor comunique seu supervisor.";
                                percentualAtualizacao = 100;
                                controleAtualizacao.criarArquivoErros();
                                publishProgress();
                            }
                        }
                        else
                        {
                            displayMessage = "Não foi possivel se conectar com o servidor.\nPor favor tente mais tarde.";
                            percentualAtualizacao = 100;
                            controleAtualizacao.criarArquivoErros();
                            publishProgress();
                        }
                    }
                    else
                    {
                        displayMessage = "Não foi possível criar o arquivo de vendas.\nPor favor tente mais tarde.";
                        percentualAtualizacao = 100;
                        controleAtualizacao.criarArquivoErros();
                        publishProgress();
                    }
                }
                else
                {
                    displayMessage = "Ocorreu um erro ao buscar os dados das vendas.\nPor favor tente mais tarde.";
                    percentualAtualizacao = 100;
                    controleAtualizacao.criarArquivoErros();
                    publishProgress();
                }
            }
            else
            {
                displayMessage = "Não existem pedidos para enviar.";
                percentualAtualizacao = 100;
                controleAtualizacao.criarArquivoErros();
                publishProgress();
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
        }
    }

    private class Clientes extends AsyncTask<Void, Void, Void>
    {
        /*
        TODO: É preciso criar a tabela de clietes novos e o modelo do mesmo;
         */
        @Override
        protected void onPreExecute()
        {
            ignore = true;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            int nrVendas;

            displayMessage = "Verificando se há clientes não enviados.";
            percentualAtualizacao = 0;
            publishProgress();

            nrVendas = verificarItensNovos(1);

            if(nrVendas > 0)
            {
                percentualAtualizacao = 20;

                displayMessage = "Buscando dados de clientes.";
                publishProgress();
                if(controleAtualizacao.atualizar(13))
                {
                    displayMessage = "Criando arquivo de clientes.";
                    publishProgress();
                    if(controleAtualizacao.atualizar(16))
                    {
                        displayMessage = "Enviando arquivo de clientes.";
                        publishProgress();
                        if(controleAtualizacao.atualizar(14))
                        {
                            displayMessage = "Atualizando clientes enviadas.";
                            publishProgress();
                            if(controleAtualizacao.atualizar(17))
                            {
                                displayMessage = "Clientes transmitidos com sucesso.";
                                controleAtualizacao.criarArquivoErros();
                                publishProgress();
                            }
                            else
                            {
                                displayMessage = "Não foi possivel atualizar os clientes novos enviadas.\nPor favor comunique seu supervisor.";
                                percentualAtualizacao = 100;
                                controleAtualizacao.criarArquivoErros();
                                publishProgress();
                            }
                        }
                        else
                        {
                            displayMessage = "Não foi possivel conectar-se com o servidor.\nPor favor tente mais tarde.";
                            percentualAtualizacao = 100;
                            controleAtualizacao.criarArquivoErros();
                            publishProgress();
                        }
                    }
                    else
                    {
                        displayMessage = "Não foi possível criar o arquivo de clientes.\nPor favor tente mais tarde.";
                        percentualAtualizacao = 100;
                        controleAtualizacao.criarArquivoErros();
                        publishProgress();
                    }
                }
                else
                {
                    displayMessage = "Ocorreu um erro ao buscar os dados dos clientes.\nPor favor tente mais tarde.";
                    percentualAtualizacao = 100;
                    controleAtualizacao.criarArquivoErros();
                    publishProgress();
                }
            }
            else
            {
                displayMessage = "Não existem clentes novos cadastrados.";
                percentualAtualizacao = 100;
                controleAtualizacao.criarArquivoErros();
                publishProgress();
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

            if(controleAtualizacao.atualizar(20))
            {
                percentualAtualizacao = 20;
                displayMessage = "Verificando arquivos de configuração";
                publishProgress();
                controleAtualizacao.atualizar(0);

                displayMessage = "Verificando configurações de empresa e conexão";
                publishProgress();
                controleAtualizacao.atualizar(1);

                displayMessage = "Verificando configurações de vendedor, vendas e horarios";
                publishProgress();
                controleAtualizacao.atualizar(2);

                displayMessage = "Atualizando configurações";
                publishProgress();
                controleAtualizacao.atualizar(7);

                displayMessage = "Atualizando concluída!\nPor favor reinicie o sistema";
                publishProgress();
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
            VendaDataAccess vda = new VendaDataAccess(getApplication());
            return vda.totalVendasAbertas();
        }
    }

    private boolean instalacaoSistema()
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(getApplicationContext());
        return !cda.verificarConfiguracao();
    }

    protected void atualizarLoadBar()
    {
        if(this.controleAtualizacao.isTabelas())
        {
            mProgressStatusTwo += 1;
            mProgressTwo.setProgress(mProgressStatusTwo);
            ((TextView) findViewById(R.id.textProgressTwo)).setText(this.displayMessage);
        }
        else
        {
            mProgressStatus += percentualAtualizacao;
            mProgress.setProgress(mProgressStatus);
            ((TextView) findViewById(R.id.textProgressOne)).setText(this.displayMessage);
        }
    }

    private void solicitarDados()
    {
        /*
            TODO: Ajustar o estilo do layout dessa dialog???;
         */
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

        final EditText usuario = new EditText(getApplicationContext());
        usuario.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        usuario.setHint("DIGITE O CÓDIGO DO USUÁRIO");

        ln.addView(empresa);
        ln.addView(usuario);

        alert.setView(ln);

        alert.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String emp = empresa.getText().toString().toUpperCase();
                int usr = Integer.parseInt(usuario.getText().toString());
                configurarSistema(emp, usr);
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
        this.controleAtualizacao = new AtualizarSistema(getApplicationContext());
        this.usr = usuario;
        this.empresa = empresa;
        new Configurar().execute();
    }

    private void buscarDadosUsuario()
    {
        this.usr = this.controleAtualizacao.buscarUsuario();
        this.empresa = this.controleAtualizacao.buscarEmpresa();
    }
}