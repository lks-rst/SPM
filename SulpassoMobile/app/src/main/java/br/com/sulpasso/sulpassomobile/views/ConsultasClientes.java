package br.com.sulpasso.sulpassomobile.views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.ConsultarClientes;
import br.com.sulpasso.sulpassomobile.controle.interfaces.ConsultaClientes;
import br.com.sulpasso.sulpassomobile.modelo.CurvaAbc;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesAbc;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesAniversarios;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesCorte;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesDevolucao;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesPositivacao;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesPrePedido;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesTitulos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensCampanhaGrupos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensCampanhaProdutos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensGravosos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensKits;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensMainFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensMinimos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensPromocoes;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.GrupoSelection;

/**
 * Created by Lucas on 16/11/2016 - 11:31 as part of the project SulpassoMobile.
 */
public class ConsultasClientes extends AppCompatActivity implements ConsultaClientes
{
    private br.com.sulpasso.sulpassomobile.controle.ConsultaClientes controle;
    private String[] fragTitles;
/**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        controle =
            new br.com.sulpasso.sulpassomobile.controle.ConsultaClientes(getApplicationContext());

        // load slide menu items
        fragTitles = getResources().getStringArray(R.array.fragTitlesConsultaClientes);
        displayView(R.id.consulta_clientes_positivacao);

        int b = valorFixo2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_consulta_clientes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.consulta_clientes_positivacao :
                displayView(R.id.consulta_clientes_positivacao);
                break;
            case R.id.consulta_clientes_titulos :
                displayView(R.id.consulta_clientes_titulos);
                break;
            case R.id.consulta_clientes_devolucao :
                displayView(R.id.consulta_clientes_devolucao);
                break;
            case R.id.consulta_clientes_corte :
                displayView(R.id.consulta_clientes_corte);
                break;
            /*
            case R.id.consulta_clientes_abc :
                displayView(R.id.consulta_clientes_abc);
                break;
            case R.id.consulta_clientes_pre :
                displayView(R.id.consulta_clientes_pre);
                break;
            case R.id.consulta_clientes_aniversario :
                displayView(R.id.consulta_clientes_aniversario);
                break;
            */

            default:
                Toast.makeText(getApplicationContext(), "Escolha uma das opções do menu",
                    Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position)
    {
//        update the main content by replacing fragments
        String title = getString(R.string.telasConsulta);
        Fragment fragment = null;

        getFragmentManager().popBackStackImmediate();
        switch (position)
        {
            case R.id.consulta_clientes_positivacao :
                fragment = new ConsultaClientesPositivacao();
                title += fragTitles[0];
                break;
            /*
            case R.id.consulta_clientes_abc :
                fragment = new ConsultaClientesAbc();
                title += fragTitles[1];
                break;
            */
            case R.id.consulta_clientes_titulos :
                fragment = new ConsultaClientesTitulos();
                title += fragTitles[2];
                break;
            case R.id.consulta_clientes_devolucao :
                fragment = new ConsultaClientesDevolucao();
                title += fragTitles[3];
                break;
            case R.id.consulta_clientes_corte :
                fragment = new ConsultaClientesCorte();
                title += fragTitles[4];
                break;
            /*
            case R.id.consulta_clientes_pre :
                fragment = new ConsultaClientesPrePedido();
                title += fragTitles[5];
                break;
            case R.id.consulta_clientes_aniversario :
                fragment = new ConsultaClientesAniversarios();
                title += fragTitles[6];
                break;
            */
            default:
                Toast.makeText(getApplicationContext(), "Escolha uma das opções do menu",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.frame_consultas, fragment).addToBackStack(null).commit();
            } else
            {
                fragmentManager.beginTransaction().replace(R.id.frame_consultas, fragment).addToBackStack(null).commit();
            }
        }
        else { Log.e("MainActivity", "Error in creating fragment"); }
        setTitle(title);
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    @Override
    public ArrayList<String> buscarListaClientes() { return this.controle.buscarListaClientes(); }

    @Override
    public ArrayList<String> buscarListaTitulos() { return this.controle.buscarListaTitulos(); }

    @Override
    public ArrayList<String> buscarListaDevolucao() { return this.controle.buscarListaDevolucao(); }

    @Override
    public ArrayList<String> buscarListaCorte() { return this.controle.buscarListaCorte(); }

    @Override
    public ArrayList<String> buscarItensCorte(int posicao) { return this.controle.buscarItensCorte(posicao); }

    @Override
    public ArrayList<String> buscarItensDevolucao(int posicao) { return this.controle.buscarItensDevolucao(posicao); }

    @Override
    public ArrayList<String> buscarItensTitulos(int posicao) { return this.controle.buscarItensTitulos(posicao); }

    @Override
    public ArrayList<String> buscarDetalhes(int posicao, int tipo) { return this.controle.buscarDetalhes(posicao, tipo); }

    @Override
    public ArrayList<String> buscarListaPrePedido() { return this.controle.buscarListaPrePedido(); }

    @Override
    public ArrayList<String> buscarListaPositivacao(boolean semana, boolean positivado)
    {
        return this.controle.buscarListaPositivacao(semana, positivado);
    }

    @Override
    public String totalPositivacao()
    {
        return this.controle.totalPositivacao();
    }

    @Override
    public ArrayList<String> buscarListaClientes(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaTitulos(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaDevolucao(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaCorte(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaPrePedido(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaPositivacao(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaClientes(int tipo, int cidade) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaTitulos(int tipo, int cidade) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaDevolucao(int tipo, int cidade) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaCorte(int tipo, int cidade) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaPrePedido(int tipo, int cidade) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaPositivacao(int tipo, int cidade) {
        return null;
    }

    @Override
    public ArrayList<String> detalharCorte(int cliente) { return this.controle.detalharCorte(cliente); }

    @Override
    public CurvaAbc detalharAbc(int cliente) { return this.controle.detalharAbc(cliente); }

    @Override
    public ArrayList<String> detalharDevolucao(int cliente) { return this.controle.detalharDevolucao(cliente); }

    @Override
    public PrePedido detalharPrePedido(int cliente) { return this.controle.detalharPrePedido(cliente); }

    @Override
    public ArrayList<String> detalharTitulos(int cliente) { return this.controle.detalharTitulos(cliente); }

    @Override
    public ArrayList<String> detalhesConsulta(int cliente, int tipo) { return this.controle.detalhesConsulta(cliente, tipo); }

    public String buscarDescricaoItem(int posicao)
    {
        return this.controle.buscarDescricaoItem(posicao);
    }
/*********************************END OF ITERFACES METHODS*****************************************/
}