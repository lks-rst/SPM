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
import br.com.sulpasso.sulpassomobile.controle.ConsultaGerencial;
import br.com.sulpasso.sulpassomobile.modelo.Mensagem;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesAbc;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesAniversarios;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesCorte;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesDevolucao;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesPositivacao;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesPrePedido;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaClientesTitulos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaGerencialMensagem;

/**
 * Created by Lucas on 16/11/2016 - 11:34 as part of the project SulpassoMobile.
 */
public class ConsultasGerenciais extends AppCompatActivity
{
    private String[] fragTitles;

    private ConsultaGerencial controle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        fragTitles = getResources().getStringArray(R.array.fragTitlesConsultaGerencial);

        this.controle = new ConsultaGerencial(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_consulta_gerencial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.consulta_gerencial_mensagem :
                displayView(R.id.consulta_gerencial_mensagem);
                break;
            case R.id.consulta_gerencial_meta :
                displayView(R.id.consulta_gerencial_meta);
                break;
            case R.id.consulta_gerencial_plano :
                displayView(R.id.consulta_gerencial_plano);
                break;
            case R.id.consulta_gerencial_grafico :
                displayView(R.id.consulta_gerencial_grafico);
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
            case R.id.consulta_gerencial_mensagem  :
                fragment = new ConsultaGerencialMensagem();
                title += fragTitles[0];
                break;
            case R.id.consulta_gerencial_meta :
                fragment = new ConsultaClientesAbc();
                title += fragTitles[1];
                break;
            case R.id.consulta_gerencial_grafico :
                fragment = new ConsultaClientesTitulos();
                title += fragTitles[2];
                break;
            case R.id.consulta_gerencial_plano :
                fragment = new ConsultaClientesDevolucao();
                title += fragTitles[3];
                break;

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
    public ArrayList<Mensagem> buscarListaMensagens()
    {
        return this.controle.buscarMensagens();
    }
}