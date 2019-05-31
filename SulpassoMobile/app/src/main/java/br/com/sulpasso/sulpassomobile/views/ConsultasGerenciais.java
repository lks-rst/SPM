package br.com.sulpasso.sulpassomobile.views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.ConsultaGerencial;
import br.com.sulpasso.sulpassomobile.modelo.Mensagem;
import br.com.sulpasso.sulpassomobile.modelo.Meta;
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaGerencialGraficos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaGerencialMensagem;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaGerencialMetas;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaGerencialPlanoVisitas;

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
            /*
            case R.id.consulta_gerencial_plano :
                displayView(R.id.consulta_gerencial_plano);
                break;
            case R.id.consulta_gerencial_grafico :
                displayView(R.id.consulta_gerencial_grafico);
                break;
            */

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
                fragment = new ConsultaGerencialMetas();
                title += fragTitles[1] + " - IDEAL: " + this.buscarMetaIdeal();
                break;
            /*
            case R.id.consulta_gerencial_grafico :
                this.controle.criarGraficos();
                fragment = new ConsultaGerencialGraficos();
                title += fragTitles[2];
                break;
            case R.id.consulta_gerencial_plano :
                fragment = new ConsultaGerencialPlanoVisitas();
                title += fragTitles[3];
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
    public void alterar_data(View v) { showDialog(0); };

    @Override
    protected Dialog onCreateDialog(int id)
    {
        Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id)
        {
            case 0:
                return new DatePickerDialog(this, select_date, ano, mes, dia);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener select_date = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            ManipulacaoStrings ms = new ManipulacaoStrings();
            String data = ms.comEsquerda(String.valueOf(dayOfMonth), "0", 2) + "/" +
                    ms.comEsquerda(String.valueOf(monthOfYear+1), "0", 2) + "/" + String.valueOf(year);

            Toast.makeText(getApplicationContext(), "Nova data selecionada == " + data, Toast.LENGTH_LONG).show();

            FragmentManager fragmentManager = getFragmentManager();
            ConsultaGerencialPlanoVisitas fragment;

            try
            {
                fragment = (ConsultaGerencialPlanoVisitas) fragmentManager.findFragmentById(R.id.frame_consultas);

                if (fragment != null) { fragment.indcarNovaData(data); }
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),
                        "Erro ao carregar dados", Toast.LENGTH_LONG).show();
            }
        }
    };
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    public ArrayList<Mensagem> buscarListaMensagens() { return this.controle.buscarMensagens(); }

    public int percentualGrafico(int campo, int mes)
    {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int max = dm.widthPixels;

        return this.controle.percentualGrafico(campo, mes, max);
    }

    public ArrayList<Meta> buscarListaMetas() { return this.controle.buscarListaMetas(); }

    public float buscarMetaIdeal() { return this.controle.buscarMetaIdeal(); }

    public String buscarMetaTotal(int tipo)
    {
        return Formatacao.format2d(this.controle.buscarMetaTotal(tipo));
    }

    public String buscarMeta(int posicao, int campo, int tipo) { return this.controle.buscarMeta(posicao, campo, tipo); }

    public String buscarDataAtual()
    {
        Date data_ = new Date();
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");
        return sf.format(data_);
    }

    public void buscarPlanoVisitas(String data)
    {
        ArrayList<String> plano = this.controle.criarPlano(data);

        FragmentManager fragmentManager = getFragmentManager();
        ConsultaGerencialPlanoVisitas fragment;

        try
        {
            fragment = (ConsultaGerencialPlanoVisitas) fragmentManager.findFragmentById(R.id.frame_consultas);

            if (fragment != null) { fragment.apresentarPlano(plano); }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),
                    "Erro ao carregar dados", Toast.LENGTH_LONG).show();
        }
    }
}