package br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.Pedido;

/*
    Todo: Criar um espaço para apresentação de dados adicionais do cliente;
    Todo: Verificar se o cliente possui títulos abertos, e apresentar estes caso haja;
    Todo: Verificar se o cliente possui devoluções e apresentar as mesmas caso haja;
    Todo: Criar filtro de clientes e função de ordenação externa ao configurador;
 */

/**
 * Created by Lucas on 17/08/2016.
 */
public class DadosClienteFragment extends Fragment
{
    private Pedido activity;
    private Spinner fdcSpnrClientes;
    private Spinner fdcSpnrNaturezas;
    private Spinner fdcSpnrPrazos;

    private final Boolean ESPECIAL = true;

    public DadosClienteFragment(){}
/**************************************************************************************************/
/**********************************FRAGMENT LIFE CICLE*********************************************/
/**************************************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_dados_cliente, /*container, false*/null);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        this.setUpLayout();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        try { activity = (Pedido) getActivity(); }
        catch (ClassCastException e)
        {
            throw new ClassCastException(getActivity().toString()
                    + " must be Pedido.class calss");
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.setUpLayout();
        this.fdcSpnrClientes.setSelection(this.activity.restoreClient());
        this.bloquearClicks();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_clientes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.clientes_agenda:
                Toast.makeText(getActivity().getApplicationContext(),
                        "Selecionada opção busca clientes por agenda", Toast.LENGTH_LONG).show();
                limparCampos(10, "");
                break;
            case R.id.clientes_visita:
                Toast.makeText(getActivity().getApplicationContext(),
                        "Selecionada opção busca clientes por visita", Toast.LENGTH_LONG).show();
                limparCampos(5, "");
                break;
            case R.id.clientes_todos:
                Toast.makeText(getActivity().getApplicationContext(),
                        "Selecionada opção busca clientes todos", Toast.LENGTH_LONG).show();
                limparCampos(0, "");
                break;
            case R.id.clientes_cidade:
                Toast.makeText(getActivity().getApplicationContext(),
                        "Selecionada opção busca clientes por cidade", Toast.LENGTH_LONG).show();
                break;
            case R.id.clientes_razao:
                Toast.makeText(getActivity().getApplicationContext(),
                        "Selecionada opção busca clientes por razao", Toast.LENGTH_LONG).show();
                break;
            case R.id.clientes_fantasia:
                Toast.makeText(getActivity().getApplicationContext(),
                        "Selecionada opção busca clientes por fantazia", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
        /*
        try { this.listarItens(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Erro ao buscar os itens", Toast.LENGTH_LONG).show();
        }

        ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).setText("");
        */
        return false;
    }
/**************************************************************************************************/
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/**************************************************************************************************/
/*********************************FRAGMENT ACCESS METHODS******************************************/
/**************************************************************************************************/
    public void ajustarLayout()
    {
        this.fdcSpnrNaturezas.setAdapter(
            new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                activity.listarNaturezas(!ESPECIAL)));

        this.fdcSpnrNaturezas.setSelection(this.activity.buscarNatureza());

        this.fdcSpnrNaturezas.setOnItemSelectedListener(selectingData);

        this.fdcSpnrNaturezas.setClickable(this.activity.permitirClick(R.id.fdcSpnrNaturezas));
        this.fdcSpnrNaturezas.setEnabled(this.activity.permitirClick(R.id.fdcSpnrNaturezas));
        this.fdcSpnrPrazos.setClickable(this.activity.permitirClick(R.id.fdcSpnrPrazos));
        this.fdcSpnrPrazos.setEnabled(this.activity.permitirClick(R.id.fdcSpnrPrazos));

        ((EditText) (getActivity().findViewById(R.id.fdcEdtCidade)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtCidade));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtUf)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtUf));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtEnd)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtEnd));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtFone)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtFone));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtCel)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtCel));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtUltima)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtUltima));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtMeta)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtMeta));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtReal)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtReal));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtLimite)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtLimite));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtQuantidade)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtQuantidade));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtMedia)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtMedia));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtDca)))
                .setText(activity.buscarDadosVenda(R.id.fdcEdtDca));
    }

    public void ajustarPrazos(int posicao)
    {
        this.fdcSpnrPrazos.setAdapter(
            new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                activity.listarPrazos(posicao)));

        this.fdcSpnrPrazos.setSelection(this.activity.buscarPrazo());
        this.fdcSpnrPrazos.setOnItemSelectedListener(selectingData);

        this.fdcSpnrPrazos.setClickable(this.activity.permitirClick(R.id.fdcSpnrPrazos));
        this.fdcSpnrPrazos.setEnabled(this.activity.permitirClick(R.id.fdcSpnrPrazos));
    }

    public void bloquearClicks()
    {
        this.fdcSpnrNaturezas.setClickable(this.activity.permitirClick(R.id.fdcSpnrNaturezas));
        this.fdcSpnrNaturezas.setEnabled(this.activity.permitirClick(R.id.fdcSpnrNaturezas));
        this.fdcSpnrPrazos.setClickable(this.activity.permitirClick(R.id.fdcSpnrPrazos));
        this.fdcSpnrPrazos.setEnabled(this.activity.permitirClick(R.id.fdcSpnrPrazos));
        this.fdcSpnrClientes.setClickable(this.activity.permitirClick(R.id.fdcSpnrClientes));
        this.fdcSpnrClientes.setEnabled(this.activity.permitirClick(R.id.fdcSpnrClientes));
    }
/**************************************************************************************************/
/******************************END OF FRAGMENT ACCESS METHODS**************************************/
/**************************************************************************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
/**************************************************************************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        this.fdcSpnrClientes = (Spinner) (getActivity().findViewById(R.id.fdcSpnrClientes));
        this.fdcSpnrNaturezas = (Spinner) (getActivity().findViewById(R.id.fdcSpnrNaturezas));
        this.fdcSpnrPrazos = (Spinner) (getActivity().findViewById(R.id.fdcSpnrPrazos));

        this.fdcSpnrClientes.setOnItemSelectedListener(selectingData);

        this.fdcSpnrClientes.setAdapter( new ArrayAdapter<String>(
            getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                activity.listarClientes(0, "")));
    }

    private void limparCampos(int tipo, String dados)
    {
        ((EditText) (getActivity().findViewById(R.id.fdcEdtCidade))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtUf))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtEnd))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtFone))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtCel))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtUltima))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtMeta))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtReal))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtLimite))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtQuantidade))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtMedia))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtDca))).setText("");

        this.fdcSpnrNaturezas.setAdapter(
            new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<String>()));

        this.fdcSpnrPrazos.setAdapter(
            new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<String>()));

        this.fdcSpnrClientes.setAdapter( new ArrayAdapter<String>(
            getActivity().getApplicationContext(),
            android.support.design.R.layout.support_simple_spinner_dropdown_item,
            activity.listarClientes(tipo, dados)));
    }
/**************************************************************************************************/
/*****************************   END OF FRAGMENT FUNCTIONAL METHODS   *****************************/
/**************************************************************************************************/
/*****************************     CLICK LISTENERS FOR THE UI         *****************************/
/**************************************************************************************************/
    private AdapterView.OnItemSelectedListener selectingData = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (parent.getId())
            {
                case R.id.fdcSpnrClientes :
                    if(position > 0)
                    {
                        activity.selecionarCliente(position - 1);
                        ((EditText) getActivity().findViewById(R.id.fdcEdtTabela)).setText("");
                    }
                break;
                case R.id.fdcSpnrNaturezas :
                    ajustarPrazos(position);
                break;
                case R.id.fdcSpnrPrazos :
                    ((EditText) getActivity().findViewById(R.id.fdcEdtTabela))
                        .setText(String.format(activity.getApplicationContext()
                        .getResources().getString(R.string.str_tab)
                        , String.valueOf(String.valueOf(activity.selecionarPrazo(position)))));
                    ((EditText) getActivity().findViewById(R.id.fdcEdtPrazo)).setText(String.format
                            (activity.getApplicationContext().getResources()
                            .getString(R.string.str_prazo), activity.selecionarPrazo()));
                break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { /******/ }
    };

    private AdapterView.OnItemSelectedListener fakeSelection = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (((Spinner) parent).getId())
            {
                case R.id.fdcSpnrNaturezas :
                    fdcSpnrNaturezas.setOnItemSelectedListener(selectingData);
                    break;
                case R.id.fdcSpnrPrazos :
                    fdcSpnrPrazos.setOnItemSelectedListener(selectingData);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { /******/ }
    };
/**************************************************************************************************/
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/**************************************************************************************************/
}