package br.com.sulpassomobile.sulpasso.sulpassomobile.views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.controle.AtualizarSistema;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Banco;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.CampanhaProduto;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Cidade;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Estoque;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Grupo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Prazo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Preco;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.TiposVenda;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.BancoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CampanhaGrupoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CampanhaProdutoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CidadeDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.EstoqueDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.GrupoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.NaturezaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrazoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrecoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.TipoVendaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;

/**
	Todo: verificar data e hora do sistema antes de abrir os pedidos;

    Todo: Criar as classes referentes a cidade (modelo, dataAccess)
*/
	
public class Inicial extends AppCompatActivity
{
    private AtualizarSistema controleAtualizacao;

    private static final int PROGRESS = 0x1;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private ProgressBar mProgressTwo;
    private int mProgressStatusTwo = 0;

    private Handler mHandler = new Handler();

    protected boolean ignore;
    protected String displayMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        mProgress = (ProgressBar) findViewById(R.id.progress_bar_full);
        mProgressTwo = (ProgressBar) findViewById(R.id.progress_bar_pw);
/*
        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus = doWork(mProgressStatus);
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgressStatus++;
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        }).start();
*/

//        inserirDadosHardCoded();
//        listDataHardCoded();
//        ((TextView) findViewById(R.id.vendas)).setText(this.listDataHardCoded());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = false;

        switch( event.getKeyCode() ) {
            case KeyEvent.KEYCODE_MENU:
                if (ignore) result = true;
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (ignore) result = true;
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (ignore) result = true;
                break;
            case KeyEvent.KEYCODE_BACK:
                if (ignore) result = true;
                break;
            default:
                result= super.dispatchKeyEvent(event);
                break;
        }

        return result;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.inicial_agenda :

                break;
            case R.id.inicial_atualizar :
                this.controleAtualizacao = new AtualizarSistema(getApplicationContext());
                new Atualizar().execute();
                break;
            case R.id.inicial_clientes :

                break;
            case R.id.inicial_consultas :
                ((TextView) findViewById(R.id.vendas)).setText(this.listDataHardCoded());
                break;
            case R.id.inicial_pedidos :
                Intent i = new Intent(getApplicationContext(), Pedido.class);
                startActivity(i);
                break;
            case R.id.inicial_sair :

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private String listDataHardCoded()
    {
        String retorno = "";
        ClienteDataAccess cda = new ClienteDataAccess(getApplicationContext());
        ItemDataAccess ida = new ItemDataAccess(getApplicationContext());
        VendaDataAccess vda = new VendaDataAccess(getApplicationContext());

        NaturezaDataAccess nda = new NaturezaDataAccess(getApplicationContext());
        PrazoDataAccess pda = new PrazoDataAccess(getApplicationContext());
        PrecoDataAccess tda = new PrecoDataAccess(getApplicationContext());
        EstoqueDataAccess eda = new EstoqueDataAccess(getApplicationContext());

        PromocaoDataAccess proda = new PromocaoDataAccess(getApplicationContext());

        BancoDataAccess bda = new BancoDataAccess(getApplicationContext());
        CampanhaGrupoDataAccess cgda = new CampanhaGrupoDataAccess(getApplicationContext());
        CampanhaProdutoDataAccess cpda = new CampanhaProdutoDataAccess(getApplicationContext());
        CidadeDataAccess cida = new CidadeDataAccess(getApplicationContext());
        GrupoDataAccess gda = new GrupoDataAccess(getApplicationContext());
        TipoVendaDataAccess tvda = new TipoVendaDataAccess(getApplicationContext());

        List<Cliente> clientes;
        List<Item> itens;
        List<Venda> vendas;

        List<Natureza> naturezas;
        List<Prazo> prazos;
        List<Preco> precos;
        List<Estoque> estoques;

        List<Promocao> promocoes;

        List<Banco> bancos;
        List<CampanhaGrupo> campanhaGrupos;
        List<CampanhaProduto> campanhaProdutos;
        List<Cidade> cidades;
        List<Grupo> grupos;
        List<TiposVenda> tiposVendas;

        try {
            clientes = cda.getAll();
            System.out.println(clientes.toString());
            /*for(Cliente c : clientes) { System.out.println(c.toString()); }*/
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            itens = ida.getAll();
            System.out.println(itens.toString());
            /*for(Item i : itens) { System.out.println(i.toString()); }*/
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            vendas = vda.getAll();

            for(Venda v : vendas) { retorno += v.toDisplay() + "\n";System.out.println(v.toString()); }
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            naturezas = nda.buscarTodos();
            System.out.println(naturezas.toString());
            /*for(Natureza v : naturezas) { System.out.println(v.toString()); }*/
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            prazos = pda.buscarTodos();
            System.out.println(prazos.toString());
            /*for(Prazo v : prazos) { System.out.println(v.toString()); }*/
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            precos = tda.buscarTodos();
            System.out.println(precos.toString());
            /*for(Preco v : precos) { System.out.println(v.toString()); }*/
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            estoques = eda.buscarTodos();

            for(Estoque v : estoques) { System.out.println(v.toString()); }
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            promocoes = proda.buscarTodos();

            for(Promocao v : promocoes) { System.out.println(v.toString()); }
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        return retorno;
    }

    private void inserirDadosHardCoded()
    {

    }

    private class Atualizar extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            ((EditText) findViewById(R.id.vendas)).setClickable(false);
            ((EditText) findViewById(R.id.vendas)).setEnabled(false);
            ignore = true;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            displayMessage = controleAtualizacao.atualizar(3);
            publishProgress();
            displayMessage = controleAtualizacao.atualizar(4);
            publishProgress();
            displayMessage = controleAtualizacao.atualizar(5);
            publishProgress();

            for(int i = 0; i < 100; i++)
            {
                displayMessage = controleAtualizacao.atualizar(6);
                publishProgress();
            }
            controleAtualizacao.finalizarTabelas();
            publishProgress();

            controleAtualizacao.criarArquivoErros();
            publishProgress();
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
            ((EditText) findViewById(R.id.vendas)).setClickable(true);
            ((EditText) findViewById(R.id.vendas)).setEnabled(true);

            controleAtualizacao.verificarErros();

            ignore = false;
        }
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
            mProgressStatus += 25;
            mProgress.setProgress(mProgressStatus);
            ((TextView) findViewById(R.id.textProgressOne)).setText(this.displayMessage);
        }
    }
}