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

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensCampanhaGrupos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensCampanhaProdutos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensGravosos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensKits;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensMainFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensMinimos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensPromocoes;

/**
 * Created by Lucas on 16/11/2016 - 11:33 as part of the project SulpassoMobile.
 */
public class ConsultasItens extends AppCompatActivity
{
    private String[] fragTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        fragTitles = getResources().getStringArray(R.array.fragTitlesConsultaItens);
        displayView(R.layout.fragment_consulta_itens_principal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_consulta_itens, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentManager fragmentManager;
        Fragment fragment = null;

        switch (item.getItemId())
        {
            case R.id.consulta_itens_campanha :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaItensCampanhaProdutos) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_itens_campanhas_produtos); }
                break;
            case R.id.consulta_itens_grupos :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaItensCampanhaGrupos) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_itens_campanhas_grupos); }
                break;
            case R.id.consulta_itens_promocao :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaItensPromocoes) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_itens_promocoes); }
                break;
            case R.id.consulta_itens_precos :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaItensMainFragment) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_itens_principal); }
                break;
            case R.id.consulta_itens_gravosos :

                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaItensGravosos) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_itens_gravosos); }
                break;
            case R.id.consulta_itens_kit :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaItensKits) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_itens_kits); }
                break;
            case R.id.consulta_itens_minimo :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaItensMinimos) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_itens_minimos); }
                break;
            case R.id.consulta_itens_foco :
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch( event.getKeyCode() )
        {
            case KeyEvent.KEYCODE_BACK:
                /*
                getFragmentManager().popBackStackImmediate();
                int fragmentos = getFragmentManager().getBackStackEntryCount();
                if(fragmentos == 0)
                {
                    getFragmentManager().executePendingTransactions();
                    onDestroy();
                    finish();
                }
                *
                break;
        }

        return false;
    }
    */

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
            case R.layout.fragment_consulta_itens_principal:
                fragment = new ConsultaItensMainFragment();
                title += fragTitles[0];
                break;
            case R.layout.fragment_consulta_itens_promocoes:
                fragment = new ConsultaItensPromocoes();
                title += fragTitles[1];
                break;
            case R.layout.fragment_consulta_itens_gravosos :
                fragment = new ConsultaItensGravosos();
                title += fragTitles[2];
                break;
            case R.layout.fragment_consulta_itens_kits :
                fragment = new ConsultaItensKits();
                title += fragTitles[3];
                break;
            case R.layout.fragment_consulta_itens_minimos :
                fragment = new ConsultaItensMinimos();
                title += fragTitles[4];
                break;
            case R.layout.fragment_consulta_itens_campanhas_produtos :
                fragment = new ConsultaItensCampanhaProdutos();
                title += fragTitles[5];
                break;
            case R.layout.fragment_consulta_itens_campanhas_grupos :
                fragment = new ConsultaItensCampanhaGrupos();
                title += fragTitles[6];
                break;
            case 5:
                break;
            default:
                Toast.makeText(this, "Clicado na posição -1", Toast.LENGTH_LONG).show();
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
}