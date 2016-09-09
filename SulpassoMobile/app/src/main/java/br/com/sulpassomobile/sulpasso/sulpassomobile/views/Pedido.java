package br.com.sulpassomobile.sulpasso.sulpassomobile.views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.controle.EfetuarPedidos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.DadosClienteFragment;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.DigitacaoItemFragment;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.FinalizacaoPedidoFragment;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ListaItensFragment;

/*
    Todo: Buscar as configurações de abertura do pedido;
    Todo: Criar as configurações de tipos de venda;
    Todo: Ajustar a forma de transação entre as telas (swipe);
    Todo: Criar o fragmento com as dados adicionais do cliente;
    Todo: Criar um fragmento para pre pedido (devera ser utilizado tanto para a consulta interna no
        pedido quanto para uma possivel consulta externa);
	Todo: verificar data e hora do sistema;

 */

public class Pedido extends AppCompatActivity
{
    private View fragmentsContainer;

    private EfetuarPedidos controlePedido;

    private String[] fragTitles;
/**********************************ACTIVITY LIFE CICLE*********************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        // load slide menu items
        fragTitles = getResources().getStringArray(R.array.fragTitles);

        // on first time display view for first nav item
        if (savedInstanceState == null)
        {
            this.controlePedido = new EfetuarPedidos(getApplicationContext());
            displayView(0);
        }

        fragmentsContainer = findViewById(R.id.frame_container);

        /*
        this.createMenu();

        // on first time display view for first nav item
        if (savedInstanceState == null) { displayView(0); }

        fragmentsContainer = findViewById(R.id.frame_container);
        progressBar = findViewById(R.id.progress);

        this.controlLiquidacao = new ControlDistribuicao(getApplicationContext());
        */
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        /*
        Log.i("Already Open", "" + alreadyOpen);

        if(!alreadyOpen)
        {
            mDrawerLayout.openDrawer(mDrawerList);
            Handler h = new Handler();
            h.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    mDrawerLayout.closeDrawer(mDrawerList);
                }
            }, 2000);

            alreadyOpen = true;
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
//        Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() { super.onDestroy(); }

    @Override
    public void onBackPressed()
    {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){ finish(); }
        else { super.onBackPressed(); }
    }
/***************************END METHODS ACTIVITY LIFE CICLE****************************************/
/********************************BUTTON CLICKS AT THE UI*******************************************/
    public void selecionarCliente(View v)
    {
        displayView(1);
    }

    public void selecionarItem(View v) { displayView(3);/*displayView(2);*/ }

    public void confirmarDigitacao(View v)
    {
        if(this.controlePedido.confirmarItem())
            getFragmentManager().popBackStackImmediate();
    }

    public void finalizar(View v)
    {
        if(this.controlePedido.finalizarPedido() == 1)
        {
//            onDestroy();
            finish();
        }
    }
/******************************END OF BUTTON CLICKS AT THE UI**************************************/
/*********************************METHODS FOR DATA ACCESS******************************************/
    public ArrayList<String> listarClientes(int tipo, String dados)
    {
        try { return this.controlePedido.listarCLientes(); }
        catch (GenercicException ge)
        {
            Toast.makeText(getApplicationContext(), ge.getMessage(), Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    public void selecionarCliente(int position)
    {
        this.controlePedido.selecionarCliente(position);

        FragmentManager fragmentManager = getFragmentManager();
        DadosClienteFragment fragment;

        try
        {
            fragment = (DadosClienteFragment) fragmentManager.findFragmentById(R.id.frame_container);

            if (fragment != null) { fragment.ajustarLayout(); }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),
                    "Erro ao carregar dados", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<String> listarNaturezas(Boolean especial)
    {
        try { return this.controlePedido.listarNaturezas(especial); }
        catch (GenercicException ge)
        {
            Toast.makeText(getApplicationContext(), ge.getMessage(), Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    public ArrayList<String> listarPrazos(int position)
    {
        try { return this.controlePedido.listarPrazos(position); }
        catch (GenercicException ge)
        {
            Toast.makeText(getApplicationContext(), ge.getMessage(), Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    public Boolean permitirClick(int id) { return this.controlePedido.permitirClick(id); }

    public int buscarNatureza()
    {
        return this.controlePedido.buscarNatureza();
    }

    public int buscarPrazo()
    {
        return this.controlePedido.buscarPrazo();
    }

    public void selecionarNatureza(int position) { /*****/ }

    public int selecionarPrazo(int position)
    {
        this.controlePedido.setPrazo(position);
        return this.controlePedido.getTabela();
    }

    public int itensVendidos() { return this.controlePedido.itensVendidos(); }

    public float valorVendido() { return this.controlePedido.valorVendido(); }

    public String listarVendidos() { return this.controlePedido.listarVendidos(); }

    public ArrayList<String> listarItens(int tipo, String dados)
    {
        this.controlePedido.indicarTipoBuscaItem(tipo);
        this.controlePedido.indicarDadosBuscaItens(dados);

        try { return this.controlePedido.listarItens(); }
        catch (GenercicException ge)
        {
            Toast.makeText(getApplicationContext(), ge.getMessage(), Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    public void selecionarItem(int position)
    {
        this.controlePedido.selecionarItem(position);
        displayView(2);
    }

    public String getItem() { return this.controlePedido.getItem(); }

    public String getValor() { return this.controlePedido.getValor(); }

    public String calcularTotalItem() { return this.controlePedido.calcularTotal(); }

    public void digitarQuantidade(String quantidade) { this.controlePedido.setQuantidade(quantidade); }

    public void digitarValor(String valor) { this.controlePedido.setValor(valor); }

    public void digitarDesconto(String desconto) { this.controlePedido.setDesconto(desconto); }

    public void digitarAcrescimo(String acrescimo) { this.controlePedido.setAcrescimo(acrescimo); }

    public int restoreClient() { return this.controlePedido.restoreClient(); }

    public void indicarToatalPedido()
    {
        this.controlePedido.indicarTotal(this.valorVendido());
    }

    public void indicarDescontoPedido(String desconto)
    {
        this.controlePedido.setDescontoPedido(desconto);
        this.exibirTotalPedido();
    }

    public String indicarFretePedido(String frete)
    {
        this.controlePedido.setFrete(frete);
        return String.valueOf(this.controlePedido.recalcularTotalPedido());
    }

    public void exibirTotalPedido()
    {
        FragmentManager fragmentManager = getFragmentManager();
        FinalizacaoPedidoFragment fragment;

        try
        {
            fragment = (FinalizacaoPedidoFragment) fragmentManager
                    .findFragmentById(R.id.frame_container);

            if (fragment != null)
            {
                fragment.ajustarLayout(String.valueOf(this.controlePedido.recalcularTotalPedido()));
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),
                    "Erro ao carregar dados", Toast.LENGTH_LONG).show();
        }
    }

    public void verificarPromocoes() { /*****/ }

    public void consultarValor() { /*****/ }

    public void confirmarInsercao()
    {
        if(this.controlePedido.confirmarItem())
            displayView(2);
        else
            Toast.makeText(getApplicationContext(), "Verifique as informações inseridas",
                    Toast.LENGTH_LONG).show();
    }

    public void informarSenha() { /*****/ }

    public void exibirInformacoes() { /*****/ }

    public void salvarPedido() { /*****/ }
/******************************END OF METHODS FOR DATA ACCESS**************************************/

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position)
    {
//        update the main content by replacing fragments
        String title = getString(R.string.telaPedido);
        Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment = new DadosClienteFragment();
                title += fragTitles[position];
                break;
            case 1:
                fragment = new ListaItensFragment();
                title += fragTitles[position];
                break;
            case 2:
                fragment = new DigitacaoItemFragment();
                title += fragTitles[position];
                break;
            case 3:
                fragment = new FinalizacaoPedidoFragment();
                title += fragTitles[position];
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                Toast.makeText(this, "Clicado na posição -1", Toast.LENGTH_LONG).show();
//                    title = fragTitles[position];
                break;
        }
        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            } else
            {
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            }
        }
        else { Log.e("MainActivity", "Error in creating fragment"); }

        /*
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawer(mDrawerList);
        */
        setTitle(title);
    }
}