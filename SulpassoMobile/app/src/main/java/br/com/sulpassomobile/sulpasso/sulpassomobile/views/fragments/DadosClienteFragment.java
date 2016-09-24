package br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.Pedido;

/*
    Todo: Acrescentas os dados de venda à tela;
    Todo: Criar um espaço para apresentação de dados adicionais do cliente;
    Todo: Verificar se o cliente possui títulos abertos, e apresentar estes caso haja;
    Todo: Verificar se o cliente possui devoluções e apresentar as mesmas caso haja;
    Todo: Criar filtro de clientes e função de ordenaçã externa ao configurador;

    TODO: Verificar alteração de clientes, trazer naturezas, prazo e tabelas;
    TODO: Verificar alteração de natureza, trazer prazos;
    TODO: Verificar alteração de prazos carregar tabela (quando necessário);
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
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
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
                            .setText(String.valueOf(activity.selecionarPrazo(position)));
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