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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.controle.ConsultarPedidos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaItensCampanhaGrupos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaItensCampanhaProdutos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaItensGravosos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaItensKits;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaItensMainFragment;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaItensMinimos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaItensPromocoes;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaPedidosLista;

public class ConsultasPedidos extends AppCompatActivity
{
    private ConsultarPedidos controle;

    private String[] fragTitles;
/**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        fragTitles = getResources().getStringArray(R.array.fragTitlesConsultaPedidos);

        this.controle = new ConsultarPedidos(getApplicationContext());

        displayView(R.layout.fragment_consulta_pedidos_lista);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_consulta_pedidos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentManager fragmentManager;
        Fragment fragment = null;

        switch (item.getItemId())
        {
            case R.id.consulta_pedidos_todos :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaPedidosLista) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_pedidos_lista); }
                else
                {
                    findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
                    findViewById(R.id.frame_itens).setVisibility(View.GONE);

                    try {
                        ((ConsultaPedidosLista) fragment).listarItens(this.controle.listarPedidos(0, ""));
                    } catch (GenercicException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.consulta_pedidos_resumo :

                break;
            case R.id.consulta_pedidos_nao :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaPedidosLista) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_pedidos_lista); }
                else
                {
                    findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
                    findViewById(R.id.frame_itens).setVisibility(View.GONE);

                    try {
                        ((ConsultaPedidosLista) fragment).listarItens(this.controle.listarPedidos(2, ""));
                    } catch (GenercicException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.consulta_pedidos_direta :

                break;
            case R.id.consulta_pedidos_enviados :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaPedidosLista) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_pedidos_lista); }
                else
                {
                    findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
                    findViewById(R.id.frame_itens).setVisibility(View.GONE);

                    try
                    {
                        ((ConsultaPedidosLista) fragment).listarItens(this.controle.listarPedidos(1, ""));
                    }
                    catch (GenercicException e) { e.printStackTrace(); }
                }
                break;
            default:

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
            case R.layout.fragment_consulta_pedidos_lista :
                fragment = new ConsultaPedidosLista();
                title += fragTitles[0];
                break;
            case R.layout.fragment_consulta_pedidos_diretas :
                fragment = new ConsultaItensPromocoes();
                title += fragTitles[1];
                break;
            case R.layout.fragment_consulta_pedidos_resumo :
                fragment = new ConsultaItensGravosos();
                title += fragTitles[2];
                break;

            default:
                Toast.makeText(this, "A opção selecionada não e reconhecida pelo sistema", Toast.LENGTH_LONG).show();
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
    public ConsultarPedidos getControle() { return controle; }
/*********************************END OF ITERFACES METHODS*****************************************/
}